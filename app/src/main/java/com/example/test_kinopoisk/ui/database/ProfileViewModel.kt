package com.example.test_kinopoisk.ui.database

import androidx.lifecycle.ViewModel

class ProfileViewModel(private val movieDao: MovieDao) : ViewModel() {

    val allLike = this.movieDao.getAllLike()

    val allReadyToView = this.movieDao.getAllReadyToView()

    val allViewed = this.movieDao.getAllViewed()

    fun deleteLike(){

    }

    fun deleteViewed(){

    }

}