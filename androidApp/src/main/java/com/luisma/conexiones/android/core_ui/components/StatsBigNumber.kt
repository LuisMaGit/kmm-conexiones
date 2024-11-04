package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.cFontSize16
import com.luisma.conexiones.android.core_ui.theme.cFontSize32
import com.luisma.conexiones.android.core_ui.theme.cSpace4

@Composable
fun StatsBigNumber(
    modifier: Modifier = Modifier,
    bigNumberColor: Color = CTheme.colors.textDefault,
    number: Int,
    description: String,
    percentage: Int
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CText(
            modifier = Modifier.padding(bottom = cSpace4),
            text = number.toString(),
            fontSize = cFontSize32.fontSizeNonScaledSp,
            color = bigNumberColor
        )
        CText(
            text = description,
            fontSize = cFontSize16.fontSizeNonScaledSp
        )
        CText(
            text = "[$percentage%]",
            fontSize = cFontSize16.fontSizeNonScaledSp
        )
    }

}

