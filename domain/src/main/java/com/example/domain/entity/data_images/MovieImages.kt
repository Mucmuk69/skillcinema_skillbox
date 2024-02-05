package com.example.domain.entity.data_images

data class MovieImages(
    val items: List<Item>,
    val total: Int? = null,
    val totalPages: Int? = null
)