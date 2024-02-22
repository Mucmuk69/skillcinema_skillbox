package com.example.domain

import com.example.domain.entity.MovieDB

interface DatabaseMovie {
    suspend fun getMovie(): MovieDB
}