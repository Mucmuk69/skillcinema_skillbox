package com.example.test_kinopoisk.ui.onboarding

import android.content.Context
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

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean(FIRST_RUN, true)
        val springDotsIndicator = binding.springDotsIndicator
        val navController = findNavController()

        if (isFirstRun) {
            adapter = OnboardingAdapter(this)
            viewPager = binding.pager
            viewPager.adapter = adapter
            springDotsIndicator.attachTo(viewPager)

            val editor = sharedPreferences.edit()
            editor.putBoolean(FIRST_RUN, false)
            editor.apply()
        } else {
            navController.navigate(R.id.action_navigation_onboarding_to_navigation_dashboard)
        }
        val buttonSkip = binding.buttonSkip
        buttonSkip.isClickable = true
        buttonSkip.setOnClickListener {
            navController.navigate(R.id.action_navigation_onboarding_to_navigation_dashboard)
        }
    }

    private companion object {
        const val FIRST_RUN = "isFirstRun"
    }
}



