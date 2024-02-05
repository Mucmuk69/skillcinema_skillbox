package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_country_and_genre.CountriesAndGenres

class ListCountriesAndGenresUseCase(private val filmDataInterface: FilmDataInterface) {

    suspend fun execute(): CountriesAndGenres {
        return filmDataInterface.getCountriesAndGenres()
    }
}