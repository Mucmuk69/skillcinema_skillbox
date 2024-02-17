package com.example.test_kinopoisk.ui.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentSearchYearBinding
import java.util.Calendar

const val ARG_YEAR_FROM_KEY = "year from"
const val ARG_YEAR_TO_KEY = "year to"

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

        //Устанавливаем минимальное и максимальное значение года
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

        //Передаю значение года "с" и "до" в другой фрагмент
        binding.buttonChoice.setOnClickListener {
            if (yearPickerFrom.value < yearPickerTo.value) {
                val advancedFragment = AdvancedSearchFragment()
                val bundle = Bundle().apply {
                    putInt(ARG_YEAR_FROM_KEY, yearPickerFrom.value)
                    putInt(ARG_YEAR_TO_KEY, yearPickerTo.value)
                }
                advancedFragment.arguments = bundle
                findNavController().navigate(
                    R.id.action_navigation_search_year_to_navigation_advanced_search,
                    bundle
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Период С должен быть меньше или равен периоду До",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}