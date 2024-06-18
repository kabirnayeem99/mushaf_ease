package io.github.kabirnayeem99.mushafease.data.mappers

import io.github.kabirnayeem99.mushafease.data.dto.roomentity.AyahEntity
import io.github.kabirnayeem99.mushafease.domain.entities.Ayah

fun List<AyahEntity>.toAyahList() = map { a -> a.toAyah() }

fun AyahEntity.toAyah() = Ayah(number = id ?: 0, key = "$id-$surahId", text = text)