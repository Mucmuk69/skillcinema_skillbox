package com.example.domain.entity.data_model_staff

data class StaffInfo(
    val age: Int?,
    val birthday: String?,
    val birthplace: String?,
    val death: String?,
    val deathplace: String?,
    val facts: List<String?>,
    val films: List<Film>,
    val growth: String?,
    val hasAwards: Int?,
    val nameEn: String?,
    val nameRu: String?,
    val personId: Int?,
    val posterUrl: String?,
    val profession: String?,
    val sex: String?,
    val spouses: List<Spouse>,
    val webUrl: String?
)