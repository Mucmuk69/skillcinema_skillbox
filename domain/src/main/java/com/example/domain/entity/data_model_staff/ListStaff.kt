package com.example.domain.entity.data_model_staff


data class ListStaff(
    var staffId: Int? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var description: String? = null,
    var posterUrl: String? = null,
    var professionText: String? = null,
    var professionKey: String? = null
)