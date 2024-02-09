package com.example.test_kinopoisk.ui.moviegallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.test_kinopoisk.databinding.FragmentMovieGalleryChildBinding
import com.example.test_kinopoisk.ui.movieinfo.MovieImagesAdapter
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment.Companion.ARG_MOVIE_ID
import com.example.test_kinopoisk.ui.movieinfo.SharedMovieImagesViewModel
import kotlinx.coroutines.launch


class MovieGalleryChildFragment : Fragment() {

    private var _binding: FragmentMovieGalleryChildBinding? = null
    private val binding get() = _binding!!

    private val movieGalleryVM: MovieGalleryChildViewModel by activityViewModels()
    private val sharedImagesVM: SharedMovieImagesViewModel by activityViewModels()
    private val imageAdapter = MovieImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieGalleryChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerMovieImages.adapter = imageAdapter
        val movieId = arguments?.getInt(ARG_MOVIE_ID)
        Log.d("MyTag", "MGCF: movie id - $movieId")

        arguments?.takeIf { it.containsKey(ARG_OBJECT_IMAGE) }?.apply {
            when (getInt(ARG_OBJECT_IMAGE)) {
                //Кадры со съемок
                1 -> {
                    lifecycleScope.launch {
                        sharedImagesVM.movieImages.collect { movieImages ->
                            if (movieImages[0]?.items?.isNotEmpty() == true) {
                                imageAdapter.submitList(movieImages[0]?.items)
                            }
                        }
                    }
                }

                //Кадры из фильма
                2 -> {
                    lifecycleScope.launch {
                        movieGalleryVM.getMovieImages(movieId = movieId!!, type = STILL)
                        movieGalleryVM.isLoadingImagesStill.collect { loadingImage ->
                            if (loadingImage) {
                                movieGalleryVM.movieImagesStill.collect { movieImages ->
                                    if (movieImages[0]?.items?.isNotEmpty() == true) {
                                        imageAdapter.submitList(movieImages[0]?.items)
                                    }
                                }
                            }
                        }
                    }
                }

                //Фан-арты
                3 -> {
                    lifecycleScope.launch {
                        movieGalleryVM.getMovieImages(movieId = movieId!!, type = FAN_ART)
                        movieGalleryVM.isLoadingImagesFanArt.collect { loadingImage ->
                            if (loadingImage) {
                                movieGalleryVM.movieImagesFanArt.collect { movieImages ->
                                    if (movieImages[0]?.items?.isNotEmpty() == true) {
                                        imageAdapter.submitList(movieImages[0]?.items)
                                    }
                                }
                            }
                        }
                    }
                }

                //Концепт-арты
                4 -> {
                    lifecycleScope.launch {
                        movieGalleryVM.getMovieImages(movieId = movieId!!, type = CONCEPT)
                        movieGalleryVM.isLoadingImagesConcept.collect { loadingImage ->
                            if (loadingImage) {
                                movieGalleryVM.movieImagesConcept.collect { movieImages ->
                                    if (movieImages[0]?.items?.isNotEmpty() == true) {
                                        imageAdapter.submitList(movieImages[0]?.items)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val ARG_OBJECT_IMAGE = "image object"
        const val STILL = "STILL"
        const val FAN_ART = "FAN_ART"
        const val CONCEPT = "CONCEPT"
    }
}