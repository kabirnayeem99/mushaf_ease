package io.github.kabirnayeem99.mushafease.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.kabirnayeem99.mushafease.data.repositories.MushafRepositoryImpl
import io.github.kabirnayeem99.mushafease.data.service.database.dao.AyahDao
import io.github.kabirnayeem99.mushafease.data.service.database.dao.SurahDao
import io.github.kabirnayeem99.mushafease.domain.repositories.MushafRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideMushafRepository(surahDao: SurahDao, ayahDao: AyahDao): MushafRepository {
        return MushafRepositoryImpl(surahDao, ayahDao)
    }
}