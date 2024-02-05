package com.example.domain.usecase

import com.example.domain.FilmDataInterface
import com.example.domain.entity.data_staff.StaffInfo

class StaffInfoUseCase(private val filmDataInterface: FilmDataInterface) {
    suspend fun execute(id: Int): StaffInfo {
        return filmDataInterface.getStaffInfo(id = id)
    }
}