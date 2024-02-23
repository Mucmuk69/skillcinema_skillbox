package com.example.test_kinopoisk.ui.movieinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FilmDataInterfaceImpl
import com.example.domain.DatabaseMovie
import com.example.domain.entity.data_model_images.MovieImages
import com.example.domain.entity.data_model_movie_info.FilmInfo
import com.example.domain.entity.data_model_serial_seasons.Seasons
import com.example.domain.entity.data_model_similar_movies.SimilarMovies
import com.example.domain.entity.data_model_staff.ListStaff
import com.example.domain.usecase.FilmInfoUseCase
import com.example.domain.usecase.ListStaffUseCase
import com.example.domain.usecase.MovieImagesUseCase
import com.example.domain.usecase.SerialSeasonsUseCase
import com.example.domain.usecase.SimilarMoviesUseCase
import com.example.test_kinopoisk.ui.database.DatabaseMovieImpl
import com.example.test_kinopoisk.ui.database.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieInfoViewModel private constructor() : ViewModel() {
    private val repository = FilmDataInterfaceImpl()

    private var _listFilmInfo = MutableStateFlow<List<FilmInfo>>(emptyList())
    val listFilmInfo = _listFilmInfo.asStateFlow()
    private var _listStaff = MutableStateFlow<List<ListStaff>>(emptyList())
    val listStaff = _listStaff.asStateFlow()
    private var _movieImages = MutableStateFlow<List<MovieImages?>>(emptyList())
    val movieImages = _movieImages.asStateFlow()
    private var _listActors = MutableStateFlow<List<ListStaff>>(emptyList())
    val listActors = _listActors.asStateFlow()
    private var _serialSeasons = MutableStateFlow<List<Seasons?>>(emptyList())
    val serialSeasons = _serialSeasons.asStateFlow()
    private var _listSimilarMovies = MutableStateFlow<List<SimilarMovies>>(emptyList())
    val listSimilarMovies = _listSimilarMovies.asStateFlow()

//    private var repositoryDB = DatabaseMovieImpl(allMovies.value[0])
//    private var movieDBUseCase = MovieDBUseCase(repositoryDB)

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private var _isLoadingSeasons = MutableStateFlow(false)
    val isLoadingSeasons = _isLoadingSeasons.asStateFlow()
    private var _isLoadingStaff = MutableStateFlow(false)
    val isLoadingStaff = _isLoadingStaff.asStateFlow()
    private var _isLoadingImages = MutableStateFlow(false)
    val isLoadingImages = _isLoadingImages.asStateFlow()
    private var _isLoadingSimilarMovies = MutableStateFlow(false)
    val isLoadingSimilarMovies = _isLoadingSimilarMovies.asStateFlow()

    private val filmInfoUseCase = FilmInfoUseCase(repository)
    private val listStaffUseCase = ListStaffUseCase(repository)
    private val serialSeasonsUseCase = SerialSeasonsUseCase(repository)
    private val movieImagesUseCase = MovieImagesUseCase(repository)
    private val similarMoviesUseCase = SimilarMoviesUseCase(repository)

//    init {
//        viewModelScope.launch {
//            isLoading.collect { loading ->
//                if (loading) {
//                    listFilmInfo.collect { filmInfo ->
//                        allMovies.value[0].movieId = filmInfo[0].kinopoiskId
//                        allMovies.value[0].nameRu = filmInfo[0].nameRu
//                        allMovies.value[0].nameEn = filmInfo[0].nameEn
//                        allMovies.value[0].posterUrl = filmInfo[0].posterUrl
//                    }
//                }
//            }
//        }
//    }
//    private val allMovies = this.movieDao.getAllMovies()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = emptyList()
//        )

    //Получение инфо о фильме по id
    fun getFilmInfo(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (listFilmInfo.value.isEmpty() || listFilmInfo.value[0].kinopoiskId != movieId) {
                runCatching {
                    _listStaff.value = emptyList()
                    _serialSeasons.value = emptyList()
                    _movieImages.value = emptyList()
                    _listSimilarMovies.value = emptyList()
                    _isLoadingImages.value = false
                    _isLoadingSeasons.value = false
                    _isLoadingStaff.value = false
                    _isLoading.value = false
                    _isLoadingSimilarMovies.value = false
                    filmInfoUseCase.execute(movieId)
                }.fold(
                    onSuccess = {
                        _listFilmInfo.value = listOf(it)
                        Log.d("MyTag", "MIVM: filmInfo -  $it")
                        _isLoading.value = true
                    },
                    onFailure = {
                        Log.d("MyTag", "MIVM-error:  ${it.message}")
                    }
                )
            }
        }
    }

    //Получение кадров из фильма и т.п.
    fun getMovieImages(id: Int, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movieImages.value.isEmpty() || listFilmInfo.value[0].kinopoiskId != id) {
                runCatching {
                    movieImagesUseCase.execute(id = id, type = type)
                }.fold(
                    onSuccess = {
                        _movieImages.value = listOf(it)
                        Log.d("MyTag", "MIVM: movie images -  $it")
                        _isLoadingImages.value = true
                    },
                    onFailure = { Log.d("MyTag", "MIVM-error:  ${it.message}") }
                )
            }
        }
    }

    //Получение инфо о сезонах сериала
    fun getSerialSeasonsInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (serialSeasons.value.isEmpty() || listFilmInfo.value[0].kinopoiskId != id) {
                runCatching {
                    serialSeasonsUseCase.execute(id = id)
                }.fold(
                    onSuccess = {
                        _serialSeasons.value = listOf(it)
                        Log.d("MyTag", "MIVM-seasons:  $it")
                        _isLoadingSeasons.value = true
                    },
                    onFailure = { Log.d("MyTag", "MIVM-error:  ${it.message}") }
                )
            }
        }
    }

    //Получение списка актеров фильма по id фильма
    fun getListStaff(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (listStaff.value.isEmpty()) {
                runCatching {
                    listStaffUseCase.execute(filmId = filmId)
                }.fold(
                    onSuccess = {
                        _listActors.value = it
                            .filter { listStaff -> listStaff.professionKey == "ACTOR" }
                            .distinctBy { listStaff -> listStaff.nameRu ?: listStaff.nameEn }
                        _listStaff.value = it
                            .filter { listStaff -> listStaff.professionKey != "ACTOR" }
                            .distinctBy { listStaff -> listStaff.nameRu ?: listStaff.nameEn }
                        Log.d("MyTag", "MIVM: listStaff -  $it")
                        _isLoadingStaff.value = true
                    },
                    onFailure = { Log.d("MyTag", "error:  ${it.message}") }
                )
            }
        }
    }

    //Получение списка похожих фильмов
    fun getSimilarMovies(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_listSimilarMovies.value.isEmpty() || listFilmInfo.value[0].kinopoiskId != filmId) {
                runCatching {
                    similarMoviesUseCase.execute(id = filmId)
                }.fold(
                    onSuccess = {
                        _listSimilarMovies.value = listOf(it)
                        Log.d("MyTag", "MIVM: similar movies -  $it")
                        _isLoadingSimilarMovies.value = true
                    },
                    onFailure = { Log.d("MyTag", "error:  ${it.message}") }
                )
            }
        }
    }

    fun addMovie() {
//        viewModelScope.launch {
//            if (allMovies.value[0].movieId == listFilmInfo.value[0].kinopoiskId) {
//                movieDao.delete(allMovies.value[0])
//            } else {
//                movieDao.insert(allMovies.value[0])
//            }
//        }
    }
}

//Актеры и другие для передачи в другой фрагмент
class SharedStaffViewModel : ViewModel() {
    var actors = MutableStateFlow<List<ListStaff>>(emptyList())
    var staff = MutableStateFlow<List<ListStaff>>(emptyList())
}

//Название сериала и сезоны для передачи в другой фрагмент
class SharedSeasonsViewModel : ViewModel() {
    var nameSerial = MutableStateFlow<String?>(null)
    var seasons = MutableStateFlow<List<Seasons?>>(emptyList())
}

//Кадры и т.п. из фильма\сериала
class SharedMovieImagesViewModel : ViewModel() {
    var movieImages = MutableStateFlow<List<MovieImages?>>(emptyList())
}

class SharedSimilarMoviesViewModel : ViewModel() {
    var similarAdapter = MutableStateFlow<List<SimilarMovies>>(emptyList())
}