package io.github.kabirnayeem99.mushafease.data.dto.roomentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Surahs")
data class SurahEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "surah_id") val surahId: Int? = 0,
    val name: String,
    @ColumnInfo(name = "name_latin") val nameLatin: String,
    @ColumnInfo(name = "number_of_ayah") val numberOfAyah: Int,
)

