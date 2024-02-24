package com.example.test_kinopoisk.ui.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.test_kinopoisk.ui.database.model.FilmInfo
import com.example.test_kinopoisk.ui.database.model.MovieDBModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllCollections(): LiveData<List<MovieDBModel>>

    @Query("SELECT * FROM movie")
    fun getAllMovies(): LiveData<List<FilmInfo>>

    @Insert
    suspend fun insertCollection(collection: MovieDBModel)

    @Insert
    suspend fun insertMovie(movie: FilmInfo)

    @Delete
    suspend fun deleteCollection(collection: MovieDBModel)

    @Delete
    suspend fun deleteMovie(movie: FilmInfo)
}