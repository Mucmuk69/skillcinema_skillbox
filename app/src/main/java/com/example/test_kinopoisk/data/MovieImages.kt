package com.example.test_kinopoisk.data

//import android.os.Parcelable
//import com.example.domain.data.data_images.MovieImages
//import kotlinx.parcelize.Parcelize
//
//@Parcelize
//data class MovieImagesEntity(
//    val items: List<ItemEntity>,
//    val total: Int,
//    val totalPages: Int
//):Parcelable
//
//@Parcelize
//data class ItemEntity(
//    val imageUrl: String,
//    val previewUrl: String
//):Parcelable
//
//// Маппер для преобразования MovieImages в MovieImagesEntity
//object MovieImagesMapper {
//    fun mapToEntity(movieImages: MovieImages): MovieImagesEntity {
//        val itemEntities = movieImages.items.map {
//            ItemEntity(it.imageUrl, it.previewUrl)
//        }
//        return MovieImagesEntity(itemEntities, movieImages.total, movieImages.totalPages)
//    }
//}