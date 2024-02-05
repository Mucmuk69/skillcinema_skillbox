package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_movie.Movie

class DynamicCompilationUseCase(private val filmDataInterface: FilmDataInterface) {

    suspend fun execute(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        page: Int
    ): Movie {
        return filmDataInterface.getFilmsSearching(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            page
        )
    }
}