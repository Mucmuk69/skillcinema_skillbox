package com.example.test_kinopoisk.ui.database

import androidx.lifecycle.LiveData
import com.example.test_kinopoisk.ui.database.model.FilmInfo
import com.example.test_kinopoisk.ui.database.model.MovieDBModel

class MovieRepoImpl(private val movieDao: MovieDao) : MovieRepo {

    override fun getAllCollections(): LiveData<List<MovieDBModel>> {
        return movieDao.getAllCollections()
    }

    override fun getAllMovies(): LiveData<List<FilmInfo>> {
        return movieDao.getAllMovies()
    }

    override suspend fun insertCollection(collection: MovieDBModel, onSuccess: () -> Unit) {
        movieDao.insertCollection(collection)
        onSuccess()
    }

    override suspend fun insertMovie(movie: FilmInfo, onSuccess: () -> Unit) {
        movieDao.insertMovie(movie)
        onSuccess()
    }

    override suspend fun deleteCollection(collection: MovieDBModel, onSuccess: () -> Unit) {
        movieDao.deleteCollection(collection)
        onSuccess()
    }

    override suspend fun deleteMovie(movie: FilmInfo, onSuccess: () -> Unit) {
        movieDao.deleteMovie(movie)
        onSuccess()
    }
}