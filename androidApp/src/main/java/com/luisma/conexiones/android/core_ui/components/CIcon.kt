package com.luisma.conexiones.android.core_ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.cDefaultIconSize

@Composable
fun CIcon(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    tint: Color? = CTheme.colors.textDefault,
    size: Dp = cDefaultIconSize,
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = id),
        contentDescription = "",
        tint = tint ?: CTheme.colors.iconDefault
    )
}
