package com.example.test_kinopoisk.ui.searching

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FilmDataInterfaceImpl
import com.example.domain.entity.data_model_search.film_keyword.FilmKeyword
import com.example.domain.entity.data_model_search.staff_keyword.StaffKeyword
import com.example.domain.usecase.FilmKeywordUseCase
import com.example.domain.usecase.StaffKeywordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchingViewModel : ViewModel() {
    private val repository = FilmDataInterfaceImpl()

    private var _isLoadingFilmKeyword = MutableStateFlow(false)
    val isLoadingFilmKeyword = _isLoadingFilmKeyword.asStateFlow()
    private var _isLoadingStaffKeyword = MutableStateFlow(false)
    val isLoadingStaffKeyword = _isLoadingStaffKeyword.asStateFlow()

    private var _listFilmKeyword = MutableStateFlow<List<FilmKeyword>>(emptyList())
    val listFilmKeyword = _listFilmKeyword.asStateFlow()
    private var _listStaffKeyword = MutableStateFlow<List<StaffKeyword>>(emptyList())
    val listStaffKeyword = _listStaffKeyword.asStateFlow()

    private val filmKeywordUseCase = FilmKeywordUseCase(repository)
    private val staffKeywordUseCase = StaffKeywordUseCase(repository)

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

    //Получение актеров, режиссеров и т.п. по имени
    fun getStaffKeyword(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                staffKeywordUseCase.execute(name = name)
            }.fold(
                onSuccess = {
                    _listStaffKeyword.value = listOf(it)
                    Log.d("MyTag", "SearchVM - staff: $it")
                    _isLoadingStaffKeyword.value = true
                },
                onFailure = { Log.d("MyTag", "error:  ${it.message}") }
            )
        }
    }
}