package com.example.test_kinopoisk.ui.database

import com.example.domain.DatabaseMovie
import com.example.domain.entity.MovieDB
import com.example.test_kinopoisk.ui.database.model.MovieDatabase

class DatabaseMovieImpl(private val movieDatabase: MovieDatabase): DatabaseMovie {
    private val movieDBMapper = MovieDBMapper
    override suspend fun getMovie(): MovieDB =
        movieDBMapper.mapToMovieDB(movieDatabase)
}
