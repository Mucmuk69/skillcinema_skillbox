package com.example.test_kinopoisk.ui.searching

import android.os.Bundle
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
import com.example.domain.entity.data_model_search.film_keyword.Film
import com.example.domain.entity.data_model_search.staff_keyword.Item
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentSearchingBinding
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment
import com.example.test_kinopoisk.ui.staffinfo.StaffInfoFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchingFragment : Fragment() {

    private var _binding: FragmentSearchingBinding? = null
    private val binding get() = _binding!!

    private val searchingViewModel: SearchingViewModel by activityViewModels()
    private val searchFilmKeywordAdapter = SearchFilmKeywordAdapter { film -> onFilmClick(film) }
    private val searchStaffKeywordAdapter =
        SearchStaffKeywordAdapter { staff -> onStaffClick(staff) }

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
        binding.recyclerSearchFilm.adapter = searchFilmKeywordAdapter
        binding.recyclerSearchStaff.adapter = searchStaffKeywordAdapter

        val searchView = binding.svSearch
        var searchFilmJob: Job? = null
        var searchStaffJob: Job? = null
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                //Получить список актеров, режиссеров по имени
//                viewLifecycleOwner.lifecycleScope.launch {
//                    repeatOnLifecycle(Lifecycle.State.STARTED) {
//                        if (query != null) {
//                            searchingViewModel.getStaffKeyword(query)
//                        }
//                        searchingViewModel.isLoadingStaffKeyword.collect { loadingStaffKeyword ->
//                            if (loadingStaffKeyword) {
//                                searchingViewModel.listStaffKeyword.collect { staffKeyword ->
//                                    searchStaffKeywordAdapter.submitList(staffKeyword[0].items)
//                                }
//                            }
//                        }
//                    }
//                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchFilmJob?.cancel()
                // Получить список фильмов по названию
                searchFilmJob = viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                                    } else {
                                        // Если фильмов не найдено, вывести сообщение о том, что результатов не найдено
                                        binding.tvNoResult.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
                searchStaffJob?.cancel()
                //Получить список актеров, режиссеров по имени
                searchStaffJob = viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        //Если введено более 3‑х символов, то делаем запрос
                        //Новый запрос после добавления символов спустя 3 секунды
                        delay(4_000)
                        if (newText != null && newText.length >= 5) {
                            searchingViewModel.getStaffKeyword(newText)
                        }
                        searchingViewModel.isLoadingStaffKeyword.collect { loadingStaffKeyword ->
                            if (loadingStaffKeyword) {
                                searchingViewModel.listStaffKeyword.collect { staffKeyword ->
                                    //Если количество найденных актеров 0, то выводим сообщение
                                    if (staffKeyword[0].total != null) {
                                        searchStaffKeywordAdapter.submitList(staffKeyword[0].items)
                                    } else {
                                        // Если актеров не найдено, вывести сообщение о том, что результатов не найдено
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

    //Клик по актеру, переход к инфо об актере
    private fun onStaffClick(staff: Item) {
        val staffId = staff.kinopoiskId!!
        val staffInfoFragment = StaffInfoFragment.newInstance(staffId = staffId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_searching) {
            findNavController().navigate(
                R.id.action_navigation_searching_to_navigation_staff_info,
                staffInfoFragment.arguments
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}