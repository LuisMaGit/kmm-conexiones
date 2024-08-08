package com.luisma.conexiones.android.features.stats

sealed class StatsEvents {
    data object InitStats : StatsEvents()
    data object DisposeStats : StatsEvents()
}