package io.github.kabirnayeem99.mushafease.data.dto.roomentity

import androidx.room.Entity

@Entity(tableName = "Surahs")
data class SurahEntity(
    val surahId: Int,
    val name: String,
    val nameLatin: String,
    val numberOfAyah: Int
)
