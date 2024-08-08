package com.luisma.conexiones.android.features.tutorial.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText

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
        modifier = Modifier.padding(bottom = KHExpContracts.PADDING_BOTTOM_BULLET)
    ) {
        Row {
            CText(
                modifier = Modifier.padding(end = KHExpContracts.PADDING_END_TITLE),
                text = title,
                fontSize = KHExpContracts.FONT_SIZE_BULLET
            )
            CText(
                text = body,
                fontSize = KHExpContracts.FONT_SIZE_BULLET,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

object KHExpContracts {
    val PADDING_BOTTOM_BULLET = 4.dp
    val PADDING_END_TITLE = 8.dp
    val FONT_SIZE_BULLET = 16.sp
}