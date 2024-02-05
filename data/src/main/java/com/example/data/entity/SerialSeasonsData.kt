package com.example.data.entity

import com.example.domain.entity.data_model_serial_seasons.Episode
import com.example.domain.entity.data_model_serial_seasons.Item
import com.example.domain.entity.data_model_serial_seasons.Seasons

data class SerialSeasonsData(
    val items: List<ItemSeasonsData>,
    val total: Int
)

data class ItemSeasonsData(
    val episodes: List<EpisodeSeasonsData>,
    val number: Int
)

data class EpisodeSeasonsData(
    val episodeNumber: Int,
    val nameEn: String,
    val nameRu: String,
    val releaseDate: String,
    val seasonNumber: Int,
    val synopsis: String
)

// Маппер для преобразования SerialSeasonsData в Seasons
object SeasonsMapper {
    fun mapToSeasons(serialSeasonsData: SerialSeasonsData): Seasons {
        val items = serialSeasonsData.items.map { item ->
            Item(
                item.episodes.map { episodeSeasonsData ->
                    Episode(
                        episodeSeasonsData.episodeNumber,
                        episodeSeasonsData.nameEn,
                        episodeSeasonsData.nameRu,
                        episodeSeasonsData.releaseDate,
                        episodeSeasonsData.seasonNumber,
                        episodeSeasonsData.synopsis
                    )
                },
                item.number
            )
        }
        return Seasons(items, serialSeasonsData.total)
    }
}