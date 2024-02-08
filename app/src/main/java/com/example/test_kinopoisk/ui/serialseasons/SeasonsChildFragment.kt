package com.example.test_kinopoisk.ui.serialseasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.test_kinopoisk.databinding.FragmentSeasonsChildBinding
import com.example.test_kinopoisk.ui.movieinfo.SharedSeasonsViewModel
import kotlinx.coroutines.launch

const val ARG_OBJECT_SEASONS = "object seasons"

class SeasonsChildFragment : Fragment() {

    private var _binding: FragmentSeasonsChildBinding? = null
    private val binding get() = _binding!!

    private val sharedVM: SharedSeasonsViewModel by activityViewModels()
    private val seasonsAdapter = SeasonsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeasonsChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerSeasons.adapter = seasonsAdapter
        //Отправляем в адаптер инфо о каждом сезоне
        arguments?.takeIf { it.containsKey(ARG_OBJECT_SEASONS) }?.apply {
            val index = getInt(ARG_OBJECT_SEASONS)
            lifecycleScope.launch {
                sharedVM.seasons.collect { listSeasons ->
                    seasonsAdapter.submitList(listSeasons[0]?.items?.get(index - 1)?.episodes)
                    //Количество серий в текущем сезоне
                    val quantityEpisodes = listSeasons[0]?.items?.get(index - 1)?.episodes?.size
                    //Текущий сезон
                    val thisSeason =
                        listSeasons[0]?.items?.get(index - 1)?.episodes?.get(0)?.seasonNumber
                    val pluralize =
                        quantityEpisodes?.let { pluralize(it, "серия", "серии", "серий") }
                    binding.tvThisSeason.text = "$thisSeason сезон, $pluralize"
                }
            }
        }
    }

    //Функция для настройки правильного окончания (сезон, сезона, сезонов)
    private fun pluralize(count: Int, singular: String, exclusion: String, plural: String): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> {
                "$count $singular"
            }

            count % 10 in 2..4 && count % 100 !in 12..14 -> {
                "$count $exclusion"
            }

            else -> {
                "$count $plural"
            }
        }
    }

}