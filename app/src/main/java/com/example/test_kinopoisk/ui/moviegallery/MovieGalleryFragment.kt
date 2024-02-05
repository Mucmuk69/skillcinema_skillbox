package com.example.test_kinopoisk.ui.moviegallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.test_kinopoisk.databinding.FragmentMovieGalleryBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MovieGalleryFragment : Fragment() {

    private var _binding: FragmentMovieGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieGalleryTabAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabNames: Array<String> = arrayOf(
        "Со съемок",
        "Кадры из фильма",
        "Фан-арты",
        "Концепт-арты",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieGalleryTabAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = adapter

        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

    }

}