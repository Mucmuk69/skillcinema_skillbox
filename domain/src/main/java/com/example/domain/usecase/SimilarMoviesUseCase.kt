package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_similar_movies.SimilarMovies

class SimilarMoviesUseCase(private val filmDataInterface: FilmDataInterface) {
    suspend fun execute(id: Int): SimilarMovies {
        return filmDataInterface.getSimilarMovies(id = id)
    }
}