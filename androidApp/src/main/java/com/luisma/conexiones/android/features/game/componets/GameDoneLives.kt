package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CIcon
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cSpace16

@Composable
fun GameDoneLives(
    modifier: Modifier = Modifier,
    lives: Int
) {
    if (lives > 0)
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            List(3) { live ->
                val filledLive = lives > live
                CIcon(
                    modifier = Modifier.padding(horizontal = cSpace16),
                    id = if (filledLive) R.drawable.ic_heart_solid else R.drawable.ic_heart,
                    tint = if (filledLive) CColor.green else CTheme.colors.textDefault,
                    size = 34.dp
                )
            }
        }
}

@Preview
@Composable
private fun GameDoneLivesPreview() {
    CThemeProvider {
        GameDoneLives(
            lives = 2
        )
    }
}