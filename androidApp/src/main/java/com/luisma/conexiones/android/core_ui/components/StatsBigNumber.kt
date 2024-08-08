package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CTheme

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
            modifier = Modifier.padding(bottom = StatsBigNumber.SPACING_TEXT),
            text = number.toString(),
            fontSize = StatsBigNumber.BIG_NUM_FONT_SIZE.fontSizeNonScaledSp,
            color = bigNumberColor
        )
        CText(
            text = description,
            fontSize = StatsBigNumber.DESCRIPTION_FONT_SIZE
        )
        CText(
            text = "[$percentage%]",
            fontSize = StatsBigNumber.DESCRIPTION_FONT_SIZE
        )
    }

}

object StatsBigNumber {
    const val BIG_NUM_FONT_SIZE = 32
    val DESCRIPTION_FONT_SIZE = 16.sp
    val SPACING_TEXT = 4.dp
}