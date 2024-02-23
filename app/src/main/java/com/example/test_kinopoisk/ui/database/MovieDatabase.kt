package com.example.test_kinopoisk.ui.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.MovieDB

@Entity(tableName = "movie")
data class MovieDatabase(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    var movieId: Int?,
    @ColumnInfo(name = "name_ru")
    var nameRu: String?,
    @ColumnInfo(name = "name_en")
    var nameEn: String?,
    @ColumnInfo(name = "poster_url")
    var posterUrl: String?
)


object MovieDBMapper {
    fun mapToMovieDB(movieDatabase: MovieDatabase): MovieDB {
        return MovieDB(
            movieDatabase.movieId,
            movieDatabase.nameRu,
            movieDatabase.nameEn,
            movieDatabase.posterUrl
        )
    }
}

