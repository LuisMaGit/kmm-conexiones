package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.cFontSize36sp
import com.luisma.conexiones.android.core_ui.theme.cSpace8


@Composable
fun GameTitle(
    modifier: Modifier = Modifier,
    level: Int,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        CText(
            modifier = Modifier.padding(vertical = cSpace8),
            text = "${stringResource(id = R.string.reusable_level)} $level",
            fontSize = cFontSize36sp,
        )
    }
}

