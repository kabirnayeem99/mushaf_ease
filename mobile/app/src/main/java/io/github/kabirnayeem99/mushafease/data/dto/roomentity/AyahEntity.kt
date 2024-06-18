package io.github.kabirnayeem99.mushafease.data.dto.roomentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "Ayahs")
@Fts4
data class AyahEntity(
    @PrimaryKey @ColumnInfo("rowid") val rowid: Int? = null,
    @ColumnInfo("surah_id") val surahId: Int,
    @ColumnInfo("ayah_number") val ayahNumber: Int,
    val juz: Int,
    val page: Int,
    val text: String,
    @ColumnInfo("text_nodiactric") val textNoDiacritic: String,
    @ColumnInfo("text_tajweed") val textTajweed: String,
    @ColumnInfo("FOREIGN") val foreign: Int? = null,
)
