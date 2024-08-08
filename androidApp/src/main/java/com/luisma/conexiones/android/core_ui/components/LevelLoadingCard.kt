package com.luisma.conexiones.android.core_ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun LevelLoadingCard(
    modifier: Modifier,
    idx: Int,
    onAppear: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        if (idx == 0) {
            onAppear()
        }
    }

    LevelCard(
        modifier = modifier,
        card = LevelCardData.empty(),
        loading = true
    )
}