package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_images.MovieImages

class MovieImagesUseCase(private val filmDataInterface: FilmDataInterface) {
    suspend fun execute(id: Int, type: String): MovieImages {
        return filmDataInterface.getMovieImages(id = id, type = type)
    }
}