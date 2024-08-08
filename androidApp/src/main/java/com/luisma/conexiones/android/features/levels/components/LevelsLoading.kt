package com.luisma.conexiones.android.features.levels.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.luisma.conexiones.android.core_ui.components.CLoader

@Composable
fun LevelsLoading(
    bottomFraction: Float = .45f
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CLoader()
        Spacer(modifier = Modifier.fillMaxHeight(bottomFraction))
    }
}