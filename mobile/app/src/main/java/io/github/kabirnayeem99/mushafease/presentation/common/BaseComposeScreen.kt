package io.github.kabirnayeem99.mushafease.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import io.github.kabirnayeem99.mushafease.presentation.config.MushafEaseTheme

@Composable
fun BaseComposeScreen(
    topBar: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    MushafEaseTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Scaffold(
                topBar = topBar,
                containerColor = Color.White,
                snackbarHost = {
                    if (snackbarHostState != null) SnackbarHost(hostState = snackbarHostState)
                }
            ) { scaffoldPadding ->
                content(scaffoldPadding)
            }
        }
    }
}