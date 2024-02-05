package com.example.test_kinopoisk

import com.example.domain.entity.data_model_movie_info.FilmInfo

sealed class State {
    object Loading : State()
    data class Success(val data: List<FilmInfo>) : State()
    data class Error(val error: String) : State()
}