package com.luisma.conexiones.android.features.tutorial.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText

@Composable
fun KHBulletPoint(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        CText(
            text = stringResource(id = R.string.reusable_bullet),
        )
        CText(text = "  ")
        content()
    }
}