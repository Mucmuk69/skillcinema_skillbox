package com.example.test_kinopoisk.ui.database

import com.example.test_kinopoisk.ui.database.model.MovieDBModel
import kotlinx.coroutines.flow.Flow

interface MovieRepo {
    fun getAllMovies(): Flow<List<MovieDBModel>>
    suspend fun insertMovie(movie: MovieDBModel, onSuccess: () -> Unit)
    suspend fun deleteMovie(movie: MovieDBModel, onSuccess: () -> Unit)
}