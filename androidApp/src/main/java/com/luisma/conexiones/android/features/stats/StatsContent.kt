package com.luisma.conexiones.android.features.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.components.StatsBigNumber
import com.luisma.conexiones.android.core_ui.components.StatsDistributionBars
import com.luisma.conexiones.android.core_ui.components.StatsDualBars
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.cSpace24
import com.luisma.conexiones.android.core_ui.theme.cSpace8

@Composable
fun StatsContent(
    state: StatsState,
) {
    if (state.statsData.playedBar.value == 0) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CText(
                text = stringResource(id = R.string.stats_empty)
            )
        }
    } else {
        Column {
            // bars
            StatsDualBars(
                winPercentage = state.statsData.winedBar.percentage.toDouble(),
                lostPercentage = state.statsData.lostBar.percentage.toDouble()
            )
            // big number stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = cSpace8,
                        bottom = cSpace24
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // wined
                StatsBigNumber(
                    number = state.statsData.winedBar.value,
                    description = stringResource(id = R.string.stats_win),
                    percentage = state.statsData.winedBar.percentage,
                    bigNumberColor = CColor.green
                )
                // lost
                StatsBigNumber(
                    number = state.statsData.lostBar.value,
                    description = stringResource(id = R.string.stats_lost),
                    percentage = state.statsData.lostBar.percentage,
                    bigNumberColor = CColor.red
                )
                // played
                StatsBigNumber(
                    number = state.statsData.playedBar.value,
                    description = stringResource(
                        id = R.string.stats_played,
                        state.statsData.playedBar.total
                    ),
                    percentage = state.statsData.playedBar.percentage
                )
            }
            // win distribution
            CText(
                modifier = Modifier.padding(
                    top = cSpace24,
                    bottom = cSpace24
                ),
                text = stringResource(id = R.string.stats_graph_title)
            )
            // bars distribution
            StatsDistributionBars(
                percentage = state.statsData.distributionBars.map { it.percentage },
                values = state.statsData.distributionBars.map { it.value },
                maxIdx = state.statsData.maxDistributionIdx
            )
        }
    }


}





