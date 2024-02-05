package com.example.domain.entity.data_model_movie

data class Items(
    var kinopoiskId: Int? = null,
    var imdbId: String? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var nameOriginal: String? = null,
    var countries: List<Countries> = listOf(),
    var genres: List<Genres> = listOf(),
    var ratingKinopoisk: Double? = null,
    var ratingImdb: Double? = null,
    var year: Int? = null,
    var type: String? = null,
    var posterUrl: String? = null,
    var posterUrlPreview: String? = null
)