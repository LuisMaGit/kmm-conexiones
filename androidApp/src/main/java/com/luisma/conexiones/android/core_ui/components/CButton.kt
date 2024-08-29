package com.luisma.conexiones.android.core_ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider

@Composable
fun CButton(
    modifier: Modifier = Modifier,
    color: Color = CColor.green,
    text: String,
    @DrawableRes iconId: Int,
    onTap: () -> Unit
) {

    val shape = RoundedCornerShape(size = CButtonContracts.BOX_BORDER_R)

    Box(
        modifier = modifier
            .height(CButtonContracts.BOX_TOTAL_H)
            .clip(shape = shape)
            .background(CColor.black)
    ) {
        Box(
            modifier = Modifier
                .height(CButtonContracts.BOX_INTERNAL_H)
                .fillMaxWidth()
                .clip(shape = shape)
                .border(
                    width = CButtonContracts.BORDER_PADDING,
                    color = CColor.black,
                    shape = shape
                )
                .padding(CButtonContracts.BORDER_PADDING)
                .background(
                    color = color,
                    shape = shape
                )
                .clickable {
                    onTap()
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CIcon(
                    modifier = Modifier.padding(end = CButtonContracts.ICON_TEXT_SPACE),
                    id = iconId,
                    size = CButtonContracts.ICON_SIZE,
                    tint = CColor.black
                )
                CText(
                    text = text,
                    fontSize = CButtonContracts.TEXT_FONT_SIZE,
                    maxLines = 1,
                    color = CColor.black
                )
            }
        }
    }
}

object CButtonContracts {
    val TEXT_FONT_SIZE = 14.sp
    val ICON_SIZE = 10.dp
    val ICON_TEXT_SPACE = 8.dp
    val BORDER_PADDING = 0.75.dp
    val BOX_INTERNAL_H = 48.dp
    val BOX_TOTAL_H = 51.dp
    val BOX_BORDER_R = 4.dp
}

@Preview
@Composable
private fun CButtonPreview() {
    CThemeProvider(
        darkTheme = false
    ) {
        CButton(
            modifier = Modifier.width(100.dp),
            text = "Enviar",
            iconId = R.drawable.ic_rocket,
            onTap = {}
        )
    }
}