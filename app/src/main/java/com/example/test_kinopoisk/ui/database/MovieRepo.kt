package com.example.test_kinopoisk.ui.database

import androidx.lifecycle.LiveData
import com.example.test_kinopoisk.ui.database.model.FilmInfo
import com.example.test_kinopoisk.ui.database.model.MovieDBModel

interface MovieRepo {
    fun getAllCollections(): LiveData<List<MovieDBModel>>
    fun getAllMovies(): LiveData<List<FilmInfo>>
    suspend fun insertCollection(collection: MovieDBModel, onSuccess: () -> Unit)
    suspend fun insertMovie(movie: FilmInfo, onSuccess: () -> Unit)
    suspend fun deleteCollection(collection: MovieDBModel, onSuccess: () -> Unit)
    suspend fun deleteMovie(movie: FilmInfo, onSuccess: () -> Unit)
}