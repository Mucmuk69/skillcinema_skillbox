package com.example.test_kinopoisk.ui.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.test_kinopoisk.ui.database.model.MovieDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieDBModel>>

    @Insert
    suspend fun insertMovie(movie: MovieDBModel)

    @Delete
    suspend fun deleteMovie(movie: MovieDBModel)
}