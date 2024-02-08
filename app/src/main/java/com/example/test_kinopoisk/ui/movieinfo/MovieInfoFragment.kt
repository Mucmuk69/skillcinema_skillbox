package com.example.test_kinopoisk.ui.movieinfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.entity.data_model_similar_movies.Item
import com.example.domain.entity.data_model_staff.ListStaff
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentMovieInfoBinding
import com.example.test_kinopoisk.ui.auxiliaryfunctions.expandText
import com.example.test_kinopoisk.ui.auxiliaryfunctions.pluralize
import com.example.test_kinopoisk.ui.staffinfo.StaffInfoFragment
import kotlinx.coroutines.launch


class MovieInfoFragment : Fragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieInfoViewModel by activityViewModels()
    private val sharedStaffVM: SharedStaffViewModel by activityViewModels()
    private val sharedSeasonsVM: SharedSeasonsViewModel by activityViewModels()
    private val sharedImagesVM: SharedMovieImagesViewModel by activityViewModels()
    private val sharedSimilarVM: SharedSimilarMoviesViewModel by activityViewModels()

    private val listActorsAdapter = ListStaffAdapter { staffId -> onItemClick(staffId) }
    private val listStaffAdapter = ListStaffAdapter { staffId -> onItemClick(staffId) }
    private val movieImagesAdapter = MovieImagesAdapter()
    private val similarMoviesAdapter = SimilarMoviesAdapter { movieId -> onItemClick(movieId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerActors.adapter = listActorsAdapter
        binding.recyclerStaff.adapter = listStaffAdapter
        binding.recyclerMovieImages.adapter = movieImagesAdapter
        binding.recyclerSimilarFilms.adapter = similarMoviesAdapter

        val movieId = arguments?.getInt(ARG_MOVIE_ID)
        val poster = binding.ivPoster
        val description = binding.tvDescription
        val actorsCount = binding.tvCountActors
        val staffCount = binding.tvCountStaff
        val allEpisodes = binding.tvAllEpisodes
        val seasons = binding.tvSeason
        val episodes = binding.tvEpisodes
        val imagesCount = binding.tvCountImages
        val similarCount = binding.tvSimilarFilms
        val llSimilarMovies = binding.llSimilarMovies
        val recyclerSimilar = binding.recyclerSimilarFilms

        //Получаем id фильма, делаем запрос, получаем инфо фильма
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (movieId != null) {
                    viewModel.getFilmInfo(movieId)
                }
                //Показываем инфо фильма на экране
                viewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        viewModel.listFilmInfo.collect {
                            sharedSeasonsVM.nameSerial.value =
                                it[0].nameRu ?: it[0].nameEn ?: it[0].nameOriginal ?: ""
                            description.text = it[0].description ?: ""
                            Glide
                                .with(poster.context)
                                .load(it[0].posterUrl)
                                .into(poster)
                        }
                    }
                }
            }
        }

        //Получение инфо о сезонах сериала
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        viewModel.listFilmInfo.collect { filmInfo ->
                            if (filmInfo[0].type == "TV_SERIES" || filmInfo[0].type == "MINI_SERIES") {
                                binding.llAllEpisodes1.visibility = View.VISIBLE
                                binding.llAllEpisodes2.visibility = View.VISIBLE
                                if (binding.llAllEpisodes1.visibility == View.VISIBLE) {
                                    if (movieId != null) {
                                        viewModel.getSerialSeasonsInfo(movieId)
                                    }
                                    viewModel.isLoadingSeasons.collect { loadingSeasons ->
                                        if (loadingSeasons) {
                                            viewModel.serialSeasons.collect { seasonsAndEpisodes ->
                                                sharedSeasonsVM.seasons.value = seasonsAndEpisodes
                                                //Количество сезонов в сериале
                                                seasons.text = seasonsAndEpisodes[0]?.total?.let {
                                                    pluralize(
                                                        it,
                                                        "сезон",
                                                        "сезона",
                                                        "сезонов"
                                                    )
                                                }
                                                Log.d("MyTag", "MIF: seasons -  ${seasons.text}")
                                                //Подсчет общего количества серий в сериале
                                                val sumEpisodes =
                                                    seasonsAndEpisodes.sumOf {
                                                        it?.items?.sumOf { season ->
                                                            season.episodes.size
                                                        } ?: 1
                                                    }
                                                episodes.text =
                                                    pluralize(
                                                        sumEpisodes,
                                                        "серия",
                                                        "серии",
                                                        "серий"
                                                    )
                                                Log.d("MyTag", "MIF: episodes -  ${episodes.text}")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

//Запрос списка актеров и отправка информации в адаптер
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (movieId != null) {
                    viewModel.getListStaff(movieId)
                }
                viewModel.isLoadingStaff.collect { isLoadingStaff ->
                    if (isLoadingStaff) {
                        viewModel.listActors.collect { listActors ->
                            sharedStaffVM.actors.value = listActors
                            listActorsAdapter.submitList(listActors)
                            actorsCount.text = listActors.size.toString()
                            Log.d("MyTag", "MIF: actors for adapter -  $listActors")
                        }
                    }
                }
            }
        }

        //Отправка списка людей "Над фильмом работали" в адаптер
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingStaff.collect { isLoadingStaff ->
                    if (isLoadingStaff) {
                        viewModel.listStaff.collect { listStaff ->
                            sharedStaffVM.staff.value = listStaff
                            listStaffAdapter.submitList(listStaff)
                            staffCount.text = listStaff.size.toString()
                            Log.d("MyTag", "MIF: staff for adapter -  $listStaff")
                        }
                    }
                }
            }
        }

        //Получение и отправка в адаптер кадров из фильма и т.п.
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (movieId != null) {
                    viewModel.getMovieImages(movieId, "SHOOTING")
                }
                viewModel.isLoadingImages.collect { loadingImages ->
                    if (loadingImages) {
                        viewModel.movieImages.collect {
                            movieImagesAdapter.submitList(it[0]?.items)
                            imagesCount.text = it[0]?.total.toString()
                            sharedImagesVM.movieImages.value = it
                        }
                    }
                }
            }
        }

        //Получение списка похожих фильмов и отправка их в адаптер
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (movieId != null) {
                    viewModel.getSimilarMovies(movieId)
                }
                viewModel.isLoadingSimilarMovies.collect { loadingSimilarMovies ->
                    if (loadingSimilarMovies) {
                        viewModel.listSimilarMovies.collect { similarMovies->
                            //Проверяем, есть ли похожие фильмы, если есть - делаем разметку видимой
                            if (similarMovies[0].items.isNotEmpty()) {
                                llSimilarMovies.visibility = View.VISIBLE
                                recyclerSimilar.visibility = View.VISIBLE
                                similarMoviesAdapter.submitList(similarMovies[0].items)
                                sharedSimilarVM.similarAdapter.value = similarMovies
                                similarCount.text = similarMovies[0].total.toString()
                                if (similarMovies[0].total!! >= 20) {
                                    similarCount.visibility = View.VISIBLE
                                    similarCount.isClickable = true
                                    similarCount.isFocusable = true
                                }
                            }
                        }
                    }
                }
            }
        }

        similarCount.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_full_similar_movies
            )
        }

        imagesCount.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_movie_gallery
            )
        }

        //Переход на экран всех сезонов и эпизодов сериала
        allEpisodes.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_seasons
            )
        }

        //Переход на экран всех актеров
        actorsCount.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("staff", "В фильме снимались")
            val destinationFragment = FullListStaffFragment()
            destinationFragment.arguments = bundle
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_full_list_staff,
                bundle
            )
        }

        //Переход на экран всего персонала
        staffCount.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("staff", "Над фильмом работали")
            val destinationFragment = FullListStaffFragment()
            destinationFragment.arguments = bundle
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_full_list_staff,
                bundle
            )
        }

//Раскрытие текста описания фильма при нажатии на него
        description.setOnClickListener {
            expandText(description)
        }
    }

    //Клик по актеру, переход к инфо об актере
    private fun onItemClick(item: ListStaff) {
        val staffId = item.staffId!!
        val staffInfoFragment = StaffInfoFragment.newInstance(staffId = staffId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_movie_info) {
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_staff_info,
                staffInfoFragment.arguments
            )
        }
    }

    //Функция для передачи и получении id фильма
    companion object {
        private const val ARG_MOVIE_ID = "movieId"
        fun newInstance(movieId: Int): MovieInfoFragment {
            val fragment = MovieInfoFragment()
            val args = Bundle()
            args.putInt(ARG_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }

    //Клик по фильму, переход к инфо о фильме
    private fun onItemClick(item: Item) {
        val movieId = item.filmId!!
        val movieInfoFragment = newInstance(movieId = movieId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_movie_info) {
            findNavController().navigate(
                R.id.navigation_movie_info,
                movieInfoFragment.arguments
            )
        }
    }
//    private fun bundleFunc(fragment: Fragment, myData: Parcelable){
//        val bundle = Bundle()
//        bundle.putParcelable("myClass", myData)
//        fragment.arguments = bundle
//    }
}