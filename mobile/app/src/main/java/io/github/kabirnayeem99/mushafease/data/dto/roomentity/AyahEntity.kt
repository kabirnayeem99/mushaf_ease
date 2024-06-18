package io.github.kabirnayeem99.mushafease.data.dto.roomentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "Ayahs")
@Fts4
data class AyahEntity(
    @PrimaryKey @ColumnInfo(name = "rowid") val rowId: Int,
    val surahId: Int,
    val ayahNumber: Int,
    val juz: Int,
    val page: Int,
    val text: String,
    val textNoDiacritic: String,
    val textTajweed: String
)
