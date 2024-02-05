package com.example.domain.entity.data_model_country_and_genre

data class CountriesAndGenres(
     var genres: List<Genres> = listOf(),
     var countries: List<Countries> = listOf()
)