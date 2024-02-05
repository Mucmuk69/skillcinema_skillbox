package com.example.domain.entity.data_model_movie

data class Movie(
    var total: Int? = null,
    var totalPages: Int? = null,
    var items: List<Items> = listOf()
)