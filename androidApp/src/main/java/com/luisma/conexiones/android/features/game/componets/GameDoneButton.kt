package com.luisma.conexiones.android.features.game.componets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CButton

@Composable
fun GameDoneButton(
    modifier: Modifier = Modifier,
    nextLevelId: Int,
    onTapNextLevel: () -> Unit
) {
    CButton(
        modifier = modifier,
        text = "${stringResource(id = R.string.reusable_level)} $nextLevelId",
        iconId = R.drawable.ic_arrow_right_solid
    ) {
        onTapNextLevel()
    }
}

