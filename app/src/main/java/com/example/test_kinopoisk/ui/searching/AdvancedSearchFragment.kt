package com.example.test_kinopoisk.ui.searching

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentAdvancedSearchBinding
import com.google.android.material.chip.Chip
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
        } else {
            advancedSearchVM.country.value = RUSSIA
        }

        if (arguments?.getString(ARG_GENRE_KEY) != null) {
            advancedSearchVM.genre.value = (arguments?.getString(ARG_GENRE_KEY))
        } else {
            advancedSearchVM.genre.value = COMEDY
        }

        if (arguments?.getInt(ARG_YEAR_FROM_KEY) != null && arguments?.getInt(ARG_YEAR_FROM_KEY) != ZERO) {
            advancedSearchVM.yearFrom.value = arguments?.getInt(ARG_YEAR_FROM_KEY)
        } else {
            advancedSearchVM.yearFrom.value = MIN_YEAR
        }

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if (arguments?.getInt(ARG_YEAR_TO_KEY) != null && arguments?.getInt(ARG_YEAR_TO_KEY) != ZERO) {
            advancedSearchVM.yearTo.value = arguments?.getInt(ARG_YEAR_TO_KEY)
        } else {
            advancedSearchVM.yearTo.value = currentYear
        }

        if (advancedSearchVM.ratingFrom.value == null) {
            advancedSearchVM.ratingFrom.value = ZERO
        }
        if (advancedSearchVM.ratingTo.value == null) {
            advancedSearchVM.ratingTo.value = TEN
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
        val orderDate = binding.chipDate
        val orderPopular = binding.chipPopular
        val orderRating = binding.chipRating
        val buttonSearch = binding.buttonSearch

        choiceCountry.text = advancedSearchVM.country.value ?: RUSSIA
        choiceGenre.text = advancedSearchVM.genre.value ?: COMEDY
        choiceYear.text =
            "с ${advancedSearchVM.yearFrom.value ?: MIN_YEAR} до ${advancedSearchVM.yearTo.value ?: currentYear}"

        if (advancedSearchVM.ratingFrom.value == ZERO || advancedSearchVM.ratingFrom.value == null &&
            advancedSearchVM.ratingTo.value == ZERO || advancedSearchVM.ratingTo.value == null
        ) {
            choiceRating.text = ANY_RATING
        } else {
            choiceRating.text =
                "с ${advancedSearchVM.ratingFrom.value ?: ZERO} до ${advancedSearchVM.ratingTo.value ?: ZERO}"
        }
        seekBarRatingFromTo(seekBarRatingFrom, choiceRating)
        seekBarRatingFromTo(seekBarRatingTo, choiceRating)

        //Выбор страны
        choiceCountry.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_country)
        }

        //Выбор жанра
        choiceGenre.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_genre)
        }

        //Выбор года
        choiceYear.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_advanced_search_to_navigation_search_year)
        }

        chipForType(typeAll)
        chipForType(typeFilms)
        chipForType(typeSerials)
        chipForOrder(orderDate)
        chipForOrder(orderPopular)
        chipForOrder(orderRating)

        when (advancedSearchVM.type.value) {
            TYPE_ALL -> {
                typeAll.isChecked = true
                typeAll.isClickable = false
            }

            TYPE_FILM -> {
                typeFilms.isChecked = true
                typeFilms.isClickable = false
            }

            TYPE_TV_SERIES -> {
                typeSerials.isChecked = true
                typeSerials.isClickable = false
            }
        }

        when (advancedSearchVM.order.value) {
            ORDER_DATE -> {
                orderDate.isChecked = true
                orderDate.isClickable = false
            }

            ORDER_POPULAR -> {
                orderPopular.isChecked = true
                orderPopular.isClickable = false
            }

            ORDER_RATING -> {
                orderRating.isChecked = true
                orderRating.isClickable = false
            }
        }

        //Искать по выбранным фильтрам и передача в другой фрагмент выбранных параметров
        buttonSearch.setOnClickListener {
            Log.d("MyTag", "advancedSearching - country: ${advancedSearchVM.country.value}")
            Log.d("MyTag", "advancedSearching - genre: ${advancedSearchVM.genre.value}")
            Log.d("MyTag", "advancedSearching - order: ${advancedSearchVM.order.value}")
            Log.d("MyTag", "advancedSearching - type: ${advancedSearchVM.type.value}")
            Log.d("MyTag", "advancedSearching - ratingFrom: ${advancedSearchVM.ratingFrom.value}")
            Log.d("MyTag", "advancedSearching - ratingTo: ${advancedSearchVM.ratingTo.value}")
            Log.d("MyTag", "advancedSearching - yearFrom: ${advancedSearchVM.yearFrom.value}")
            Log.d("MyTag", "advancedSearching - yearTo: ${advancedSearchVM.yearTo.value}")
            if (advancedSearchVM.country.value != null && advancedSearchVM.genre.value != null &&
                advancedSearchVM.order.value != null && advancedSearchVM.type.value != null
                && advancedSearchVM.ratingFrom.value != null && advancedSearchVM.ratingTo.value != null
                && advancedSearchVM.yearFrom.value != null && advancedSearchVM.yearTo.value != null
            ) {
                val searchingFragment = SearchingFragment()
                val bundle = Bundle().apply {
                    putString(ARG_COUNTRY, advancedSearchVM.country.value ?: "Россия")
                    putString(ARG_GENRE, advancedSearchVM.genre.value ?: "комедия")
                    putString(ARG_TYPE, advancedSearchVM.type.value ?: "ALL")
                    putString(ARG_ORDER, advancedSearchVM.order.value ?: "RATING")
                    putInt(ARG_YEAR_FROM, advancedSearchVM.yearFrom.value ?: 1000)
                    putInt(ARG_YEAR_TO, advancedSearchVM.yearTo.value ?: 3000)
                    putInt(ARG_RATING_FROM, advancedSearchVM.ratingFrom.value ?: 0)
                    putInt(ARG_RATING_TO, advancedSearchVM.ratingTo.value ?: 10)
                }
                searchingFragment.arguments = bundle
                findNavController().navigate(
                    R.id.action_navigation_advanced_search_to_navigation_searching,
                    bundle
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Выберите все представленные параметры(страна, жанр, год, рейтинг, тип подборки, тип сортировки)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //Функция для сохранения значения поиска по типу и слушатель для Chip
    private fun chipForType(chip: Chip) {
        when (chip) {
            binding.chipAll -> {
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        advancedSearchVM.type.value = TYPE_ALL
                        buttonView.isClickable = false
                    } else {
                        buttonView.isClickable = true
                    }
                }
            }

            binding.chipFilms -> {
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        advancedSearchVM.type.value = TYPE_FILM
                        buttonView.isClickable = false
                    } else {
                        buttonView.isClickable = true
                    }
                }
            }

            binding.chipSerials -> {
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        advancedSearchVM.type.value = TYPE_TV_SERIES
                        buttonView.isClickable = false
                    } else {
                        buttonView.isClickable = true
                    }
                }
            }
        }
    }

    //Функция для сохранения значения поиска по типу и слушатель для Chip
    private fun chipForOrder(chip: Chip) {
        when (chip) {
            binding.chipDate -> {
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        advancedSearchVM.order.value = ORDER_DATE
                        buttonView.isClickable = false
                    } else {
                        buttonView.isClickable = true
                    }
                }
            }

            binding.chipPopular -> {
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        advancedSearchVM.order.value = ORDER_POPULAR
                        buttonView.isClickable = false
                    } else {
                        buttonView.isClickable = true
                    }
                }
            }

            binding.chipRating -> {
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        advancedSearchVM.order.value = ORDER_RATING
                        buttonView.isClickable = false
                    } else {
                        buttonView.isClickable = true
                    }
                }
            }
        }
    }

    //Функция для сохранения прогресса у seekBar для поиска по рейтингу и слушатель seekBar
    private fun seekBarRatingFromTo(seekBar: SeekBar, textView: TextView) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (seekBar) {
                    binding.seekbarRatingFrom -> {
                        advancedSearchVM.ratingFrom.value = progress
                        textView.text =
                            "с ${advancedSearchVM.ratingFrom.value ?: ZERO} до ${advancedSearchVM.ratingTo.value ?: ZERO}"
                    }

                    else -> {
                        advancedSearchVM.ratingTo.value = progress
                        textView.text =
                            "с ${advancedSearchVM.ratingFrom.value ?: ZERO} до ${advancedSearchVM.ratingTo.value ?: ZERO}"
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

    companion object {
        private const val TYPE_ALL = "ALL"
        private const val TYPE_FILM = "FILM"
        private const val TYPE_TV_SERIES = "TV_SERIES"
        private const val COMEDY = "комедия"
        private const val RUSSIA = "Россия"
        private const val MIN_YEAR = 1900
        private const val ANY_RATING = "Любой"
        private const val ZERO = 0
        private const val TEN = 10
        private const val ORDER_DATE = "YEAR"
        private const val ORDER_POPULAR = "NUM_VOTE"
        private const val ORDER_RATING = "RATING"

        const val ARG_TYPE = "type"
        const val ARG_ORDER = "order"
        const val ARG_COUNTRY = "country"
        const val ARG_GENRE = "genre"
        const val ARG_YEAR_FROM = "year from"
        const val ARG_YEAR_TO = "year to"
        const val ARG_RATING_FROM = "rating from"
        const val ARG_RATING_TO = "rating to"
    }
}