package com.example.data.entity

import com.example.domain.entity.data_model_images.Item
import com.example.domain.entity.data_model_images.MovieImages


data class MovieImagesData(
    val items: List<ItemData>,
    val total: Int? = null,
    val totalPages: Int? = null
)


data class ItemData(
    val imageUrl: String? = null,
    val previewUrl: String? = null
)

// Маппер для преобразования MovieImagesEntity в MovieImages
object MovieImagesMapper {
    fun mapToMovieImages(movieImagesData: MovieImagesData): MovieImages {
        val items = movieImagesData.items.map {
            Item(it.imageUrl, it.previewUrl)
        }
        return MovieImages(items, movieImagesData.total, movieImagesData.totalPages)
    }
}