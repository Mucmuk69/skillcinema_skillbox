package com.example.domain.entity

data class MovieDB(
    var movieId: Int?,
    var nameRu: String?,
    var nameEn: String?,
    var posterUrl: String?,
    var webUrl: String?,
    var like: Boolean?,
    var viewed: Boolean?,
    var readyToView: Boolean?
)
