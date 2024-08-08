package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatsBar(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(StatsBarContracts.BORDER_R))
            .height(StatsBarContracts.HEIGHT)
            .background(color = color)
    )
}

object StatsBarContracts {
    val HEIGHT = 20.dp
    val BORDER_R = 4.dp
}