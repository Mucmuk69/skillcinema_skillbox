package com.example.data.entity

import com.example.domain.entity.data_model_similar_movies.Item
import com.example.domain.entity.data_model_similar_movies.SimilarMovies

data class SimilarMoviesData(
    val total: Int?,
    val items: List<ItemSimilarMoviesData>
)

data class ItemSimilarMoviesData(
    val filmId: Int?,
    val nameEn: String?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val relationType: String?
)

object SimilarMoviesMapper {
    fun mapToSimilarMovies(similarMoviesData: SimilarMoviesData): SimilarMovies {
        val items = similarMoviesData.items.map { itemSimilarMoviesData ->
            Item(
                itemSimilarMoviesData.filmId,
                itemSimilarMoviesData.nameEn,
                itemSimilarMoviesData.nameOriginal,
                itemSimilarMoviesData.nameRu,
                itemSimilarMoviesData.posterUrl,
                itemSimilarMoviesData.posterUrlPreview,
                itemSimilarMoviesData.relationType
            )
        }
        return SimilarMovies(similarMoviesData.total, items)
    }
}
