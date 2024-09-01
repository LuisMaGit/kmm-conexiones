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
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4

@Composable
fun StatsBar(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(cBorderRadius4))
            .height(20.dp)
            .background(color = color)
    )
}

