package io.github.kabirnayeem99.mushafease.presentation.ui.directmushafpage

import io.github.kabirnayeem99.mushafease.domain.entities.MushafPage
import io.github.kabirnayeem99.mushafease.domain.entities.Surah

data class DirectMushafPageUiState(
    val isLoading: Boolean = false,
    val page: MushafPage = MushafPage(),
    val currentPageIndex: Int = 0,
    val maxPageIndex: Int = 603,
    val surahs: List<Surah> = emptyList(),
    val totalPageNumber: Int = 604,
)