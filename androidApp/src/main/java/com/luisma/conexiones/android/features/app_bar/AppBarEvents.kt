package com.luisma.conexiones.android.features.app_bar

sealed class AppBarEvents {
    data object ToggleStats : AppBarEvents()
    data object ToggleLives : AppBarEvents()
    data object ToggleTutorial : AppBarEvents()
    data class SetLives(val lives: Int?) : AppBarEvents()
}