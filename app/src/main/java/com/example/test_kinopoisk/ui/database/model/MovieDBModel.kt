package com.example.test_kinopoisk.ui.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "movie")
data class MovieDBModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "title")
    var titleCollection: String?,
    @Embedded
    var filmInfo: FilmInfo,
)

data class FilmInfo(
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


//object MovieDBMapper {
//    fun mapToMovieDB(movieDatabase: MovieDatabase): MovieDB {
//        return MovieDB(
//            movieDatabase.movieId,
//            movieDatabase.nameRu,
//            movieDatabase.nameEn,
//            movieDatabase.posterUrl
//        )
//    }
//}

