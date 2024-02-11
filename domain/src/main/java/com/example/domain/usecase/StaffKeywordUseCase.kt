package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_model_search.staff_keyword.StaffKeyword

class StaffKeywordUseCase(private val filmDataInterface: FilmDataInterface) {
    suspend fun execute(name: String): StaffKeyword {
        return filmDataInterface.getStaffKeyword(name = name)
    }
}