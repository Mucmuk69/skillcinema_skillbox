package com.example.data.entity

import com.example.domain.entity.data_model_staff.Film
import com.example.domain.entity.data_model_staff.Spouse
import com.example.domain.entity.data_model_staff.StaffInfo

data class StaffInfoData(
    val age: Int?,
    val birthday: String?,
    val birthplace: String?,
    val death: String?,
    val deathplace: String?,
    val facts: List<String?>,
    val films: List<FilmData>,
    val growth: String?,
    val hasAwards: Int?,
    val nameEn: String?,
    val nameRu: String?,
    val personId: Int?,
    val posterUrl: String?,
    val profession: String?,
    val sex: String?,
    val spouses: List<SpouseData>,
    val webUrl: String?
)

data class SpouseData(
    val children: Int?,
    val divorced: Boolean?,
    val divorcedReason: String?,
    val name: String?,
    val personId: Int?,
    val relation: String?,
    val sex: String?,
    val webUrl: String?
)

data class FilmData(
    val description: String?,
    val filmId: Int?,
    val general: Boolean?,
    val nameEn: String?,
    val nameRu: String?,
    val professionKey: String?,
    val rating: String?
)

// Маппер для преобразования StaffInfoData в StaffInfo
object StaffInfoMapper {
    fun mapToStaffInfo(staffInfoData: StaffInfoData): StaffInfo {
        val films = staffInfoData.films.map { filmData ->
            Film(
                filmData.description,
                filmData.filmId,
                filmData.general,
                filmData.nameEn,
                filmData.nameRu,
                filmData.professionKey,
                filmData.rating
            )
        }
        val spouses = staffInfoData.spouses.map { spouseData ->
            Spouse(
                spouseData.children,
                spouseData.divorced,
                spouseData.divorcedReason,
                spouseData.name,
                spouseData.personId,
                spouseData.relation,
                spouseData.sex,
                spouseData.webUrl
            )
        }
        return StaffInfo(
            staffInfoData.age,
            staffInfoData.birthday,
            staffInfoData.birthplace,
            staffInfoData.death,
            staffInfoData.deathplace,
            staffInfoData.facts,
            films,
            staffInfoData.growth,
            staffInfoData.hasAwards,
            staffInfoData.nameEn,
            staffInfoData.nameRu,
            staffInfoData.personId,
            staffInfoData.posterUrl,
            staffInfoData.profession,
            staffInfoData.sex,
            spouses,
            staffInfoData.webUrl
        )
    }
}
