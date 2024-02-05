package com.example.test_kinopoisk.ui.staffinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FilmDataInterfaceImpl
import com.example.domain.entity.data_model_movie_info.FilmInfo
import com.example.domain.entity.data_model_staff.Film
import com.example.domain.entity.data_model_staff.StaffInfo
import com.example.domain.usecase.FilmInfoUseCase
import com.example.domain.usecase.StaffInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StaffInfoViewModel private constructor(
    repository: FilmDataInterfaceImpl
) : ViewModel() {
    constructor() : this(FilmDataInterfaceImpl())

    private var _isLoadingStaffInfo = MutableStateFlow(false)
    val isLoadingStaffInfo = _isLoadingStaffInfo.asStateFlow()

    private var _isLoadingListBestFilms = MutableStateFlow(false)
    val isLoadingListBestFilms = _isLoadingListBestFilms.asStateFlow()

    private var _isLoadingBestFilms = MutableStateFlow(false)
    val isLoadingBestFilms = _isLoadingListBestFilms.asStateFlow()

    private var _listStaffInfo = MutableStateFlow<List<StaffInfo?>>(emptyList())
    val listStaffInfo = _listStaffInfo.asStateFlow()

    private var _listBestFilms = MutableStateFlow<List<FilmInfo>>(emptyList())
    val listBestFilms = _listBestFilms.asStateFlow()

    private var _listIdBestFilms = MutableStateFlow<List<Film>>(emptyList())
    val listIdBestFilms = _listIdBestFilms.asStateFlow()

    private val staffInfoUseCase = StaffInfoUseCase(repository)
    private val filmInfoUseCase = FilmInfoUseCase(repository)

    //Получение информации об актере
    fun getStaffInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (listStaffInfo.value.isEmpty() || listStaffInfo.value[0]?.personId != id) {
                kotlin.runCatching {
                    _listIdBestFilms.value = emptyList()
                    _listBestFilms.value = emptyList()
                    _isLoadingListBestFilms.value = false
                    _isLoadingBestFilms.value = false
                    _isLoadingStaffInfo.value = false
                    staffInfoUseCase.execute(id = id)
                }.fold(
                    onSuccess = {
                        _listStaffInfo.value = listOf(it)
                        Log.d("MyTag", "SIVM: _listStaffInfo -  $it")
                        _isLoadingStaffInfo.value = true
                    },
                    onFailure = { Log.d("MyTag", "SIVM-errorStaffInfo:  ${it.message}") }
                )
            }
        }
    }


    //Получение информации о лучших фильмах актера
    fun getBestFilms(idFilm: Int) {
        viewModelScope.launch {
            if (listBestFilms.value.isEmpty() || listBestFilms.value.all { it.kinopoiskId != idFilm }) {
                kotlin.runCatching {
                    filmInfoUseCase.execute(id = idFilm)
                }.fold(
                    onSuccess = {
                        _listBestFilms.value += it
                        Log.d("MyTag", "SIVM: _listBestFilms -  ${listStaffInfo.value}")
                        _isLoadingBestFilms.value = true
                    },
                    onFailure = { Log.d("MyTag", "SIVM-errorBestFilms:  ${it.message}") }
                )
            }
        }
    }

    //Получение списка лучших фильмов актера
    fun getListBestFilms() {
        viewModelScope.launch {
            if (listIdBestFilms.value.isEmpty()) {
                isLoadingStaffInfo.collect { loadingStaffInfo ->
                    if (loadingStaffInfo) {
                        listStaffInfo.collect { listFilms ->
                            // Создание нового списка для фильмов с лучшим рейтингом и ограничение до 10 фильмов
                            _listIdBestFilms.value = listFilms[0]?.films
                                ?.filter { film -> film.rating != null && film.rating!! >= 4.toString() }
                                ?.distinctBy { filmName -> filmName.nameRu ?: filmName.nameEn }
                                ?.take(1) ?: emptyList()
                            Log.d("MyTag", "SIVM: Id best films -  ${listIdBestFilms.value}")
                            _isLoadingListBestFilms.value = true
                        }
                    }
                }
            }
        }
    }

}