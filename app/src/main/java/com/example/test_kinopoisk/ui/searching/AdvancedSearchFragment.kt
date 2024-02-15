package com.example.test_kinopoisk.ui.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentAdvancedSearchBinding


class AdvancedSearchFragment : Fragment() {

    private var _binding: FragmentAdvancedSearchBinding? = null
    private val binding get() = _binding!!

    private var country: String = "Россия"
    private var genre: String = "Комедия"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdvancedSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.getString("ARG_COUNTRY_KEY")?.let { country = it }
        savedInstanceState?.getString("ARG_GENRE_KEY")?.let { genre = it }

        val choiceCountry = binding.tvChoiceCountry
        val choiceGenre = binding.tvChoiceGenre
        val seekBarRating = binding.seekbarRating
        val choiceRating = binding.tvChoiceRating

        choiceCountry.text = arguments?.getString(ARG_COUNTRY_KEY) ?: country
        choiceGenre.text = arguments?.getString(ARG_GENRE_KEY) ?: genre
        country = choiceCountry.text.toString()
        genre = choiceGenre.text.toString()

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("ARG_COUNTRY_KEY", country)
        outState.putString("ARG_GENRE_KEY", genre)
        super.onSaveInstanceState(outState)
    }
}