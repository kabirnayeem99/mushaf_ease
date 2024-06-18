package io.github.kabirnayeem99.mushafease.data.service.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.kabirnayeem99.mushafease.data.dto.roomentity.SurahEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SurahDao {
    @Query("SELECT * FROM Surahs")
    fun surahs(): Flow<List<SurahEntity>>

    @Query("SELECT * FROM Surahs WHERE surah_id in (:surahIds)")
    fun surahsByIds(surahIds: IntArray): List<SurahEntity>
}