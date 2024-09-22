package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.luisma.conexiones.android.features.app_bar.AppBarBuilder
import com.luisma.conexiones.android.features.app_bar.AppBarLeadingComponentType

@Composable
fun GameWrapper(
    onBack: () -> Unit,
    lives: Int,
    content: @Composable () -> Unit
) {
    Column {
        AppBarBuilder(
            onBack = onBack,
            lives = lives,
            leadingComponentType = AppBarLeadingComponentType.BackArrow
        )
        content()
    }
}