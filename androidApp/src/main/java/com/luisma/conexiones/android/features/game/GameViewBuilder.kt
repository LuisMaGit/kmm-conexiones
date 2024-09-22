package com.luisma.conexiones.android.features.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameViewBuilder(gameId: Int) {
    val gameViewModel = viewModel<GameViewModel>(factory = GameViewModel.Factory)
    val gameState by gameViewModel.state.collectAsState()
    GameView(
        gameId = gameId,
        state = gameState,
        sendEvent = { event -> gameViewModel.sendEvent(event = event) }
    )
}