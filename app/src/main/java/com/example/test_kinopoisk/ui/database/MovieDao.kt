package com.example.test_kinopoisk.ui.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.domain.entity.MovieDB
import com.example.test_kinopoisk.State
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieDatabase>>

    @Insert
    suspend fun insert(movie: MovieDatabase)

    @Delete
    suspend fun delete(movie: MovieDatabase)
}