package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.CColor

@Composable
fun LevelFloatingButton(
    modifier: Modifier = Modifier,
    onTap: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(LevelFloatingButtonContracts.HEIGHT)
            .background(color = CColor.orange)
            .clickable { onTap() },
        contentAlignment = Alignment.Center
    ) {
        CText(
            text = stringResource(id = R.string.levels_floating_button),
            color = CColor.white
        )
    }
}


object LevelFloatingButtonContracts {
    val HEIGHT = 50.dp
}