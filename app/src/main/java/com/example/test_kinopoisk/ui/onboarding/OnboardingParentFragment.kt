package com.example.test_kinopoisk.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentParentOnboardingBinding

class OnboardingParentFragment : Fragment() {

    private var _binding: FragmentParentOnboardingBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: OnboardingAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val springDotsIndicator = binding.springDotsIndicator
        val buttonSkip = binding.buttonSkip

        adapter = OnboardingAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = adapter
        springDotsIndicator.attachTo(viewPager)

        buttonSkip.isClickable = true
        buttonSkip.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_loading_collections)
        }
    }
}



