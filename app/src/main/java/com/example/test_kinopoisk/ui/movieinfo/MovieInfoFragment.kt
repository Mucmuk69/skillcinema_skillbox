package com.example.test_kinopoisk.ui.movieinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.example.test_kinopoisk.ui.moviegallery.MovieGalleryFragment
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

        initDB()

        binding.recyclerActors.adapter = listActorsAdapter
        binding.recyclerStaff.adapter = listStaffAdapter
        binding.recyclerMovieImages.adapter = movieImagesAdapter
        binding.recyclerSimilarFilms.adapter = similarMoviesAdapter

        val movieId = arguments?.getInt(ARG_MOVIE_ID)
        Log.d("MyTag", "MIF: movie id - $movieId")
        val poster = binding.ivPoster
        val description = binding.tvDescription
        val actorsCount = binding.tvCountActors
        val staffCount = binding.tvCountStaff
        val allEpisodes = binding.tvAllEpisodes
        val seasons = binding.tvSeason
        val episodes = binding.tvEpisodes
        val imagesCount = binding.tvCountImages
        val llMovieImages = binding.llMovieImages
        val recyclerImageMovies = binding.recyclerMovieImages
        val similarCount = binding.tvSimilarFilms
        val llSimilarMovies = binding.llSimilarMovies
        val recyclerSimilar = binding.recyclerSimilarFilms

        //Получаем id фильма, делаем запрос, получаем инфо фильма
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getFilmInfo(movieId!!)
                //Показываем инфо фильма на экране
                viewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        viewModel.listFilmInfo.collect { filmInfo ->
                            sharedSeasonsVM.nameSerial.value =
                                filmInfo[0].nameRu ?: filmInfo[0].nameEn ?: filmInfo[0].nameOriginal
                                        ?: ""
                            description.text = filmInfo[0].description ?: ""
                            Glide
                                .with(poster.context)
                                .load(filmInfo[0].posterUrl)
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
                                    viewModel.getSerialSeasonsInfo(movieId!!)
                                    Log.d("MyTag", "MIF: movie id in func seasons - $movieId")
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
                                                    seasonsAndEpisodes.sumOf { seasons ->
                                                        seasons?.items?.sumOf { season ->
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
                viewModel.getListStaff(movieId!!)
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
                viewModel.getMovieImages(movieId!!, "SHOOTING")
                viewModel.isLoadingImages.collect { loadingImages ->
                    if (loadingImages) {
                        viewModel.movieImages.collect { movieImages ->
                            //Если есть кадры, то делаем видимыми элементы разметки
                            if (movieImages[0]?.items?.isNotEmpty() == true) {
                                visibilityLayout(llMovieImages, recyclerImageMovies)
                                movieImagesAdapter.submitList(movieImages[0]?.items)
                                sharedImagesVM.movieImages.value = movieImages
                                imagesCount.text = movieImages[0]?.total.toString()
                                //Если total не 0, то делаем его кликабельным для перехода на другой фрагмент
                                if (movieImages[0]?.total != null || movieImages[0]?.total!! > 0) {
                                    isClickableAndVisibleTotalCount(imagesCount)
                                }
                            }
                        }
                    }
                }
            }
        }

        //Получение списка похожих фильмов и отправка их в адаптер
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSimilarMovies(movieId!!)
                viewModel.isLoadingSimilarMovies.collect { loadingSimilarMovies ->
                    if (loadingSimilarMovies) {
                        viewModel.listSimilarMovies.collect { similarMovies ->
                            //Проверяем, есть ли похожие фильмы, если есть - делаем разметку видимой
                            if (similarMovies[0].items.isNotEmpty()) {
                                visibilityLayout(llSimilarMovies, recyclerSimilar)
                                similarMoviesAdapter.submitList(similarMovies[0].items)
                                sharedSimilarVM.similarAdapter.value = similarMovies
                                similarCount.text = similarMovies[0].total.toString()
                                if (similarMovies[0].total!! >= 20) {
                                    isClickableAndVisibleTotalCount(similarCount)
                                }
                            }
                        }
                    }
                }
            }
        }

        //Переход на экран всех похожих фильмов
        similarCount.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_full_similar_movies
            )
        }

        //Переход на экран всех кадров т.п.
        imagesCount.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(ARG_MOVIE_ID, movieId!!)
            val movieGalleryFragment = MovieGalleryFragment()
            movieGalleryFragment.arguments = bundle
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_movie_gallery,
                bundle
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
            val fullListStaffFragment = FullListStaffFragment()
            fullListStaffFragment.arguments = bundle
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_full_list_staff,
                bundle
            )
        }

        //Переход на экран всего персонала
        staffCount.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("staff", "Над фильмом работали")
            val fullListStaffFragment = FullListStaffFragment()
            fullListStaffFragment.arguments = bundle
            findNavController().navigate(
                R.id.action_navigation_movie_info_to_navigation_full_list_staff,
                bundle
            )
        }
        var isFavorite = false
        //Добавить фильм в коллекцию "Любимые"
        binding.ivLike.setOnClickListener {
            isFavorite = if (!isFavorite) {
                viewModel.insertMovie(binding.ivLike) {}
                true
            } else {
                viewModel.deleteMovie(binding.ivLike) {}
                false
            }
        }
        //Добавить фильм в коллекцию "Хочу посмотреть"
        binding.ivReadyToView.setOnClickListener {

        }
        //Добавить фильм в коллекцию "Просмотрены"
        binding.ivViewed.setOnClickListener {

        }

        //Поделиться ссылкой на фильм
        binding.ivShare.setOnClickListener {
            lifecycleScope.launch {
                viewModel.listFilmInfo.collect { filmInfo ->
                    val webUrl = filmInfo[0].webUrl
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, webUrl)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
            }
        }
        //Добавить в свою коллекцию\создать свою коллекцию
        binding.ivDots.setOnClickListener {

        }

//Раскрытие текста описания фильма при нажатии на него
        description.setOnClickListener {
            expandText(description)
        }
    }

    private fun initDB() {
        viewModel.initDatabase()
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

    private fun isClickableAndVisibleTotalCount(view: View) {
        view.visibility = View.VISIBLE
        view.isClickable = true
        view.isFocusable = true
    }

    private fun visibilityLayout(view1: View, view2: View) {
        view1.visibility = View.VISIBLE
        view2.visibility = View.VISIBLE
    }

    //Функция для передачи и получении id фильма
    companion object {
        const val ARG_MOVIE_ID = "movieId"
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

    //Функция для скрытия текста описания фильма
    private fun expandText(textView: TextView) {
        textView.maxLines = Integer.MAX_VALUE
        textView.ellipsize = null
    }
//    private fun bundleFunc(fragment: Fragment, myData: Parcelable){
//        val bundle = Bundle()
//        bundle.putParcelable("myClass", myData)
//        fragment.arguments = bundle
//    }
}