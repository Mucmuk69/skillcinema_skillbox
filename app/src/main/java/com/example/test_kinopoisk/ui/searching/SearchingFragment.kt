package com.example.test_kinopoisk.ui.searching

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.data_model_movie.Items
import com.example.domain.entity.data_model_search.film_keyword.Film
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentSearchingBinding
import com.example.test_kinopoisk.ui.loadingcollections.SharedCollectionsViewModel
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchingFragment : Fragment() {

    private var _binding: FragmentSearchingBinding? = null
    private val binding get() = _binding!!

    private val searchingViewModel: SearchingViewModel by activityViewModels()
    private val sharedCollectionsVM: SharedCollectionsViewModel by activityViewModels()
    private val searchFilmKeywordAdapter = SearchFilmKeywordAdapter { film -> onFilmClick(film) }
    private val searchingAdvancedAdapter = SearchingAdvancedAdapter { film -> onItemClick(film) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearchKeyword.adapter = searchFilmKeywordAdapter
        binding.recyclerSearchAdvanced.adapter = searchingAdvancedAdapter

        val searchView = binding.svSearch
        var searchFilmJob: Job? = null

        //Вся область поисковой строки кликабельна
        searchView.setOnClickListener {
            searchView.isIconified = false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchFilmJob?.cancel()
                // Получить список фильмов по названию
                searchFilmJob = viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        binding.recyclerSearchKeyword.visibility = View.VISIBLE
                        binding.recyclerSearchAdvanced.visibility = View.GONE
                        binding.tvPrevious.visibility = View.VISIBLE
                        //Если введено более 3‑х символов, то делаем запрос
                        //Новый запрос после добавления символов спустя 3 секунды
                        delay(3_000)
                        if (newText != null && newText.length >= 3) {
                            searchingViewModel.getFilmKeyword(newText)
                        }
                        searchingViewModel.isLoadingFilmKeyword.collect { loadingFilmKeyword ->
                            if (loadingFilmKeyword) {
                                searchingViewModel.listFilmKeyword.collect { filmKeyword ->
                                    //Если количество найденных фильмов 0, то выводим сообщение
                                    if (filmKeyword[0].searchFilmsCountResult != null) {
                                        searchFilmKeywordAdapter.submitList(filmKeyword[0].films)
                                        binding.tvPrevious.visibility = View.GONE
                                    } else {
                                        // Если фильмов не найдено, вывести сообщение о том, что результатов не найдено
                                        binding.tvNoResult.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
                return true
            }
        })

        //Получаем список фильмов/сериалов через расширенные настройки поиска
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    if (arguments?.getString(AdvancedSearchFragment.ARG_COUNTRY) != null && arguments?.getString(
                            AdvancedSearchFragment.ARG_GENRE
                        ) != null) {
                        binding.recyclerSearchAdvanced.visibility = View.VISIBLE
                        binding.recyclerSearchKeyword.visibility = View.GONE
                        binding.tvPrevious.visibility = View.GONE
                    val country = arguments?.getString(AdvancedSearchFragment.ARG_COUNTRY)
                    val genre = arguments?.getString(AdvancedSearchFragment.ARG_GENRE)
                    val indexCountry =
                        sharedCollectionsVM.listCountriesAndGenres.value.map { countriesAndGenres ->
                            countriesAndGenres.countries.indexOfFirst { it.country == country } + 1
                        }
                    Log.d("MyTag", "indexCountry: $indexCountry")
                    val indexGenre =
                        sharedCollectionsVM.listCountriesAndGenres.value.map { countriesAndGenres ->
                            countriesAndGenres.genres.indexOfFirst { it.genre == genre } + 1
                        }
                    Log.d("MyTag", "indexGenre: $indexGenre")
                    val type = arguments?.getString(AdvancedSearchFragment.ARG_TYPE)
                    val order = arguments?.getString(AdvancedSearchFragment.ARG_ORDER)
                    val yearFrom = arguments?.getInt(AdvancedSearchFragment.ARG_YEAR_FROM)
                    val yearTo = arguments?.getInt(AdvancedSearchFragment.ARG_YEAR_TO)
                    val ratingFrom = arguments?.getInt(AdvancedSearchFragment.ARG_RATING_FROM)
                    val ratingTo = arguments?.getInt(AdvancedSearchFragment.ARG_RATING_TO)
                    if (order != null && type != null && ratingFrom != null && ratingTo != null &&
                        yearFrom != null && yearTo != null
                    ) {
                        searchingViewModel.getFilmSearching(
                            countries = indexCountry,
                            genres = indexGenre,
                            order = order,
                            type = type,
                            ratingFrom = ratingFrom,
                            ratingTo = ratingTo,
                            yearFrom = yearFrom,
                            yearTo = yearTo
                        )
                    }
                    searchingViewModel.isLoadingFilmSearching.collect { loadingFilmsSearch ->
                        if (loadingFilmsSearch) {
                            searchingViewModel.listFilmSearching.collect { movies ->
                                searchingAdvancedAdapter.submitList(movies[0].items)
                            }
                        }
                    }
                }
            }
        }

        binding.ivAdvancedSearch.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_searching_to_navigation_advanced_search)
        }

    }

    //Клик по фильму, переход к инфо о фильме
    private fun onFilmClick(film: Film) {
        val movieId = film.filmId
        val movieInfoFragment = movieId?.let { MovieInfoFragment.newInstance(movieId = it) }
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_searching) {
            findNavController().navigate(
                R.id.action_navigation_searching_to_navigation_movie_info,
                movieInfoFragment?.arguments
            )
        }
    }

    //Клик по фильму, переход к инфо о фильме
    private fun onItemClick(film: Items) {
        val movieId = film.kinopoiskId
        val movieInfoFragment = movieId?.let { MovieInfoFragment.newInstance(movieId = it) }
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_searching) {
            findNavController().navigate(
                R.id.action_navigation_searching_to_navigation_movie_info,
                movieInfoFragment?.arguments
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}