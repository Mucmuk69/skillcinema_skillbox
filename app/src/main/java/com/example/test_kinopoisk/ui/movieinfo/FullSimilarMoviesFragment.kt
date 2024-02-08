package com.example.test_kinopoisk.ui.movieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.data_model_similar_movies.Item
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentFullSimilarMoviesBinding
import kotlinx.coroutines.launch


class FullSimilarMoviesFragment : Fragment() {

    private var _binding: FragmentFullSimilarMoviesBinding? = null
    private val binding get() = _binding!!

    private val sharedSimilarVM: SharedSimilarMoviesViewModel by activityViewModels()
    private var similarMoviesAdapter = SimilarMoviesAdapter { movieId -> onItemClick(movieId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullSimilarMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSimilar.adapter = similarMoviesAdapter

        lifecycleScope.launch {
            similarMoviesAdapter.submitList(sharedSimilarVM.similarAdapter.value[0].items)
        }
    }

    //Клик по фильму, переход к инфо о фильме
    private fun onItemClick(item: Item) {
        val movieId = item.filmId!!
        val movieInfoFragment = MovieInfoFragment.newInstance(movieId = movieId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_full_similar_movies) {
            findNavController().navigate(
                R.id.action_navigation_full_similar_movies_to_navigation_movie_info,
                movieInfoFragment.arguments
            )
        }
    }
}