package com.example.test_kinopoisk.ui.loadingcollections

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.FilmDataInterfaceImpl
import com.example.domain.entity.data_model_country_and_genre.CountriesAndGenres
import com.example.domain.entity.data_model_movie.Items
import com.example.domain.usecase.DynamicCompilationUseCase
import com.example.domain.usecase.ListCountriesAndGenresUseCase
import com.example.domain.usecase.PremieresUseCase
import com.example.domain.usecase.TopPopularAllUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class DashboardViewModel private constructor() : ViewModel() {
    private val repository = FilmDataInterfaceImpl()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _listCountriesAndGenres = MutableStateFlow<List<CountriesAndGenres>>(emptyList())
    val listCountriesAndGenres = _listCountriesAndGenres.asStateFlow()
    private var _randomCountry = MutableStateFlow<List<Int>>(emptyList())
    private val randomCountry = _randomCountry.asStateFlow()
    private var _randomGenre = MutableStateFlow<List<Int>>(emptyList())
    private val randomGenre = _randomGenre.asStateFlow()
    private var _countryAndGenre = MutableStateFlow<String?>(null)
    val countryAndGenre = _countryAndGenre.asStateFlow()

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private val monthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
    private val currentMonth = monthFormat.format(Calendar.getInstance().time)

    private val countriesAndGenresUseCase = ListCountriesAndGenresUseCase(repository)
    private val premieresUseCase = PremieresUseCase(repository)
    private val topPopularAllUseCase = TopPopularAllUseCase(repository)
    private val dynamicCompilationUseCase = DynamicCompilationUseCase(repository)

    init {
        listCountriesAndGenres()
    }


    //получение списка стран и жанров для динамических подборок
    private fun listCountriesAndGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                countriesAndGenresUseCase.execute()
            }.fold(
                onSuccess = { countriesAndGenres ->
                    _listCountriesAndGenres.emit(listOf(countriesAndGenres))
                    val randomIndexCountry = Random.nextInt(5)
                    val randomIndexGenre = Random.nextInt(5)
                    //получение рандомных id из 5 для определения страны и жанра
                    listCountriesAndGenres.value[0].countries[randomIndexCountry].id?.let {
                        _randomCountry.emit(listOf(it))
                    }
                    listCountriesAndGenres.value[0].genres[randomIndexGenre].id?.let {
                        _randomGenre.emit(listOf(it))
                    }
                    Log.d("MyTag", "HVM: countryId: ${randomCountry.value}")
                    Log.d("MyTag", "HVM: genreId: ${randomGenre.value}")

                    val indexCountry = randomCountry.value[0]
                    val indexGenre = randomGenre.value[0]
                    //получение рандомной страны и жанра по текущим id
                    val country = countriesAndGenres.countries[indexCountry - 1].country
                    val genre = countriesAndGenres.genres[indexGenre - 1].genre
                    _countryAndGenre.value = "$country - $genre"
                    _isLoading.value = true
                },
                onFailure = { Log.d("MyTag", "HVM: error: ${it.message}") }
            )
        }
    }

    //популярное
    val topPopularAllPaged: Flow<PagingData<Items>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 1),
        pagingSourceFactory = {
            MoviePagingSourceTopPopular(
                type = TOP_POPULAR_ALL,
                useCase = topPopularAllUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    //топ 250
    val top250MoviesPaged: Flow<PagingData<Items>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 1),
        pagingSourceFactory = {
            MoviePagingSourceTop250(
                type = TOP_250_MOVIES,
                useCase = topPopularAllUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    //премьеры
    val premieresPaged: Flow<PagingData<Items>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 1),
        pagingSourceFactory = {
            MoviePagingSource(
                year = currentYear,
                month = currentMonth,
                useCase = premieresUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    //динамическая подборка по стране и жанру
    val dynamicCollectionsPaged: Flow<PagingData<Items>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 1),
        pagingSourceFactory = {
            MoviePagingSourceDynamic(
                countries = _randomCountry.value,
                genres = _randomGenre.value,
                type = FILM,
                useCase = dynamicCompilationUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    //подборка сериалов
    val serialsPaged: Flow<PagingData<Items>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 1),
        pagingSourceFactory = {
            MoviePagingSourceDynamicSerials(
                countries = _randomCountry.value,
                genres = _randomGenre.value,
                type = TV_SERIES,
                useCase = dynamicCompilationUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    private companion object {
        private const val TOP_POPULAR_ALL = "TOP_POPULAR_ALL"
        private const val TOP_250_MOVIES = "TOP_250_MOVIES"
        private const val TV_SERIES = "TV_SERIES"
        private const val FILM = "FILM"
    }
}

class SharedCollectionsViewModel : ViewModel() {
    var allAdapters = MutableStateFlow<List<MoviePagedAdapter>>(emptyList())
    var countryAndGenre = MutableStateFlow<String?>(null)
    var listCountriesAndGenres = MutableStateFlow<List<CountriesAndGenres>>(emptyList())
}