package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_staff.ListStaff

class ListStaffUseCase(private val filmDataInterface: FilmDataInterface) {
    suspend fun execute(filmId: Int): List<ListStaff> {
        return filmDataInterface.getListStaff(filmId = filmId)
    }
}