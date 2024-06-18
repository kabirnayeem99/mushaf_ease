package io.github.kabirnayeem99.mushafease.presentation.ui.directmushafpage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.kabirnayeem99.mushafease.presentation.common.BaseComposeScreen
import io.github.kabirnayeem99.mushafease.presentation.common.htmlcompose.HtmlText

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun DirectMushafPageScreen(viewModel: DirectMushafPageViewModel = viewModel()) {

    val uiState = viewModel.uiState.collectAsState().value
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { uiState.maxPageIndex + 1 })

    LaunchedEffect(true) { viewModel.fetchSurahs() }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.loadMushafPage(pagerState.currentPage + 1)
    }

    BaseComposeScreen { innerPadding ->
        HorizontalPager(state = pagerState) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp),
            ) {
                uiState.page.ayahs.forEach { ayah ->
                    item {
                        HtmlText(
                            text = ayah.text,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 24.sp,
                            lineHeight = 1.5.sp,
                        )
                    }
                }
            }
        }
    }
}