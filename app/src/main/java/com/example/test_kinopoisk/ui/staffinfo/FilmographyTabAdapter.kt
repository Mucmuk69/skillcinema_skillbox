package com.example.test_kinopoisk.ui.staffinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FilmographyTabAdapter(fragment: FilmographyParentFragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = FilmographyChildFragment()
        fragment.arguments = Bundle().apply {
            putInt(FilmographyChildFragment.ARG_PROFESSION_KEY, position + 1)
        }
        return fragment
    }
}