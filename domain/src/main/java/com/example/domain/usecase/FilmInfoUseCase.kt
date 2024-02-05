package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_movie_info.FilmInfo

class FilmInfoUseCase(private val filmDataInterface: FilmDataInterface) {

    suspend fun execute(id: Int): FilmInfo {
        return filmDataInterface.getFilmInfo(id = id)
    }
}