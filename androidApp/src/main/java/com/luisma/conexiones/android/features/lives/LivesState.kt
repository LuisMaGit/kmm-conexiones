package com.luisma.conexiones.android.features.lives

import com.luisma.conexiones.models.BasicScreenState


data class LivesState(
    val lives: Int,
    val initialized: Boolean,
    val screenState: BasicScreenState,
    val enabledLivesButton: Boolean,
) {

    val hasLife: Boolean
        get() = lives > 0

    companion object {
        fun initial(): LivesState {
            return LivesState(
                lives = 0,
                initialized = false,
                screenState = BasicScreenState.Loading,
                enabledLivesButton = false,
            )
        }
    }
}