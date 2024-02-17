package com.example.domain

import com.example.domain.entity.data_model_country_and_genre.CountriesAndGenres
import com.example.domain.entity.data_model_images.MovieImages
import com.example.domain.entity.data_model_movie.Movie
import com.example.domain.entity.data_model_movie_info.FilmInfo
import com.example.domain.entity.data_model_search.film_keyword.FilmKeyword
import com.example.domain.entity.data_model_search.staff_keyword.StaffKeyword
import com.example.domain.entity.data_model_serial_seasons.Seasons
import com.example.domain.entity.data_model_similar_movies.SimilarMovies
import com.example.domain.entity.data_model_staff.ListStaff
import com.example.domain.entity.data_model_staff.StaffInfo

interface FilmDataInterface {

    //Премьеры фильмов
    suspend fun getPremieres(year: Int, month: String): Movie

    //Динамическая подборка по стране и жанру
    suspend fun getDynamicFilms(
        countries: List<Int>,
        genres: List<Int>,
        type: String,
        page: Int
    ): Movie

    //Список стран и жанров
    suspend fun getCountriesAndGenres(): CountriesAndGenres

    //Подборки фильмов
    suspend fun getFilmCollections(type: String, page: Int): Movie

    //Получить инфо о фильме
    suspend fun getFilmInfo(id: Int): FilmInfo

    //Получить список актеров, режиссеров и т.д.
    suspend fun getListStaff(filmId: Int): List<ListStaff>

    //Получить инфо об актерах, режиссерах и т.д.
    suspend fun getStaffInfo(id: Int): StaffInfo

    //Получить инфо о сезонах сериала
    suspend fun getSerialSeasons(id: Int): Seasons

    //Получить кадры из фильма и т.п.
    suspend fun getMovieImages(id: Int, type: String): MovieImages

    //Получить список похожих фильмов
    suspend fun getSimilarMovies(id: Int): SimilarMovies

    //Получить список фильмов по названию
    suspend fun getFilmKeyword(keyword: String): FilmKeyword

    //Получить список актеров, режиссеров и т.п. по имени
    suspend fun getStaffKeyword(name: String): StaffKeyword

    //Получить список фильмов по различным фильтрам в поиске
    suspend fun getFilmSearching(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int
    ): Movie
}