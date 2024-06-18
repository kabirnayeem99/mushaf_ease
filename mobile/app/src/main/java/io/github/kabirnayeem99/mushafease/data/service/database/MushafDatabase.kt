package io.github.kabirnayeem99.mushafease.data.service.database

import androidx.room.RoomDatabase
import io.github.kabirnayeem99.mushafease.data.service.database.dao.AyahDao
import io.github.kabirnayeem99.mushafease.data.service.database.dao.SurahDao

abstract class MushafDatabase: RoomDatabase() {
    abstract fun surahDao(): SurahDao
    abstract fun ayahDao(): AyahDao
}