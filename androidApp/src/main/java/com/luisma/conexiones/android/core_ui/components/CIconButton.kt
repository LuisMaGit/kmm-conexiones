package com.luisma.conexiones.android.core_ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cDefaultIconSize
import com.luisma.conexiones.android.core_ui.theme.cSpace4

@Composable
fun CIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    enabled: Boolean = true,
    iconColor: Color? = null,
    onTap: () -> Unit,
    iconSize: Dp = cDefaultIconSize
) {
    Box(
        modifier = modifier
            .clickable(enabled = enabled) { onTap() }
            .padding(cSpace4),
        contentAlignment = Alignment.Center
    ) {
        CIcon(
            id = id,
            tint = iconColor,
            size = iconSize
        )
    }
}



@Preview
@Composable
private fun CIconButtonPreview() {
    CThemeProvider {
        CIconButton(
            id = R.drawable.ic_rocket,
            onTap = {}
        )
    }
}