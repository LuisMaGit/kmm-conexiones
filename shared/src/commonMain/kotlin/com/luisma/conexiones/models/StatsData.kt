package com.luisma.conexiones.models

data class StatsData(
    val winedBar: StatsBarModel,
    val lostBar: StatsBarModel,
    val playedBar: StatsBarModel,
    val distributionBars: List<StatsBarModel>,
    val maxDistributionIdx: Int,
) {
    companion object {
        fun initial(): StatsData {
            return StatsData(
                winedBar = StatsBarModel.initial(),
                lostBar = StatsBarModel.initial(),
                playedBar = StatsBarModel.initial(),
                distributionBars = emptyList(),
                maxDistributionIdx = 0
            )
        }
    }
}

data class StatsBarModel(
    val value: Int,
    val total: Int,
    val percentage: Int,

    ) {
    companion object {
        fun initial(): StatsBarModel {
            return StatsBarModel(
                value = 0,
                total = 0,
                percentage = 0,
            )
        }
    }
}
