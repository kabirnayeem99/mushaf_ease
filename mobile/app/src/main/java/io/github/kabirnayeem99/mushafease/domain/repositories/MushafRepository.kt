package io.github.kabirnayeem99.mushafease.domain.repositories

import io.github.kabirnayeem99.mushafease.domain.entities.Ayah
import io.github.kabirnayeem99.mushafease.domain.entities.MushafPage
import io.github.kabirnayeem99.mushafease.domain.entities.Resource
import io.github.kabirnayeem99.mushafease.domain.entities.Surah
import kotlinx.coroutines.flow.Flow

interface MushafRepository {
    fun surahs(): Flow<Resource<List<Surah>>>

    fun ayahByPage(pageNumber: Int): Flow<Resource<MushafPage>>
}