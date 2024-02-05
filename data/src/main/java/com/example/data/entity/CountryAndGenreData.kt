package com.example.data.entity

import com.example.domain.entity.data_model_country_and_genre.Countries
import com.example.domain.entity.data_model_country_and_genre.CountriesAndGenres
import com.example.domain.entity.data_model_country_and_genre.Genres

data class CountriesAndGenresData(
    var genres: List<GenresData> = listOf(),
    var countries: List<CountriesData> = listOf()
)

data class CountriesData(
    var id: Int? = null,
    var country: String? = null
)

data class GenresData(
    var id: Int? = null,
    var genre: String? = null
)

// Маппер для преобразования CountriesAndGenresData в CountriesAndGenres
object CountriesAndGenresMapper {
    fun mapToCountriesAndGenres(countriesAndGenresData: CountriesAndGenresData): CountriesAndGenres {
        val genres = countriesAndGenresData.genres.map {
            Genres(it.id, it.genre)
        }
        val countries = countriesAndGenresData.countries.map {
            Countries(it.id, it.country)
        }
        return CountriesAndGenres(genres, countries)
    }
}