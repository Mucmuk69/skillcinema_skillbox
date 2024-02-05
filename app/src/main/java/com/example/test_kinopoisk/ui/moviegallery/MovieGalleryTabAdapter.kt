package com.example.test_kinopoisk.ui.moviegallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MovieGalleryTabAdapter(fragment: MovieGalleryFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = MovieGalleryChildFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT_IMAGE, position + 1)
        }
        return fragment
    }
}