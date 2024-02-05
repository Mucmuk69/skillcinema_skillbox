package com.example.test_kinopoisk.ui.serialseasons

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entity.data_model_serial_seasons.Seasons
import kotlinx.coroutines.flow.MutableStateFlow

class SeasonStateAdapter(
    fragment: SeasonsParentFragment,
    private val seasons: MutableStateFlow<List<Seasons?>>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return seasons.value[0]?.total ?: 1
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = SeasonsChildFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT_SEASONS, position + 1)
        }
        return fragment
    }
}