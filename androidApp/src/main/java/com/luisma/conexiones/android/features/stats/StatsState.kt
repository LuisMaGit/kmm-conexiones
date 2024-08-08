package com.luisma.conexiones.android.features.stats

import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.models.StatsData

data class StatsState(
    val initialized: Boolean,
    val statsData: StatsData,
    val screenState: BasicScreenState
) {
    companion object {
        fun initial(): StatsState {
            return StatsState(
                initialized = false,
                statsData = StatsData.initial(),
                screenState = BasicScreenState.Loading
            )
        }
    }
}