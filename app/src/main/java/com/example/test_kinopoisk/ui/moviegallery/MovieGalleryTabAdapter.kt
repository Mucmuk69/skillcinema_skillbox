package com.example.test_kinopoisk.ui.moviegallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test_kinopoisk.ui.moviegallery.MovieGalleryChildFragment.Companion.ARG_OBJECT_IMAGE
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment.Companion.ARG_MOVIE_ID

class MovieGalleryTabAdapter(fragment: MovieGalleryFragment, private val movieId: Int) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = MovieGalleryChildFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT_IMAGE, position + 1)
            putInt(ARG_MOVIE_ID, movieId)
        }
        return fragment
    }
}