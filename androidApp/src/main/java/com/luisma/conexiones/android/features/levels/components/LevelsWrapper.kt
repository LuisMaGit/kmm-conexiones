package com.luisma.conexiones.android.features.levels.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.luisma.conexiones.android.features.app_bar.AppBarBuilder
import com.luisma.conexiones.android.features.app_bar.AppBarLeadingComponentType

@Composable
fun LevelsWrapper(
    lives: Int?,
    onTapLogo: () -> Unit,
    onDismissTutorial: () -> Unit,
    onDismissLives: () -> Unit,
    openTutorial: Boolean,
    content: @Composable () -> Unit,
) {
    Column {
        AppBarBuilder(
            leadingComponentType = AppBarLeadingComponentType.Logo,
            lives = lives,
            onTapLogo = onTapLogo,
            openTutorial = openTutorial,
            onDismissTutorial = onDismissTutorial,
            onDismissLives = onDismissLives
        )
        content()
    }
}