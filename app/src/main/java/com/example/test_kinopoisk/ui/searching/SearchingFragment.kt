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
import com.example.domain.entity.data_model_search.Film
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentSearchingBinding
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment
import kotlinx.coroutines.launch

class SearchingFragment : Fragment() {

    private var _binding: FragmentSearchingBinding? = null
    private val binding get() = _binding!!

    private val searchingViewModel: SearchingViewModel by activityViewModels()
    private val searchFilmKeywordAdapter = SearchFilmKeywordAdapter { film -> onItemClick(film) }

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
        binding.recyclerSearch.adapter = searchFilmKeywordAdapter
        val searchView = binding.svSearch
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                //Получить список фильмов по ключевому слову
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        if (query != null) {
                            searchingViewModel.getFilmKeyword(query)
                        }
                        searchingViewModel.isLoadingFilmKeyword.collect { loadingFIlmKeyword ->
                            if (loadingFIlmKeyword) {
                                searchingViewModel.listFilmKeyword.collect { filmKeyword ->
                                    searchFilmKeywordAdapter.submitList(filmKeyword[0].films)
                                }
                            }
                        }
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Здесь вы можете реагировать на изменения текста в SearchView
                return true
            }
        })
    }

    //Клик по фильму, переход к инфо о фильме
    private fun onItemClick(film: Film) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}