package com.luisma.conexiones.android.features.game

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.luisma.conexiones.android.features.game.componets.GameLoader
import com.luisma.conexiones.android.features.game.componets.GameSuccess
import com.luisma.conexiones.models.BasicScreenState

@Composable
fun GameView(
    gameId: Int,
    state: GameViewState,
    sendEvent: (event: GameViewEvents) -> Unit
) {

    LaunchedEffect(key1 = gameId) {
        sendEvent(GameViewEvents.OnCreate(gameId = gameId))
    }

    when (state.screenState) {
        BasicScreenState.Success -> GameSuccess(
            state = state,
            sendEvent = sendEvent
        )

        BasicScreenState.Loading -> GameLoader(
            lives = state.lives,
            onBack = { sendEvent(GameViewEvents.OnBack) }
        )

        else -> Box {}
    }

}



