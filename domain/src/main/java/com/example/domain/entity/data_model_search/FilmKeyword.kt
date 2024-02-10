package com.example.domain.entity.data_model_search

data class FilmKeyword(
    val films: List<Film>,
    val keyword: String?,
    val pagesCount: Int?,
    val searchFilmsCountResult: Int?
)