package io.github.kabirnayeem99.mushafease.data.dto.roomentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Surahs")
data class SurahEntity(
    @PrimaryKey
    val surahId: Int,
    val name: String,
    val nameLatin: String,
    val numberOfAyah: Int
)
