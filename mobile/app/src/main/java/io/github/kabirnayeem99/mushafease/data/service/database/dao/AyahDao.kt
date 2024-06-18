package io.github.kabirnayeem99.mushafease.data.service.database.dao

import androidx.room.Query
import io.github.kabirnayeem99.mushafease.data.dto.roomentity.AyahEntity
import kotlinx.coroutines.flow.Flow

interface AyahDao {
    @Query("SELECT * FROM Ayahs")
    fun getAllAyahs(): Flow<List<AyahEntity>>

    @Query("SELECT * FROM Ayahs WHERE surahId=:surahId")
    fun getAyahsFromSurah(surahId: Int): Flow<List<AyahEntity>>

    @Query("SELECT * FROM Ayahs WHERE page=:page")
    fun getAyahsFromPage(page: Int): Flow<List<AyahEntity>>

    @Query("SELECT * FROM Ayahs WHERE juz=:juz")
    fun getAyahsFromJuz(juz: Int): Flow<List<AyahEntity>>
}