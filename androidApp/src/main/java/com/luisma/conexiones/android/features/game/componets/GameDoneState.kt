package com.luisma.conexiones.android.features.game.componets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.cFontSize36sp

@Composable
fun GameDoneState(
    modifier: Modifier = Modifier,
    win: Boolean
) {
    CText(
        modifier = modifier,
        text = if (win) {
            stringResource(id = R.string.level_win)
        } else {
            stringResource(id = R.string.level_lost)
        },
        fontSize = cFontSize36sp,
    )
}