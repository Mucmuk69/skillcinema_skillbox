package com.example.test_kinopoisk.ui.database

import com.example.test_kinopoisk.ui.database.model.MovieDBModel
import kotlinx.coroutines.flow.Flow

class MovieRepoImpl(private val movieDao: MovieDao) : MovieRepo {

    override fun getAllMovies(): Flow<List<MovieDBModel>> {
        return movieDao.getAllMovies()
    }

    override suspend fun insertMovie(movie: MovieDBModel, onSuccess: () -> Unit) {
        movieDao.insertMovie(movie)
        onSuccess()
    }

    override suspend fun deleteMovie(movie: MovieDBModel, onSuccess: () -> Unit) {
        movieDao.deleteMovie(movie)
        onSuccess()
    }
}