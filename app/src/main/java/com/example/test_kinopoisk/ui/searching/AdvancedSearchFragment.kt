package com.example.test_kinopoisk.ui.searching

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentAdvancedSearchBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getString(ARG_COUNTRY_KEY) != null)
        advancedSearchVM.country.value = (arguments?.getString(ARG_COUNTRY_KEY) ?: "Россия")
        Log.d("MyTag", "country args : ${advancedSearchVM.country.value}")

        if (arguments?.getString(ARG_GENRE_KEY) != null)
        advancedSearchVM.genre.value = (arguments?.getString(ARG_GENRE_KEY) ?: "Комедия")
        Log.d("MyTag", "genre args : ${advancedSearchVM.genre.value}")

        val choiceCountry = binding.tvChoiceCountry
        val choiceGenre = binding.tvChoiceGenre
        val seekBarRating = binding.seekbarRating
        val choiceRating = binding.tvChoiceRating

        choiceCountry.text = advancedSearchVM.country.value ?: "Россия"
        choiceGenre.text = advancedSearchVM.genre.value ?: "Комедия"

        seekBarRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress == 0) {
                    choiceRating.text = "Любой"
                } else {
                    choiceRating.text = progress.toString()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })

        choiceCountry.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_country)
        }

        choiceGenre.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_genre)
        }

    }
}