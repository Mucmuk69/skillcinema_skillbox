package com.example.test_kinopoisk.ui.staffinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.data_model_staff.Film
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentFilmographyChildBinding
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment
import kotlinx.coroutines.launch


class FilmographyChildFragment : Fragment() {

    private var _binding: FragmentFilmographyChildBinding? = null
    private val binding get() = _binding!!

    private val sharedStaffInfoVM: SharedStaffInfoViewModel by activityViewModels()
    private val filmographyAdapter = FilmographyAdapter { filmId -> onItemClick(filmId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmographyChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerFilmography.adapter = filmographyAdapter
        arguments?.takeIf { it.containsKey(ARG_PROFESSION_KEY) }?.apply {
            lifecycleScope.launch {
                sharedStaffInfoVM.staffInfo.collect { filmInfo ->
                    if (filmInfo.isNotEmpty()) {
                        //Получаем список профессий
                        val listProfessionKeys = filmInfo[0]?.films
                            ?.mapNotNull { film -> film.professionKey }
                            ?.distinctBy { it }
                        //Получаем профессию из списка по текущей позиции TabLayout
                        val selectedKey = listProfessionKeys?.getOrNull(getInt(ARG_PROFESSION_KEY)-1)
                        //Передаем список фильмов в адаптер и фильтруем по профессии
                        filmographyAdapter.submitList(filmInfo[0]?.films
                            ?.filter { film ->
                                film.professionKey == selectedKey
                            }
                            ?.distinctBy { film -> film.nameRu ?: film.nameEn }
                        )
                    }
                }
            }
        }
    }

    //клик по фильму, переход к инфо о фильме
    private fun onItemClick(item: Film) {
        val movieId = item.filmId!!
        val movieInfoFragment = MovieInfoFragment.newInstance(movieId = movieId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_filmography) {
            findNavController().navigate(
                R.id.action_navigation_filmography_to_navigation_movie_info,
                movieInfoFragment.arguments
            )
        }
    }

    companion object {
        const val ARG_PROFESSION_KEY = "profession key"
    }
}