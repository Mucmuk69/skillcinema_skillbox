package com.example.data

import com.example.data.entity.CountriesAndGenresMapper
import com.example.data.entity.FilmInfoMapper
import com.example.data.entity.FilmKeywordMapper
import com.example.data.entity.ListStaffMapper
import com.example.data.entity.MovieImagesMapper
import com.example.data.entity.MovieMapper
import com.example.data.entity.SeasonsMapper
import com.example.data.entity.SimilarMoviesMapper
import com.example.data.entity.StaffInfoMapper
import com.example.data.entity.StaffKeywordMapper
import com.example.data.retrofit.RetrofitInstance
import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_country_and_genre.CountriesAndGenres
import com.example.domain.entity.data_model_movie.Movie
import com.example.domain.entity.data_model_movie_info.FilmInfo
import com.example.domain.entity.data_model_search.film_keyword.FilmKeyword
import com.example.domain.entity.data_model_search.staff_keyword.StaffKeyword
import com.example.domain.entity.data_model_serial_seasons.Seasons
import com.example.domain.entity.data_model_similar_movies.SimilarMovies
import com.example.domain.entity.data_model_staff.ListStaff
import com.example.domain.entity.data_model_staff.StaffInfo

class FilmDataInterfaceImpl : FilmDataInterface {
    private val movieImagesMapper = MovieImagesMapper
    private val countriesAndGenresMapper = CountriesAndGenresMapper
    private val movieMapper = MovieMapper
    private val filmInfoMapper = FilmInfoMapper
    private val seasonsMapper = SeasonsMapper
    private val staffInfoMapper = StaffInfoMapper
    private val listStaffMapper = ListStaffMapper
    private val similarMoviesMapper = SimilarMoviesMapper
    private val filmKeywordMapper = FilmKeywordMapper
    private val staffKeywordMapper = StaffKeywordMapper

    //Премьеры
    override suspend fun getPremieres(year: Int, month: String): Movie =
        movieMapper.mapToMovie(RetrofitInstance.retrofit.premieres(year = year, month = month))


    //Динамическая подборка
    override suspend fun getDynamicFilms(
        countries: List<Int>,
        genres: List<Int>,
        type: String,
        page: Int
    ): Movie =
        movieMapper.mapToMovie(
            RetrofitInstance.retrofit.dynamicFilms(
                countries = countries,
                genres = genres,
                type = type,
                page = page
            )
        )


    //Список стран и жанров
    override suspend fun getCountriesAndGenres(): CountriesAndGenres =
        countriesAndGenresMapper.mapToCountriesAndGenres(RetrofitInstance.retrofit.countriesAndGenres())


    //Подборка различных топов фильмов
    override suspend fun getFilmCollections(type: String, page: Int): Movie =
        movieMapper.mapToMovie(RetrofitInstance.retrofit.filmCollections(type = type, page = page))


    //Получить инфо о фильме
    override suspend fun getFilmInfo(id: Int): FilmInfo =
        filmInfoMapper.mapToFilmInfo(RetrofitInstance.retrofit.filmInfo(id = id))


    //Получить список актеров, режиссеров и т.д.
    override suspend fun getListStaff(filmId: Int): List<ListStaff> =
        listStaffMapper.mapToListStaff(RetrofitInstance.retrofit.listStaff(filmId = filmId))


    //Получить информацию об актерах, режиссерах и т.д.
    override suspend fun getStaffInfo(id: Int): StaffInfo =
        staffInfoMapper.mapToStaffInfo(RetrofitInstance.retrofit.staffInfo(id = id))


    //Получить инфо о сезонах сериала
    override suspend fun getSerialSeasons(id: Int): Seasons =
        seasonsMapper.mapToSeasons(RetrofitInstance.retrofit.serialSeasonsInfo(id = id))


    //Получить кадры из фильма и т.п.
    override suspend fun getMovieImages(id: Int, type: String) =
        movieImagesMapper.mapToMovieImages(
            RetrofitInstance.retrofit.movieImages(id = id, type = type)
        )

    //Получить список похожих фильмов
    override suspend fun getSimilarMovies(id: Int): SimilarMovies =
        similarMoviesMapper.mapToSimilarMovies(
            RetrofitInstance.retrofit.similarMovies(id = id)
        )

    //Получить список фильмов по ключевому слову
    override suspend fun getFilmKeyword(keyword: String): FilmKeyword =
        filmKeywordMapper.mapToFilmKeyword(
            RetrofitInstance.retrofit.filmKeyword(keyword = keyword)
        )

    //Получить список актеров, режиссеров и т.п. по имени
    override suspend fun getStaffKeyword(name: String): StaffKeyword =
        staffKeywordMapper.mapToStaffKeyword(
            RetrofitInstance.retrofit.staffKeyword(name = name)
        )

    //Получить список фильмов по различным фильтрам в поиске
    override suspend fun getFilmSearching(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        keyword: String
    ): Movie =
        movieMapper.mapToMovie(
            RetrofitInstance.retrofit.filmSearching(
                countries = countries,
                genres = genres,
                order = order,
                type = type,
                ratingFrom = ratingFrom,
                ratingTo = ratingTo,
                yearFrom = yearFrom,
                yearTo = yearTo,
                keyword = keyword
            )
        )
}