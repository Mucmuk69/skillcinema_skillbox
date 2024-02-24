package com.example.test_kinopoisk.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test_kinopoisk.ui.database.model.MovieDBModel

@Database(entities = [MovieDBModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var database: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return if (database == null) {
                database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                    .fallbackToDestructiveMigration()
                    .build()
                database as AppDatabase
            } else {
                database as AppDatabase
            }
        }
    }
}