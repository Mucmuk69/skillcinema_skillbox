package com.example.test_kinopoisk.ui.serialseasons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.test_kinopoisk.databinding.FragmentSeasonsParentBinding
import com.example.test_kinopoisk.ui.movieinfo.SharedSeasonsViewModel
import kotlinx.coroutines.launch

class SeasonsParentFragment : Fragment() {

    private var _binding: FragmentSeasonsParentBinding? = null
    private val binding get() = _binding!!

    private val sharedVM: SharedSeasonsViewModel by activityViewModels()
    private lateinit var stateAdapter: SeasonStateAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeasonsParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            sharedVM.nameSerial.collect{title->
                binding.titleSerial.text = title
            }
        }

        val springDotsIndicator = binding.springDotsIndicator
        stateAdapter = SeasonStateAdapter(this, sharedVM.seasons)
        viewPager = binding.viewPager
        viewPager.adapter = stateAdapter
        springDotsIndicator.attachTo(viewPager)
    }

}