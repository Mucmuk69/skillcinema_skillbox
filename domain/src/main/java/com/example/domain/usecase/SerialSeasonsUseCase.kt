package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_serial_seasons.Seasons

class SerialSeasonsUseCase(private val filmDataInterface: FilmDataInterface) {
    suspend fun execute(id: Int): Seasons {
        return filmDataInterface.getSerialSeasons(id = id)
    }
}