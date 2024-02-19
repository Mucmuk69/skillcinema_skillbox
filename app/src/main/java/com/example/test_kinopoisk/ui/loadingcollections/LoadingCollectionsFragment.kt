package com.example.test_kinopoisk.ui.loadingcollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.data_model_movie.Items
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentLoadingCollectionsBinding
import com.example.test_kinopoisk.ui.homescreen.HomeFragment.Companion.ARG_DYNAMIC_COUNT
import com.example.test_kinopoisk.ui.homescreen.HomeFragment.Companion.ARG_POPULAR_COUNT
import com.example.test_kinopoisk.ui.homescreen.HomeFragment.Companion.ARG_PREMIERES_COUNT
import com.example.test_kinopoisk.ui.homescreen.HomeFragment.Companion.ARG_SERIALS_COUNT
import com.example.test_kinopoisk.ui.homescreen.HomeFragment.Companion.ARG_TOP250_COUNT
import com.example.test_kinopoisk.ui.loadingcollections.MoviePagingSourceDynamic.Companion.isLoadingDynamic
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoadingCollectionsFragment : Fragment() {

    private var _binding: FragmentLoadingCollectionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by activityViewModels()
    private val sharedViewModel: SharedCollectionsViewModel by activityViewModels()

    private val topPopularAllAdapter = MoviePagedAdapter { movie -> onItemClick(movie) }
    private val top250MoviesAdapter = MoviePagedAdapter { movie -> onItemClick(movie) }
    private val dynamicAdapter = MoviePagedAdapter { movie -> onItemClick(movie) }
    private val premieresAdapter = MoviePagedAdapter { movie -> onItemClick(movie) }
    private val serialsAdapter = MoviePagedAdapter { movie -> onItemClick(movie) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingCollectionsBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerParent.adapter = topPopularAllAdapter
        binding.recyclerParent.adapter = top250MoviesAdapter
        binding.recyclerParent.adapter = dynamicAdapter
        binding.recyclerParent.adapter = premieresAdapter
        binding.recyclerParent.adapter = serialsAdapter
        val title = binding.titleParent
        val recycler = binding.recyclerParent
        val progressBar = binding.progressBar
        val imageView = binding.imageView
        val constraintLayout = binding.constraintLayout

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                swipeLayoutParams(constraintLayout)
                visibilityElementXML(progressBar, imageView, title, recycler)
                viewModel.isLoading.collect { loading ->
                    if (loading) {
                        allAdapters()
//                        isLoadingPremieres.collect { loadingPagedPremieres ->
//                            if (loadingPagedPremieres) {
//                                isLoadingTopPopular.collect { loadingPagedPopular ->
//                                    if (loadingPagedPopular) {
//                                        isLoadingTop250.collect { loadingPagedTop250 ->
//                                            if (loadingPagedTop250) {
                                                isLoadingDynamic.collect { loadingPagedDynamic ->
                                                    if (loadingPagedDynamic) {
//                                                        isLoadingDynamicSerials.collect { loadingPagedSerials ->
//                                                            if (loadingPagedSerials) {
                                                                exportAdapters()
                                                                swipeLayoutParams(
                                                                    constraintLayout
                                                                )
                                                                visibilityElementXML(
                                                                    title,
                                                                    recycler,
                                                                    progressBar,
                                                                    imageView
                                                                )
                                                                findNavController().navigate(
                                                                    R.id.action_navigation_loading_collection_to_navigation_home,
                                                                    exportTotalCountMovie()
                                                                )
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Передача количества фильмов в подборке в HomeFragment
    private fun exportTotalCountMovie() : Bundle{
        val args = Bundle()
        MoviePagingSource.countTotalPremieres.value?.let {
            args.putInt(ARG_PREMIERES_COUNT, it)
        }
        MoviePagingSourceTopPopular.countTotalTopPopular.value?.let {
            args.putInt(ARG_POPULAR_COUNT, it)
        }
        MoviePagingSourceTop250.countTotalTop250.value?.let {
            args.putInt(ARG_TOP250_COUNT, it)
        }
        MoviePagingSourceDynamic.countTotalDynamic.value?.let {
            args.putInt(ARG_DYNAMIC_COUNT, it)
        }
        MoviePagingSourceDynamicSerials.countTotalDynamicSerials.value?.let {
            args.putInt(ARG_SERIALS_COUNT, it)
        }
        return args
    }

    //Передача адаптеров в HomeFragment в адаптер для адаптеров
    private fun exportAdapters() {
        val listAdapters = listOf(
            premieresAdapter,
            topPopularAllAdapter,
            top250MoviesAdapter,
            dynamicAdapter,
            serialsAdapter
        )
        //Сохранение адаптеров и название страны и жанра в общую ViewModel
        sharedViewModel.allAdapters.value = listAdapters
        sharedViewModel.countryAndGenre.value = viewModel.countryAndGenre.value
        sharedViewModel.listCountriesAndGenres.value = viewModel.listCountriesAndGenres.value
    }


    //Клик по фильму, переход к инфо о фильме
    private fun onItemClick(item: Items) {
        val movieId = item.kinopoiskId!!
        val movieInfoFragment = MovieInfoFragment.newInstance(movieId = movieId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_home) {
            findNavController().navigate(
                R.id.action_navigation_home_to_navigation_movie_info,
                movieInfoFragment.arguments
            )
        } else if (currentDestination?.id == R.id.navigation_all_film_in_collection) {
            findNavController().navigate(
                R.id.action_navigation_all_film_to_navigation_movie_info,
                movieInfoFragment.arguments
            )
        }
    }

    //Функция для замены параметров заполнения ширины и высоты layout
    private fun swipeLayoutParams(element: View) {
        val layoutParams = element.layoutParams
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }else{
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        element.layoutParams = layoutParams
    }

    //Функция для скрытия и наоборот некоторых элементов разметки
    private fun visibilityElementXML(
        element1: View,
        element2: View,
        element3: View,
        element4: View,
    ) {
        element1.visibility = View.VISIBLE
        element2.visibility = View.VISIBLE
        element3.visibility = View.GONE
        element4.visibility = View.GONE
    }


    //Наполнение подборок
    private suspend fun allAdapters() {

//        //Премьеры
//        viewModel.premieresPaged.onEach {
//            premieresAdapter.submitData(it)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)

//        //Популярное
//        viewModel.topPopularAllPaged.onEach {
//            topPopularAllAdapter.submitData(it)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)
//
//        //Топ 250 фильмов
//        viewModel.top250MoviesPaged.onEach {
//            top250MoviesAdapter.submitData(it)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)
//
        //Динамическая подборка по стране и жанру
        viewModel.dynamicCollectionsPaged.onEach {
            dynamicAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
//
//        //Подборка сериалов
//        viewModel.serialsPaged.onEach {
//            serialsAdapter.submitData(it)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}