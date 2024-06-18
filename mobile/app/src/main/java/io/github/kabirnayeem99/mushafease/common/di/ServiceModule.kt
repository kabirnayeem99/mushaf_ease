package io.github.kabirnayeem99.mushafease.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.kabirnayeem99.mushafease.data.service.database.MushafDatabase

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideMushafDatabase(@ApplicationContext context: Context) = MushafDatabase.get(context)

    @Provides
    fun provideAyahDao(db: MushafDatabase) = db.ayahDao()

    @Provides
    fun provideSurahDao(db: MushafDatabase) = db.surahDao()
}