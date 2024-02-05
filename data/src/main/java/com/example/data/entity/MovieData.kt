package com.example.data.entity

import com.example.domain.entity.data_model_movie.Countries
import com.example.domain.entity.data_model_movie.Genres
import com.example.domain.entity.data_model_movie.Items
import com.example.domain.entity.data_model_movie.Movie

data class MovieData(
    var total: Int? = null,
    var totalPages: Int? = null,
    var items: List<ItemsData> = listOf()
)

data class ItemsData(
    var kinopoiskId: Int? = null,
    var imdbId: String? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var nameOriginal: String? = null,
    var countries: List<CountriesDataMovie> = listOf(),
    var genres: List<GenresDataMovie> = listOf(),
    var ratingKinopoisk: Double? = null,
    var ratingImdb: Double? = null,
    var year: Int? = null,
    var type: String? = null,
    var posterUrl: String? = null,
    var posterUrlPreview: String? = null
)

data class GenresDataMovie(
    var genre: String? = null
)

data class CountriesDataMovie(
    var country: String? = null
)

// Маппер для преобразования MovieData в Movie
object MovieMapper {
    fun mapToMovie(movieData: MovieData): Movie {
        val items = movieData.items.map { itemData ->
            Items(
                itemData.kinopoiskId,
                itemData.imdbId,
                itemData.nameRu,
                itemData.nameEn,
                itemData.nameOriginal,
                itemData.countries.map { countryData -> Countries(countryData.country) },
                itemData.genres.map { genreData -> Genres(genreData.genre) },
                itemData.ratingKinopoisk,
                itemData.ratingImdb,
                itemData.year,
                itemData.type,
                itemData.posterUrl,
                itemData.posterUrlPreview
            )
        }
        return Movie(movieData.total, movieData.totalPages, items)
    }
}