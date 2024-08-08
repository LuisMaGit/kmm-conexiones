package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider

@Composable
fun StatsDistributionBars(
    percentage: List<Int>,
    values: List<Int>,
    maxIdx: Int,
) {

    if (values.count() != percentage.count()
        && values.isEmpty()
        && values.all { it == 0 }
    ) {
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth(StatsDistributionContracts.FRACTION_SCREEN)
    ) {
        for (idx in 0..<values.count()) {
            val perc = percentage[idx]
            val value = values[idx]
            val graphColor = if (idx == maxIdx) CColor.green else CTheme.colors.textDefault
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = StatsDistributionContracts.PADDING_BETWEEN_BARS),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //num
                Box(
                    modifier = Modifier
                        .width(width = StatsDistributionContracts.WIDTH_FIRST_NUMB),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CText(
                        text = "${idx + 1}",
                        fontSize = StatsDistributionContracts.NUMB_FONT_SIZE.fontSizeNonScaledSp
                    )
                }
                // graph
                if (perc != 0)
                    StatsBar(
                        modifier = Modifier
                            .fillMaxWidth(
                                perc.toFloat() / StatsDistributionContracts.MAX_FRACTION_BAR
                            ),
                        color = graphColor,
                    )

                // tries
                if (value != 0)
                    Box(
                        modifier = Modifier
                            .padding(start = StatsDistributionContracts.PADDING_LAST_NUMB)
                    ) {
                        CText(
                            text = value.toString(),
                            fontSize = StatsDistributionContracts.NUMB_FONT_SIZE.fontSizeNonScaledSp,
                            color = graphColor
                        )
                    }

            }
        }
    }
}

object StatsDistributionContracts {
    val WIDTH_FIRST_NUMB = 24.dp
    val PADDING_LAST_NUMB = 4.dp
    val PADDING_BETWEEN_BARS = 24.dp
    const val FRACTION_SCREEN = .9f
    const val MAX_FRACTION_BAR = 110
    const val NUMB_FONT_SIZE = 16
}

@Preview
@Composable
private fun StatsDistributionBarsPreview() {
    CThemeProvider {
        Box(
            modifier = Modifier.background(color = Color.White)
        ) {
            StatsDistributionBars(
                percentage = List(4) { if (it == 3) 100 else it * 10 },
                values = List(4) { it },
                maxIdx = 2
            )
        }
    }
}