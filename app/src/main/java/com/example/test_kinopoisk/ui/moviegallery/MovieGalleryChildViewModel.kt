package com.example.test_kinopoisk.ui.moviegallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FilmDataInterfaceImpl
import com.example.domain.entity.data_model_images.MovieImages
import com.example.domain.usecase.MovieImagesUseCase
import com.example.test_kinopoisk.ui.moviegallery.MovieGalleryChildFragment.Companion.CONCEPT
import com.example.test_kinopoisk.ui.moviegallery.MovieGalleryChildFragment.Companion.FAN_ART
import com.example.test_kinopoisk.ui.moviegallery.MovieGalleryChildFragment.Companion.STILL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieGalleryChildViewModel : ViewModel() {

    private var _movieImagesStill = MutableStateFlow<List<MovieImages?>>(emptyList())
    val movieImagesStill = _movieImagesStill.asStateFlow()
    private var _movieImagesFanArt = MutableStateFlow<List<MovieImages?>>(emptyList())
    val movieImagesFanArt = _movieImagesFanArt.asStateFlow()
    private var _movieImagesConcept = MutableStateFlow<List<MovieImages?>>(emptyList())
    val movieImagesConcept = _movieImagesConcept.asStateFlow()

    private var _isLoadingImagesStill = MutableStateFlow(false)
    val isLoadingImagesStill = _isLoadingImagesStill.asStateFlow()
    private var _isLoadingImagesFanArt = MutableStateFlow(false)
    val isLoadingImagesFanArt = _isLoadingImagesFanArt.asStateFlow()
    private var _isLoadingImagesConcept = MutableStateFlow(false)
    val isLoadingImagesConcept = _isLoadingImagesConcept.asStateFlow()

    private val repository = FilmDataInterfaceImpl()
    private val movieImagesUseCase = MovieImagesUseCase(repository)

    //Получаем кадры из фильма и т.п. в зависимости, от того, где находится tab
    fun getMovieImages(movieId: Int, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                //Кадры из фильма
                STILL -> {
                    _movieImagesStill.value = emptyList()
                    _isLoadingImagesStill.value = false
                    runCatching {
                        movieImagesUseCase.execute(movieId, type)
                    }.fold(
                        onSuccess = {
                            _movieImagesStill.value = listOf(it)
                            Log.d("MyTag", "MGCVM image still:  ${movieImagesStill.value}")
                            _isLoadingImagesStill.value = true
                        },
                        onFailure = { Log.d("MyTag", "MGCVM-error:  ${it.message}") }
                    )
                }

                //Фан-арты
                FAN_ART -> {
                    _movieImagesFanArt.value = emptyList()
                    _isLoadingImagesFanArt.value = false
                    runCatching {
                        movieImagesUseCase.execute(movieId, type)
                    }.fold(
                        onSuccess = {
                            _movieImagesFanArt.value = listOf(it)
                            Log.d("MyTag", "MGCVM image fan-art:  ${movieImagesFanArt.value}")
                            _isLoadingImagesFanArt.value = true
                        },
                        onFailure = { Log.d("MyTag", "MGCVM-error:  ${it.message}") }
                    )
                }

                //Концепт-арты
                CONCEPT -> {
                    _movieImagesConcept.value = emptyList()
                    _isLoadingImagesConcept.value = false
                    runCatching {
                        movieImagesUseCase.execute(movieId, type)
                    }.fold(
                        onSuccess = {
                            _movieImagesConcept.value = listOf(it)
                            Log.d("MyTag", "MGCVM image concept:  ${movieImagesConcept.value}")
                            _isLoadingImagesConcept.value = true
                        },
                        onFailure = { Log.d("MyTag", "MGCVM-error:  ${it.message}") }
                    )
                }
            }
        }
    }
}