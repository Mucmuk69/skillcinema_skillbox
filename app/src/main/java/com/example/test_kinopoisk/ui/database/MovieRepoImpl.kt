package com.example.test_kinopoisk.ui.database

import androidx.lifecycle.LiveData
import com.example.test_kinopoisk.ui.database.model.MovieDBModel

class MovieRepoImpl(private val movieDao: MovieDao) : MovieRepo {

    override fun getAllMovies(): LiveData<List<MovieDBModel>> {
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