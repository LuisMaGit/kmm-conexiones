package com.luisma.conexiones.android.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@Composable
fun CThemeProvider(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {


    val colors = if (darkTheme) {
        CThemeColors.dark()
    } else {
        CThemeColors.light()
    }

    CompositionLocalProvider(
        LocalCColors provides colors
    ) {
        content()
    }
}


object CTheme {
    val colors: CThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCColors.current
}