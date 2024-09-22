package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CButton
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.cFontSize14sp
import com.luisma.conexiones.android.core_ui.theme.cSpace16


@Composable
fun GameDoneMoreLives(
    modifier: Modifier = Modifier,
    onTapMoreLives: () -> Unit
) {

    Column(
        modifier = modifier,
    ) {
        CText(
            modifier = Modifier.padding(bottom = cSpace16),
            text = stringResource(id = R.string.level_lost_no_lives),
            fontSize = cFontSize14sp
        )
        CButton(
            text = "+1",
            iconId = R.drawable.ic_heart_solid,
            color = CColor.orange
        ) {
            onTapMoreLives()
        }
    }
}