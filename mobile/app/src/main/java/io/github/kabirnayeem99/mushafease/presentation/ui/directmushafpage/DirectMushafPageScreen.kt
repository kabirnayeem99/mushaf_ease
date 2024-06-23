package io.github.kabirnayeem99.mushafease.presentation.ui.directmushafpage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.charlex.compose.material3.HtmlText
import io.github.kabirnayeem99.mushafease.presentation.common.BaseComposeScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DirectMushafPageScreen(viewModel: DirectMushafPageViewModel = viewModel()) {

    val uiState = viewModel.uiState.collectAsState().value
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { uiState.maxPageIndex + 1 })

    LaunchedEffect(true) { viewModel.fetchSurahs() }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.loadMushafPage(pagerState.currentPage + 1)
    }

    BaseComposeScreen { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            key = { it }
        ) {

            val fontSize = rememberedFontSize()

            LazyColumn(modifier = Modifier.padding(12.dp)) {
                val ayahs = uiState.page.ayahs.joinToString(" ") { it.text }
                item {
                    if (ayahs.isNotBlank()) {
                        HtmlText(
                            text = ayahs,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = fontSize.sp,
                            lineHeight = 40.sp,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberedFontSize(): Float {
    val context = LocalContext.current
    val displayMetrics = remember { context.resources.displayMetrics }

    val width = remember { displayMetrics.widthPixels }
    val height = remember { displayMetrics.heightPixels }

    return remember(width, height) {
        calculateFontSize(width, height)
    }
}

fun calculateScreenRatio(width: Int, height: Int): Float {
    return width.toFloat() / height.toFloat()
}

const val BASE_SCREEN_RATIO = 0.46213093f
const val BASE_FONT_SIZE = 30

fun calculateFontSize(width: Int, height: Int): Float {
    val screenRatio = calculateScreenRatio(width, height)
    return BASE_FONT_SIZE * (screenRatio / BASE_SCREEN_RATIO)
}

private const val TAG = "DirectMushafPageScreen"