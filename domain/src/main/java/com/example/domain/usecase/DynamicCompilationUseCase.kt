package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_movie.Movie

class DynamicCompilationUseCase(private val filmDataInterface: FilmDataInterface) {

    suspend fun execute(
        countries: List<Int>,
        genres: List<Int>,
        type: String,
        page: Int
    ): Movie {
        return filmDataInterface.getDynamicFilms(
            countries,
            genres,
            type,
            page
        )
    }
}