package com.example.data.entity

import com.example.domain.entity.data_model_staff.ListStaff

data class ListStaffData(
    var staffId: Int? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var description: String? = null,
    var posterUrl: String? = null,
    var professionText: String? = null,
    var professionKey: String? = null
)

// Маппер для преобразования ListStaffData в ListStaff
object ListStaffMapper {
    fun mapToListStaff(listStaffData: List<ListStaffData>): List<ListStaff> {
        return listStaffData.map {data->
            ListStaff(
                data.staffId,
                data.nameRu,
                data.nameEn,
                data.description,
                data.posterUrl,
                data.professionText,
                data.professionKey
            )
        }
    }
}