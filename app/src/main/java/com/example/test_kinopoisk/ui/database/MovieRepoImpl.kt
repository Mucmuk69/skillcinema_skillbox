package com.example.test_kinopoisk.ui.database

import kotlinx.coroutines.flow.Flow

class MovieRepoImpl(private val movieDao: MovieDao) : MovieRepo {
    override fun getAllMovies(): Flow<List<MovieDatabase>> {
        return movieDao.getAllMovies()
    }

    override suspend fun insert(movie: MovieDatabase, onSuccess: () -> Unit) {
        movieDao.insert(movie)
        onSuccess()
    }

    override suspend fun delete(movie: MovieDatabase, onSuccess: () -> Unit) {
        movieDao.delete(movie)
        onSuccess()
    }
}