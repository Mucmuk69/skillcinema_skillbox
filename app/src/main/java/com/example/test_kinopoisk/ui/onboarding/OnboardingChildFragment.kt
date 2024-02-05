package com.example.test_kinopoisk.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentChildOnboardingBinding

const val ARG_OBJECT = "object"

class OnboardingChild : Fragment() {

    private var _binding: FragmentChildOnboardingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val imageView: ImageView = binding.ivOnboarding
            val textView: TextView = binding.tvOnboarding
            when (getInt(ARG_OBJECT)) {
                1 -> {
                    imageView.setImageResource(R.drawable.onboarding1_1)
                    textView.setText(R.string.onboardind1)
                }

                2 -> {
                    imageView.setImageResource(R.drawable.onboarding2_2)
                    textView.setText(R.string.onboardind2)
                }

                3 -> {
                    imageView.setImageResource(R.drawable.onboarding3_3)
                    textView.setText(R.string.onboardind3)
                }
            }
        }
    }
}