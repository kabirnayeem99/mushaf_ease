package io.github.kabirnayeem99.mushafease.domain.entities

data class MushafPage(
    val number: Int = 0,
    val singleSurah: Boolean = false,
    val ayahs: List<Ayah> = emptyList(),
    val surahs: Map<Int, Surah> = emptyMap(),
    val juz: Int = 0,
)