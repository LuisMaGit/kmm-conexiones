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
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4
import com.luisma.conexiones.android.core_ui.theme.cFontSize16
import com.luisma.conexiones.android.core_ui.theme.cSpace8

@Composable
fun CButton(
    modifier: Modifier = Modifier,
    color: Color = CColor.green,
    text: String,
    @DrawableRes iconId: Int,
    onTap: () -> Unit
) {

    val shape = RoundedCornerShape(size = cBorderRadius4)
    val borderPadding = 0.75.dp
    Box(
        modifier = modifier
            .height(51.dp)
            .clip(shape = shape)
            .background(CColor.black)
    ) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clip(shape = shape)
                .border(
                    width = borderPadding,
                    color = CColor.black,
                    shape = shape
                )
                .padding(borderPadding)
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
                    modifier = Modifier.padding(end = cSpace8),
                    id = iconId,
                    size = 14.dp,
                    tint = CColor.black
                )
                CText(
                    text = text,
                    fontSize = cFontSize16.fontSizeNonScaledSp,
                    maxLines = 1,
                    color = CColor.black
                )
            }
        }
    }
}

@Preview
@Composable
private fun CButtonPreview() {
    CThemeProvider(
        darkTheme = false
    ) {
        CButton(
            modifier = Modifier.width(200.dp),
            text = "Enviar",
            iconId = R.drawable.ic_rocket,
            onTap = {}
        )
    }
}