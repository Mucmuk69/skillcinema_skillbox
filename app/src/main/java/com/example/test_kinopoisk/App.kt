package com.example.test_kinopoisk

import android.app.Application
import androidx.room.Room
import com.example.test_kinopoisk.ui.database.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}