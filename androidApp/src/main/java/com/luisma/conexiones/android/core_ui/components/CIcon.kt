package com.luisma.conexiones.android.core_ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.theme.CTheme

@Composable
fun CIcon(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    tint: Color? = CTheme.colors.textDefault,
    size: Dp = C_ICON_SIZE,
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = id),
        contentDescription = "",
        tint = tint ?: CTheme.colors.iconDefault
    )
}

val C_ICON_SIZE = 24.dp