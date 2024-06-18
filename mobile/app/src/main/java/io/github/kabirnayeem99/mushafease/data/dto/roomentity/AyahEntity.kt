package io.github.kabirnayeem99.mushafease.data.dto.roomentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Ayahs", foreignKeys = [ForeignKey(
        entity = SurahEntity::class,
        parentColumns = ["surah_id"],
        childColumns = ["surah_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AyahEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo(name = "surah_id") val surahId: Int,
    @ColumnInfo(name = "ayah_number") val ayahNumber: Int,
    val juz: Int,
    val page: Int,
    val text: String,
    @ColumnInfo(name = "text_nodiactric") val textNodiactric: String,
    @ColumnInfo(name = "text_tajweed") val textTajweed: String,
)
