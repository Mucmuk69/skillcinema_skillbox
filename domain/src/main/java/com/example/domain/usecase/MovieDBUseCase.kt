package com.example.domain.usecase

import com.example.domain.DatabaseMovie
import com.example.domain.entity.MovieDB

class MovieDBUseCase(private val databaseMovie: DatabaseMovie) {
    suspend fun execute(): MovieDB{
        return databaseMovie.getMovie()
    }
}