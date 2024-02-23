package com.example.test_kinopoisk.ui.database

import kotlinx.coroutines.flow.Flow

interface MovieRepo {
    fun getAllMovies(): Flow<List<MovieDatabase>>
    suspend fun insert(movie: MovieDatabase, onSuccess: () -> Unit)
    suspend fun delete(movie: MovieDatabase, onSuccess: () -> Unit)
}