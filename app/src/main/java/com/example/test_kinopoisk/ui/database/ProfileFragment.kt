package com.example.test_kinopoisk.ui.database

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.data_model_similar_movies.Item
import com.example.test_kinopoisk.App
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentProfileBinding
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val profileAdapter = ProfileAdapter { movieId -> onMovieClick(movieId) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerInterestingMovie.adapter = profileAdapter
        val movieDao: MovieDao = (activity?.application as App).db.movieDao()



    }

    //Клик по фильму, переход к инфо о фильме
    private fun onMovieClick(movie: MovieDatabase) {
        val movieId = movie.movieId
        val movieInfoFragment = MovieInfoFragment.newInstance(movieId = movieId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_profile) {
            findNavController().navigate(
                R.id.action_navigation_profile_to_navigation_movie_info,
                movieInfoFragment.arguments
            )
        }
    }

}