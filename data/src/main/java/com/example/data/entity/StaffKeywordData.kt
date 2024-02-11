package com.example.data.entity

import com.example.domain.entity.data_model_search.staff_keyword.Item
import com.example.domain.entity.data_model_search.staff_keyword.StaffKeyword

data class StaffKeywordData(
    val items: List<ItemStaffKeywordData>,
    val total: Int?
)

data class ItemStaffKeywordData(
    val kinopoiskId: Int?,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val sex: String?,
    val webUrl: String?
)

object StaffKeywordMapper {
    fun mapToStaffKeyword(staffKeywordData: StaffKeywordData): StaffKeyword {
        val items = staffKeywordData.items.map { item ->
            Item(
                item.kinopoiskId,
                item.nameEn,
                item.nameRu,
                item.posterUrl,
                item.sex,
                item.webUrl
            )
        }
        return StaffKeyword(items, staffKeywordData.total)
    }
}
