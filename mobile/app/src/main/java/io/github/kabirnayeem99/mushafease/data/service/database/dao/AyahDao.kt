package io.github.kabirnayeem99.mushafease.data.service.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.kabirnayeem99.mushafease.data.dto.roomentity.AyahEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AyahDao {
    @Query("SELECT * FROM Ayahs")
    fun getAllAyahs(): Flow<List<AyahEntity>>

    @Query("SELECT * FROM Ayahs WHERE surah_id=:surahId")
    fun getAyahsFromSurah(surahId: Int): Flow<List<AyahEntity>>

    @Query("SELECT * FROM Ayahs WHERE page=:page")
    fun getAyahsFromPage(page: Int): Flow<List<AyahEntity>>

    @Query("SELECT * FROM Ayahs WHERE page=:page")
    suspend fun getAyahsFromPageSync(page: Int): List<AyahEntity>

    @Query("SELECT * FROM Ayahs WHERE juz=:juz")
    fun getAyahsFromJuz(juz: Int): Flow<List<AyahEntity>>
}