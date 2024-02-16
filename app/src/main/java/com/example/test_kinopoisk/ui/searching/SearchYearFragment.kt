package com.example.test_kinopoisk.ui.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentSearchYearBinding
import java.util.Calendar

const val ARG_YEAR_FROM = "year from"
const val ARG_YEAR_TO = "year to"

class SearchYearFragment : Fragment() {

    private var _binding: FragmentSearchYearBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchYearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val yearPickerFrom = binding.pickerFrom
        val yearPickerTo = binding.pickerTo
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        yearPickerFrom.minValue = 1900
        yearPickerTo.minValue = 1900
        yearPickerFrom.maxValue = currentYear
        yearPickerTo.maxValue = currentYear
        yearPickerFrom.value = yearPickerFrom.minValue
        yearPickerTo.value = currentYear

        yearPickerFrom.setOnValueChangedListener { _, oldVal, newVal ->
            if (oldVal != newVal) {
                yearPickerFrom.value = newVal
            }
        }
        yearPickerTo.setOnValueChangedListener { _, oldVal, newVal ->
            if (oldVal != newVal) {
                yearPickerTo.value = newVal
            }
        }

        binding.buttonChoice.setOnClickListener {
            val advancedFragment = AdvancedSearchFragment()
            val bundle = Bundle().apply {
                putInt(ARG_YEAR_FROM, yearPickerFrom.value)
                putInt(ARG_YEAR_TO, yearPickerTo.value)
            }
            advancedFragment.arguments = bundle
            findNavController().navigate(
                R.id.action_navigation_search_year_to_navigation_advanced_search,
                bundle
            )
        }
    }
}