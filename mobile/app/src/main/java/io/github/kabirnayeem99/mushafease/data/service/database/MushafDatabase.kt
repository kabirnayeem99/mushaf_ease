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
        private var DB_INSTANCE: MushafDatabase? = null

        private const val DB_NAME = "mushaf_database"
        private const val ASSET_DB_FILE = "indopak_mushaf.db"

        fun get(context: Context): MushafDatabase {
            return DB_INSTANCE ?: synchronized(DB_NAME) {
                val appContext = context.applicationContext
                val dbBuilder =
                    Room.databaseBuilder(appContext, MushafDatabase::class.java, DB_NAME)
                dbBuilder.apply {
                    createFromAsset(ASSET_DB_FILE)
                    fallbackToDestructiveMigration()
                }
                val instance = dbBuilder.build()
                DB_INSTANCE = instance
                instance
            }
        }
    }
}