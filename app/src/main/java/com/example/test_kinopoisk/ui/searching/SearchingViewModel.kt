package com.example.test_kinopoisk.ui.searching

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FilmDataInterfaceImpl
import com.example.domain.entity.data_model_search.FilmKeyword
import com.example.domain.usecase.FilmKeywordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchingViewModel : ViewModel() {
    private val repository = FilmDataInterfaceImpl()

    private var _isLoadingFilmKeyword = MutableStateFlow(false)
    val isLoadingFilmKeyword = _isLoadingFilmKeyword.asStateFlow()

    private var _listFilmKeyword = MutableStateFlow<List<FilmKeyword>>(emptyList())
    val listFilmKeyword = _listFilmKeyword.asStateFlow()

    private val filmKeywordUseCase = FilmKeywordUseCase(repository)

    fun getFilmKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                filmKeywordUseCase.execute(keyword)
            }.fold(
                onSuccess = {
                    _listFilmKeyword.value = listOf(it)
                    Log.d("MyTag", "SearchVM: $it")
                    _isLoadingFilmKeyword.value = true
                },
                onFailure = { Log.d("MyTag", "error:  ${it.message}")}
            )
        }
    }
}