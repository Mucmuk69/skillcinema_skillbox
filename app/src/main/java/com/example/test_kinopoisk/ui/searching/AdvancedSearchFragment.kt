package com.example.test_kinopoisk.ui.searching

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentAdvancedSearchBinding
import java.util.Calendar

class AdvancedSearchFragment : Fragment() {

    private var _binding: FragmentAdvancedSearchBinding? = null
    private val binding get() = _binding!!

    private val advancedSearchVM: AdvancedSearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdvancedSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getString(ARG_COUNTRY_KEY) != null) {
            advancedSearchVM.country.value = (arguments?.getString(ARG_COUNTRY_KEY))
        }

        if (arguments?.getString(ARG_GENRE_KEY) != null) {
            advancedSearchVM.genre.value = (arguments?.getString(ARG_GENRE_KEY))
        }

        if (arguments?.getInt(ARG_YEAR_FROM) != null && arguments?.getInt(ARG_YEAR_FROM) != 0) {
            advancedSearchVM.yearFrom.value = arguments?.getInt(ARG_YEAR_FROM)
        }

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if (arguments?.getInt(ARG_YEAR_TO) != null && arguments?.getInt(ARG_YEAR_TO) != 0) {
            advancedSearchVM.yearTo.value = arguments?.getInt(ARG_YEAR_TO)
        }

        val choiceCountry = binding.tvChoiceCountry
        val choiceGenre = binding.tvChoiceGenre
        val choiceYear = binding.tvChoiceYear
        val seekBarRatingFrom = binding.seekbarRatingFrom
        val seekBarRatingTo = binding.seekbarRatingTo
        val choiceRating = binding.tvChoiceRating
        val typeAll = binding.chipAll
        val typeFilms = binding.chipFilms
        val typeSerials = binding.chipSerials
        val chipGroup1 = binding.chipGroup1

        choiceCountry.text = advancedSearchVM.country.value ?: "Россия"
        choiceGenre.text = advancedSearchVM.genre.value ?: "Комедия"
        choiceYear.text =
            "с ${advancedSearchVM.yearFrom.value ?: 1900} до ${advancedSearchVM.yearTo.value ?: currentYear}"

        if (advancedSearchVM.ratingFrom.value == 0 || advancedSearchVM.ratingFrom.value == null &&
            advancedSearchVM.ratingTo.value == 0 || advancedSearchVM.ratingTo.value == null
        ) {
            choiceRating.text = "Любой"
        } else {
            choiceRating.text =
                "с ${advancedSearchVM.ratingFrom.value ?: 0} до ${advancedSearchVM.ratingTo.value ?: 0}"
        }
        seekBarFromFunc(seekBarRatingFrom, choiceRating)
        seekBarFromFunc(seekBarRatingTo, choiceRating)

        choiceCountry.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_country)
        }

        choiceGenre.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_genre)
        }

        choiceYear.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_year)
        }

        chipGroup1.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                typeAll.id -> {
                    advancedSearchVM.type.value = "ALL"
                    Log.d("MyTag", "ASF: type (ALL) ${advancedSearchVM.type.value}")
                }
                typeFilms.id -> {
                    advancedSearchVM.type.value = "FILM"
                    Log.d("MyTag", "ASF: type (FILM) ${advancedSearchVM.type.value}")
                }
                typeSerials.id -> {
                    advancedSearchVM.type.value = "TV_SERIES"
                    Log.d("MyTag", "ASF: type (SERIALS) ${advancedSearchVM.type.value}")
                }
            }
        }
        Log.d("MyTag", "ASF: type  ${advancedSearchVM.type.value}")
    }

    private fun seekBarFromFunc(seekBar: SeekBar, textView: TextView) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (seekBar) {
                    binding.seekbarRatingFrom -> {
                        advancedSearchVM.ratingFrom.value = progress
                        textView.text =
                            "с ${advancedSearchVM.ratingFrom.value ?: 0} до ${advancedSearchVM.ratingTo.value ?: 0}"
                    }

                    else -> {
                        advancedSearchVM.ratingTo.value = progress
                        textView.text =
                            "с ${advancedSearchVM.ratingFrom.value ?: 0} до ${advancedSearchVM.ratingTo.value ?: 0}"
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })
    }
}