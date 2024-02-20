package com.example.test_kinopoisk.ui.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieDatabase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}