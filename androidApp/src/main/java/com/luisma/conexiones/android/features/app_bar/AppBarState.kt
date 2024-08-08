package com.luisma.conexiones.android.features.app_bar


data class AppBarState(
    val showStats: Boolean,
    val showLives: Boolean,
    val showTutorial: Boolean,
    val lives: Int
) {

    val hasLife: Boolean
        get() = lives > 0

    companion object {
        fun initial(): AppBarState {
            return AppBarState(
                showLives = false,
                showStats = false,
                showTutorial = false,
                lives = 0
            )
        }
    }
}