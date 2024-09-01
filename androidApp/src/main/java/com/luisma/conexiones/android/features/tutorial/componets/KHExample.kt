package com.luisma.conexiones.android.features.tutorial.componets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.cFontSize16sp
import com.luisma.conexiones.android.core_ui.theme.ceraFontFamily

@Composable
fun KHExample() {
    val boldStyle = SpanStyle(
        color = CTheme.colors.textDefault,
        fontFamily = ceraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = cFontSize16sp,
    )
    val defStyle = SpanStyle(
        color = CTheme.colors.textDefault,
        fontFamily = ceraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = cFontSize16sp,
    )
    Text(
        text = buildAnnotatedString {
            pushStyle(defStyle)
            append(stringResource(id = R.string.kh_ex_text_1))
            append(" ")
            pop()

            pushStyle(boldStyle)
            append(stringResource(id = R.string.kh_ex_text_1_substring_1))
            pop()

            pushStyle(defStyle)
            append(", ")
            pop()


            pushStyle(boldStyle)
            append(stringResource(id = R.string.kh_ex_text_1_substring_2))
            pop()

            pushStyle(defStyle)
            append(" o ")
            pop()


            pushStyle(boldStyle)
            append(stringResource(id = R.string.kh_ex_text_1_substring_3))
            pop()

            pushStyle(defStyle)
            append(".")

            toAnnotatedString()
        }
    )
}
