package com.example.test_kinopoisk.ui.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.domain.entity.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT `like` FROM movie")
    fun getAllLike(): Flow<List<MovieDatabase>>

    @Query("SELECT `viewed` FROM movie")
    fun getAllViewed(): Flow<List<MovieDatabase>>

    @Query("SELECT `ready_to_view` FROM movie")
    fun getAllReadyToView(): Flow<List<MovieDatabase>>

    @Insert
    suspend fun insert(movie: MovieDB)

    @Delete
    suspend fun delete(movie: MovieDB)
}