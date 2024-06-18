package io.github.kabirnayeem99.mushafease.data.mappers

import io.github.kabirnayeem99.mushafease.data.dto.roomentity.SurahEntity
import io.github.kabirnayeem99.mushafease.domain.entities.Surah

fun List<SurahEntity>.toSurahList() = map { surahDto -> surahDto.toSurah() }

fun SurahEntity.toSurah() = Surah(
    number = surahId ?: -1, totalAyah = numberOfAyah, name = "$name - $nameLatin"
)