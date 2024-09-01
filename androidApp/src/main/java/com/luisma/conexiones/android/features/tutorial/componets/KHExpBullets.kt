package com.luisma.conexiones.android.features.tutorial.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.cFontSize16sp
import com.luisma.conexiones.android.core_ui.theme.cSpace4
import com.luisma.conexiones.android.core_ui.theme.cSpace8

@Composable
fun KHExpBullets() {
    KHBulletExp(
        title = stringResource(id = R.string.kh_ex_bullet_1),
        body = stringResource(id = R.string.kh_ex_words_1)
    )
    KHBulletExp(
        title = stringResource(id = R.string.kh_ex_bullet_2),
        body = stringResource(id = R.string.kh_ex_words_2)
    )
}

@Composable
fun KHBulletExp(
    title: String,
    body: String
) {
    KHBulletPoint(
        modifier = Modifier.padding(bottom = cSpace4)
    ) {
        Row {
            CText(
                modifier = Modifier.padding(end = cSpace8),
                text = title,
                fontSize = cFontSize16sp
            )
            CText(
                text = body,
                fontSize = cFontSize16sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
