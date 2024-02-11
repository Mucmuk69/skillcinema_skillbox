package com.example.test_kinopoisk.ui.searching

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FilmDataInterfaceImpl
import com.example.domain.entity.data_model_movie.Movie
import com.example.domain.entity.data_model_search.film_keyword.FilmKeyword
import com.example.domain.usecase.FilmKeywordUseCase
import com.example.domain.usecase.FilmSearchingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchingViewModel : ViewModel() {
    private val repository = FilmDataInterfaceImpl()

    private var _isLoadingFilmSearching = MutableStateFlow(false)
    val isLoadingFilmSearching = _isLoadingFilmSearching.asStateFlow()
    private var _isLoadingFilmKeyword = MutableStateFlow(false)
    val isLoadingFilmKeyword = _isLoadingFilmKeyword.asStateFlow()
//    private var _isLoadingStaffKeyword = MutableStateFlow(false)
//    val isLoadingStaffKeyword = _isLoadingStaffKeyword.asStateFlow()

    private var _listFilmSearching = MutableStateFlow<List<Movie>>(emptyList())
    val listFilmSearching = _listFilmSearching.asStateFlow()
    private var _listFilmKeyword = MutableStateFlow<List<FilmKeyword>>(emptyList())
    val listFilmKeyword = _listFilmKeyword.asStateFlow()
//    private var _listStaffKeyword = MutableStateFlow<List<StaffKeyword>>(emptyList())
//    val listStaffKeyword = _listStaffKeyword.asStateFlow()

    private val filmSearchingUseCase = FilmSearchingUseCase(repository)
    private val filmKeywordUseCase = FilmKeywordUseCase(repository)
//    private val staffKeywordUseCase = StaffKeywordUseCase(repository)

    fun getFilmSearching(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        keyword: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                filmSearchingUseCase.execute(
                    countries = countries,
                    genres = genres,
                    order = order,
                    type = type,
                    ratingFrom = ratingFrom,
                    ratingTo = ratingTo,
                    yearFrom = yearFrom,
                    yearTo = yearTo,
                    keyword = keyword
                )
            }.fold(
                onSuccess = {
                    _listFilmSearching.value = listOf(it)
                    Log.d("MyTag", "SearchVM - searching: $it")
                    _isLoadingFilmSearching.value = true
                },
                onFailure = { Log.d("MyTag", "error:  ${it.message}") }
            )
        }
    }

    //Получение фильмов по названию
    fun getFilmKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                filmKeywordUseCase.execute(keyword)
            }.fold(
                onSuccess = {
                    _listFilmKeyword.value = listOf(it)
                    Log.d("MyTag", "SearchVM - film: $it")
                    _isLoadingFilmKeyword.value = true
                },
                onFailure = { Log.d("MyTag", "error:  ${it.message}") }
            )
        }
    }

//    //Получение актеров, режиссеров и т.п. по имени
//    fun getStaffKeyword(name: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            runCatching {
//                staffKeywordUseCase.execute(name = name)
//            }.fold(
//                onSuccess = {
//                    _listStaffKeyword.value = listOf(it)
//                    Log.d("MyTag", "SearchVM - staff: $it")
//                    _isLoadingStaffKeyword.value = true
//                },
//                onFailure = { Log.d("MyTag", "error:  ${it.message}") }
//            )
//        }
//    }
}