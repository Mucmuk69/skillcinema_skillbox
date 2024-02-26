package com.example.test_kinopoisk.ui.database

import androidx.lifecycle.LiveData
import com.example.test_kinopoisk.ui.database.model.MovieDBModel

interface MovieRepo {
    fun getAllMovies(): LiveData<List<MovieDBModel>>
    suspend fun insertMovie(movie: MovieDBModel, onSuccess: () -> Unit)
    suspend fun deleteMovie(movie: MovieDBModel, onSuccess: () -> Unit)
}