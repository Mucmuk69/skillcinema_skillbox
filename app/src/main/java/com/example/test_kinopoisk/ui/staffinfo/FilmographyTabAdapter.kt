package com.example.test_kinopoisk.ui.staffinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test_kinopoisk.ui.staffinfo.FilmographyChildFragment.Companion.ARG_PROFESSION_KEY

class FilmographyTabAdapter(fragment: FilmographyParentFragment, private val listKeys:List<String>?) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = listKeys?.size!!

    override fun createFragment(position: Int): Fragment {
        val fragment = FilmographyChildFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_PROFESSION_KEY, position + 1)
        }
        return fragment
    }
}