package com.example.test_kinopoisk.ui.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieDatabase(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    var movieId: Int,
    @ColumnInfo(name = "name_ru")
    val nameRu: String?,
    @ColumnInfo(name = "name_en")
    val nameEn: String?,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String?,
    @ColumnInfo(name = "web_url")
    val webUrl: String,
    @ColumnInfo(name = "like")
    val like: Boolean?,
    @ColumnInfo(name = "viewed")
    val viewed: Boolean?,
    @ColumnInfo(name = "ready_to_view")
    val readyToView: Boolean?
)
