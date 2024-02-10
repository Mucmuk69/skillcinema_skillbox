package com.example.data.entity

import com.example.domain.entity.data_model_search.Country
import com.example.domain.entity.data_model_search.Film
import com.example.domain.entity.data_model_search.FilmKeyword
import com.example.domain.entity.data_model_search.Genre

data class FilmKeywordData(
    val films: List<FilmKeywordInfoData>,
    val keyword: String?,
    val pagesCount: Int?,
    val searchFilmsCountResult: Int?
)

data class FilmKeywordInfoData(
    val countries: List<CountryKeywordData>,
    val description: String?,
    val filmId: Int?,
    val filmLength: String?,
    val genres: List<GenreKeywordData>,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val rating: String?,
    val ratingVoteCount: Int?,
    val type: String?,
    val year: String?
)

data class CountryKeywordData(
    val country: String?
)

data class GenreKeywordData(
    val genre: String?
)

object FilmKeywordMapper {
    fun mapToFilmKeyword(filmKeywordData: FilmKeywordData): FilmKeyword {
        val films = filmKeywordData.films.map { film ->
            Film(
                film.countries.map { country -> Country(country.country) },
                film.description,
                film.filmId,
                film.filmLength,
                film.genres.map { genre -> Genre(genre.genre) },
                film.nameEn,
                film.nameRu,
                film.posterUrl,
                film.posterUrlPreview,
                film.rating,
                film.ratingVoteCount,
                film.type,
                film.year
            )
        }
        return FilmKeyword(
            films,
            filmKeywordData.keyword,
            filmKeywordData.pagesCount,
            filmKeywordData.searchFilmsCountResult
        )
    }
}