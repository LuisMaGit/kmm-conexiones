package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.luisma.conexiones.android.core_ui.components.CLoader

@Composable
fun GameLoader(
    onBack: () -> Unit,
    lives: Int,
) {
    GameWrapper(
        onBack = onBack,
        lives = lives
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CLoader()
        }
    }
}

