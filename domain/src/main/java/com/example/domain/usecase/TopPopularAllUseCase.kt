package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_movie.Movie

class TopPopularAllUseCase(private val filmDataInterface: FilmDataInterface) {

    suspend fun execute(
        type: String,
        page: Int
    ): Movie {
        return filmDataInterface.getFilmCollections(type, page)
    }
}