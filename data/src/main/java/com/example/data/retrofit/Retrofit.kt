package com.example.data.retrofit

import com.example.data.entity.CountriesAndGenresData
import com.example.data.entity.FilmInfoData
import com.example.data.entity.FilmKeywordData
import com.example.data.entity.ListStaffData
import com.example.data.entity.MovieData
import com.example.data.entity.MovieImagesData
import com.example.data.entity.SerialSeasonsData
import com.example.data.entity.SimilarMoviesData
import com.example.data.entity.StaffInfoData
import com.example.data.entity.StaffKeywordData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitInstance {
    val retrofit: KinopoiskApi = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KinopoiskApi::class.java)
}


interface KinopoiskApi {

    //Премьеры фильмов
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun premieres(
        @Query("year") year: Int, @Query("month") month: String,
    ): MovieData

    //Динамическая подборка по стране и жанру
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun dynamicFilms(
        @Query("countries") countries: List<Int>,
        @Query("genres") genres: List<Int>,
        @Query("type") type: String,
        @Query("page") page: Int
    ): MovieData

    //Список стран и жанров
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun countriesAndGenres(): CountriesAndGenresData

    //Подборки фильмов
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/collections")
    suspend fun filmCollections(
        @Query("type") type: String,
        @Query("page") page: Int,
    ): MovieData

    //Инфо о фильме
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun filmInfo(
        @Path("id") id: Int
    ): FilmInfoData

    //Инфо о сезонах сериала
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun serialSeasonsInfo(
        @Path("id") id: Int
    ): SerialSeasonsData

    //Список актеров, режиссеров и т.д.
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun listStaff(
        @Query("filmId") filmId: Int
    ): List<ListStaffData>

    //Инфо об актерах, режиссерах и т.д. по id
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff/{id}")
    suspend fun staffInfo(
        @Path("id") id: Int
    ): StaffInfoData

    //Инфо о фильме
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun movieImages(
        @Path("id") id: Int,
        @Query("type") type: String
    ): MovieImagesData

    //Список похожих фильмов
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun similarMovies(
        @Path("id") id: Int
    ): SimilarMoviesData

    //Поиск фильма по названию
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun filmKeyword(
        @Query("keyword") keyword: String
    ): FilmKeywordData

    //Поиск актера, режиссера и т.п. по имени
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/persons")
    suspend fun staffKeyword(
        @Query("name") name: String
    ): StaffKeywordData

    //Динамическая подборка по стране и жанру
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun filmSearching(
        @Query("countries") countries: List<Int>,
        @Query("genres") genres: List<Int>,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int
    ): MovieData

    private companion object {
        private const val api_key = "c305061c-5d0f-42ad-ac09-a0e0005d8710"
    }
}

