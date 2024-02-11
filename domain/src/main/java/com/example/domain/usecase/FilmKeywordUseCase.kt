package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_search.film_keyword.FilmKeyword

class FilmKeywordUseCase(private val filmDataInterface: FilmDataInterface) {
    suspend fun execute(keyword: String): FilmKeyword {
        return filmDataInterface.getFilmKeyword(keyword = keyword)
    }
}