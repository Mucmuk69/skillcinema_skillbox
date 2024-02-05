package com.example.data.entity

import com.example.domain.entity.data_model_movie_info.Countries
import com.example.domain.entity.data_model_movie_info.FilmInfo
import com.example.domain.entity.data_model_movie_info.Genres

data class FilmInfoData(
    var kinopoiskId: Int? = null,
    var kinopoiskHDId: String? = null,
    var imdbId: String? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var nameOriginal: String? = null,
    var posterUrl: String? = null,
    var posterUrlPreview: String? = null,
    var coverUrl: String? = null,
    var logoUrl: String? = null,
    var reviewsCount: Int? = null,
    var ratingGoodReview: Double? = null,
    var ratingGoodReviewVoteCount: Int? = null,
    var ratingKinopoisk: Double? = null,
    var ratingKinopoiskVoteCount: Int? = null,
    var ratingImdb: Double? = null,
    var ratingImdbVoteCount: Int? = null,
    var ratingFilmCritics: Double? = null,
    var ratingFilmCriticsVoteCount: Int? = null,
    var ratingAwait: Double? = null,
    var ratingAwaitCount: Int? = null,
    var ratingRfCritics: Double? = null,
    var ratingRfCriticsVoteCount: Int? = null,
    var webUrl: String? = null,
    var year: Int? = null,
    var filmLength: Int? = null,
    var slogan: String? = null,
    var description: String? = null,
    var shortDescription: String? = null,
    var editorAnnotation: String? = null,
    var isTicketsAvailable: Boolean? = null,
    var productionStatus: String? = null,
    var type: String? = null,
    var ratingMpaa: String? = null,
    var ratingAgeLimits: String? = null,
    var hasImax: Boolean? = null,
    var has3D: Boolean? = null,
    var lastSync: String? = null,
    var countries: List<CountriesFilmInfoData> = listOf(),
    var genres: List<GenresFilmInfoData> = listOf(),
    var startYear: Int? = null,
    var endYear: Int? = null,
    var serial: Boolean? = null,
    var shortFilm: Boolean? = null,
    var completed: Boolean? = null
)

data class CountriesFilmInfoData(
    var country: String? = null
)

data class GenresFilmInfoData(
    var genre: String? = null
)

// Маппер для преобразования FilmInfoData в FilmInfo
object FilmInfoMapper {
    fun mapToFilmInfo(filmInfoData: FilmInfoData): FilmInfo {
        return FilmInfo(
            filmInfoData.kinopoiskId,
            filmInfoData.kinopoiskHDId,
            filmInfoData.imdbId,
            filmInfoData.nameRu,
            filmInfoData.nameEn,
            filmInfoData.nameOriginal,
            filmInfoData.posterUrl,
            filmInfoData.posterUrlPreview,
            filmInfoData.coverUrl,
            filmInfoData.logoUrl,
            filmInfoData.reviewsCount,
            filmInfoData.ratingGoodReview,
            filmInfoData.ratingGoodReviewVoteCount,
            filmInfoData.ratingKinopoisk,
            filmInfoData.ratingKinopoiskVoteCount,
            filmInfoData.ratingImdb,
            filmInfoData.ratingImdbVoteCount,
            filmInfoData.ratingFilmCritics,
            filmInfoData.ratingFilmCriticsVoteCount,
            filmInfoData.ratingAwait,
            filmInfoData.ratingAwaitCount,
            filmInfoData.ratingRfCritics,
            filmInfoData.ratingRfCriticsVoteCount,
            filmInfoData.webUrl,
            filmInfoData.year,
            filmInfoData.filmLength,
            filmInfoData.slogan,
            filmInfoData.description,
            filmInfoData.shortDescription,
            filmInfoData.editorAnnotation,
            filmInfoData.isTicketsAvailable,
            filmInfoData.productionStatus,
            filmInfoData.type,
            filmInfoData.ratingMpaa,
            filmInfoData.ratingAgeLimits,
            filmInfoData.hasImax,
            filmInfoData.has3D,
            filmInfoData.lastSync,
            filmInfoData.countries.map { countriesFilmInfoData -> Countries(countriesFilmInfoData.country) },
            filmInfoData.genres.map { genresFilmInfoData -> Genres(genresFilmInfoData.genre) },
            filmInfoData.startYear,
            filmInfoData.endYear,
            filmInfoData.serial,
            filmInfoData.shortFilm,
            filmInfoData.completed
        )
    }
}
