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
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider

@Composable
fun CIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    enabled: Boolean = true,
    iconColor: Color? = null,
    onTap: () -> Unit,
    iconSize: Dp = C_ICON_SIZE
) {
    Box(
        modifier = modifier
            .clickable(enabled = enabled) { onTap() }
            .padding(CIconButtonContracts.PADDING_CONTENT),
        contentAlignment = Alignment.Center
    ) {
        CIcon(
            id = id,
            tint = iconColor,
            size = iconSize
        )
    }
}

object CIconButtonContracts {
    val PADDING_CONTENT = 5.dp
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