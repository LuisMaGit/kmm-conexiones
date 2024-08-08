package com.luisma.conexiones.android.features.tutorial.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CIcon
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.cFontDefaultSize
import com.luisma.conexiones.android.core_ui.theme.ceraFontFamily

@Composable
fun KHBullets() {
    val khHeartIconId = "khHeartIconId"
    val defStyle = SpanStyle(
        color = CTheme.colors.textDefault,
        fontFamily = ceraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = cFontDefaultSize
    )
    KHBulletPoint(
        modifier = Modifier.padding(bottom = KHBulletsContracts.PADDING_BOTTOM_BULLETS)
    ) {

        val boldStyle = SpanStyle(
            color = CTheme.colors.textDefault,
            fontFamily = ceraFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = cFontDefaultSize
        )
        Text(
            text = buildAnnotatedString {
                pushStyle(defStyle)
                append(stringResource(id = R.string.kh_bullet_1))
                append(" ")
                pop()
                pushStyle(boldStyle)
                append(stringResource(id = R.string.kh_bullet_1_substring_1))
                pop()
                pushStyle(defStyle)
                append(" ")
                append(stringResource(id = R.string.kh_bullet_1_substring_2))
                toAnnotatedString()
            }
        )
    }
    KHBulletPoint(
        modifier = Modifier.padding(bottom = KHBulletsContracts.PADDING_BOTTOM_BULLETS)
    ) {
        CText(
            text = stringResource(id = R.string.kh_bullet_2),
            fontWeight = FontWeight.Normal
        )
    }
    KHBulletPoint(
        modifier = Modifier.padding(bottom = KHBulletsContracts.PADDING_BOTTOM_BULLETS)
    ) {
        BasicText(
            text = buildAnnotatedString {
                pushStyle(defStyle)
                append(stringResource(id = R.string.kh_bullet_3))
                append(" ")
                appendInlineContent(khHeartIconId)
                append(" .")
                toAnnotatedString()
            },
            inlineContent = mapOf(
                Pair(
                    khHeartIconId,
                    InlineTextContent(
                        placeholder = Placeholder(
                            width = KHBulletsContracts.SIZE_GREEN_HEART.sp,
                            height = KHBulletsContracts.SIZE_GREEN_HEART.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                        ),
                    ) {
                        CIcon(
                            id = R.drawable.ic_heart_solid,
                            tint = CColor.green,
                            size = KHBulletsContracts.SIZE_GREEN_HEART.dp
                        )
                    }
                )
            )
        )
    }
    KHBulletPoint(
        modifier = Modifier.padding(bottom = KHBulletsContracts.PADDING_BOTTOM_BULLETS)
    ) {
        CText(
            text = stringResource(id = R.string.kh_bullet_4),
            fontWeight = FontWeight.Normal
        )
    }
}

object KHBulletsContracts {
    val PADDING_BOTTOM_BULLETS = 8.dp
    const val SIZE_GREEN_HEART = 16.0
}

