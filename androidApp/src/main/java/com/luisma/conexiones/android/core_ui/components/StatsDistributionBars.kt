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
import com.luisma.conexiones.android.core_ui.theme.cFontSize16
import com.luisma.conexiones.android.core_ui.theme.cScreenFraction
import com.luisma.conexiones.android.core_ui.theme.cSpace24
import com.luisma.conexiones.android.core_ui.theme.cSpace4

@Composable
fun StatsDistributionBars(
    percentage: List<Int>,
    values: List<Int>,
    maxIdx: Int,
) {

    if (values.count() != percentage.count()
        || values.isEmpty()
        || values.all { it == 0 }
    ) {
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth(cScreenFraction)
    ) {
        for (idx in 0..<values.count()) {
            val perc = percentage[idx]
            val value = values[idx]
            val graphColor = if (idx == maxIdx) CColor.green else CTheme.colors.textDefault
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = cSpace24),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //num
                Box(
                    modifier = Modifier
                        .width(width = 24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CText(
                        text = "${idx + 1}",
                        fontSize = cFontSize16.fontSizeNonScaledSp
                    )
                }
                // graph
                if (perc != 0)
                    StatsBar(
                        modifier = Modifier
                            .fillMaxWidth(
                                perc.toFloat() / 110
                            ),
                        color = graphColor,
                    )

                // tries
                if (value != 0)
                    Box(
                        modifier = Modifier
                            .padding(start = cSpace4)
                    ) {
                        CText(
                            text = value.toString(),
                            fontSize = cFontSize16.fontSizeNonScaledSp,
                            color = graphColor
                        )
                    }

            }
        }
    }
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