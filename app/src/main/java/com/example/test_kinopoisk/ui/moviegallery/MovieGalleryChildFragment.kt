package com.example.test_kinopoisk.ui.moviegallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.test_kinopoisk.databinding.FragmentMovieGalleryChildBinding
import com.example.test_kinopoisk.ui.movieinfo.MovieImagesAdapter
import com.example.test_kinopoisk.ui.movieinfo.SharedMovieImagesViewModel
import kotlinx.coroutines.launch

const val ARG_OBJECT_IMAGE = "image object"

class MovieGalleryChildFragment : Fragment() {

    private var _binding: FragmentMovieGalleryChildBinding? = null
    private val binding get() = _binding!!

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

        arguments?.takeIf { it.containsKey(ARG_OBJECT_IMAGE) }?.apply {
            when (getInt(ARG_OBJECT_IMAGE)) {
                1 -> {
                    lifecycleScope.launch {
                        sharedImagesVM.movieImages.collect {
                            imageAdapter.submitList(it[0]?.items)
                        }
                    }
                }

                2 -> {}
                3 -> {}
                4 -> {}
            }
        }
    }
}