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
import com.luisma.conexiones.android.core_ui.theme.cFontSize16
import com.luisma.conexiones.android.core_ui.theme.cSpace4

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
            .padding(bottom = cSpace4),
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .width(20.dp),
            contentAlignment = Alignment.Center
        ) {
            CText(
                text = "$errors",
                fontSize = cFontSize16.fontSizeNonScaledSp
            )
        }
        CText(
            modifier = Modifier.width(width = 60.dp),
            text = if (errors == 1) {
                stringResource(id = R.string.kh_error)
            } else {
                stringResource(id = R.string.kh_errors)
            },
            fontWeight = FontWeight.Normal,
            fontSize = cFontSize16.fontSizeNonScaledSp
        )
        CText(
            modifier = Modifier.width(width = 20.dp),
            text = stringResource(id = R.string.reusable_equal),
            fontSize = cFontSize16.fontSizeNonScaledSp
        )
        Row(
            modifier = Modifier.width(100.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            for (heart in 0..<hearts) {
                CIcon(
                    modifier = Modifier.padding(end = cSpace4),
                    id = R.drawable.ic_heart_solid,
                    tint = CColor.green,
                    size = 12.dp
                )
            }
        }
    }
}
