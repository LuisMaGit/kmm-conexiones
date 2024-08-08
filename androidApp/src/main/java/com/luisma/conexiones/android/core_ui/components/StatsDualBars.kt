package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider

@Composable
fun StatsDualBars(
    winPercentage: Double,
    lostPercentage: Double
) {
    val wp = winPercentage / 100
    val lp = lostPercentage / 100

    Row {
        if (wp != 0.0)
            StatsBar(
                modifier = Modifier.weight(wp.toFloat()),
                color = CColor.green
            )
        if (lp != 0.0)
            StatsBar(
                modifier = Modifier.weight(lp.toFloat()),
                color = CColor.red
            )
    }
}

@Preview
@Composable
private fun StatsDualBarsPreview() {
    CThemeProvider {
        StatsDualBars(
            winPercentage = 100.0,
            lostPercentage = 0.0
        )
    }
}