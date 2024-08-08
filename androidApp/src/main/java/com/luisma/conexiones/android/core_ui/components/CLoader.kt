package com.luisma.conexiones.android.core_ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider

@Composable
fun CLoader(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cLoader")
    val animation by infiniteTransition.animateFloat(
        targetValue = 360f,
        initialValue = 0f,
        label = "c_Loader",
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    CLogo(
        modifier  = modifier.graphicsLayer {
            rotationX = animation
        }
    )
}

@Preview
@Composable
private fun CLoaderPreview() {
    CThemeProvider {
        CLoader()
    }
}