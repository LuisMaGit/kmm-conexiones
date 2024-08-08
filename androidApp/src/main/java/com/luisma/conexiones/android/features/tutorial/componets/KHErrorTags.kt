package com.luisma.conexiones.android.features.tutorial.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CIcon
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CColor

@Composable
fun KHErrorTags() {
    fun getHearts(tag: Int): Int {
        return when (tag) {
            0 -> 3
            1 -> 2
            2, 3 -> 1
            else -> 0
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (tag in 0..<4) {
            KHErrorTag(
                errors = tag,
                hearts = getHearts(tag)
            )
        }
    }
}

@Composable
fun KHErrorTag(errors: Int, hearts: Int) {
    Row(
        modifier = Modifier
            .padding(bottom = KHErrorTagContracts.PADDING_BOTTOM_ERRORS),
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .width(KHErrorTagContracts.SIZE_BOX_ERROR_AMOUNT),
            contentAlignment = Alignment.Center
        ) {
            CText(
                text = "$errors"
            )
        }
        CText(
            modifier = Modifier.width(width = KHErrorTagContracts.SIZE_BOX_ERROR_TEXT),
            text = if (errors == 1) {
                stringResource(id = R.string.kh_error)
            } else {
                stringResource(id = R.string.kh_errors)
            },
            fontWeight = FontWeight.Normal,
            fontSize = KHErrorTagContracts.FONT_SIZE_TEXT.fontSizeNonScaledSp
        )
        CText(
            modifier = Modifier.width(width = KHErrorTagContracts.SIZE_BOX_EQUAL),
            text = stringResource(id = R.string.reusable_equal),
            fontSize = KHErrorTagContracts.FONT_SIZE_TEXT.fontSizeNonScaledSp
        )
        Row(
            modifier = Modifier.width(KHErrorTagContracts.SIZE_BOX_HEARTS_ICONS),
            verticalAlignment = Alignment.Bottom
        ) {
            for (heart in 0..<hearts) {
                CIcon(
                    modifier = Modifier.padding(end = KHErrorTagContracts.PADDING_END_HEARTS_ICON),
                    id = R.drawable.ic_heart_solid,
                    tint = CColor.green,
                    size = KHErrorTagContracts.SIZE_HEARTS_ICON
                )
            }
        }
    }
}

object KHErrorTagContracts {
    val PADDING_BOTTOM_ERRORS = 4.dp
    val PADDING_END_HEARTS_ICON = 4.dp
    val SIZE_HEARTS_ICON = 12.dp
    val SIZE_BOX_HEARTS_ICONS = 100.dp
    val SIZE_BOX_EQUAL = 20.dp
    const val FONT_SIZE_TEXT = 16
    val SIZE_BOX_ERROR_AMOUNT = 20.dp
    val SIZE_BOX_ERROR_TEXT = 60.dp
}