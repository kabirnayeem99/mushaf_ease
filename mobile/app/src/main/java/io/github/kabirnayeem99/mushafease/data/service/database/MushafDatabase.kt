package io.github.kabirnayeem99.mushafease.data.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.kabirnayeem99.mushafease.data.dto.roomentity.AyahEntity
import io.github.kabirnayeem99.mushafease.data.dto.roomentity.SurahEntity
import io.github.kabirnayeem99.mushafease.data.service.database.dao.AyahDao
import io.github.kabirnayeem99.mushafease.data.service.database.dao.SurahDao

@Database(exportSchema = false, entities = [AyahEntity::class, SurahEntity::class], version = 1)
abstract class MushafDatabase : RoomDatabase() {
    abstract fun surahDao(): SurahDao
    abstract fun ayahDao(): AyahDao

    companion object {
        @Volatile
        private var INSTANCE: MushafDatabase? = null

        fun getDatabase(context: Context): MushafDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MushafDatabase::class.java, "mushaf_database"
                ).createFromAsset("indopak_mushaf.db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}