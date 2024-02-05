package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_movie.Movie

class PremieresUseCase(private val filmDataInterface: FilmDataInterface) {

    suspend fun execute(year: Int, month: String): Movie {
        return filmDataInterface.getPremieres(year, month)
    }
}