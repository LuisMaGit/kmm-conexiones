package com.luisma.conexiones.android.features.game

import com.luisma.conexiones.contracts.GAME_WORD_AMOUNT
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.models.game.GameData
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.models.game.GameGridRowState
import com.luisma.conexiones.models.game.GameState


data class GameViewState(
    val screenState: BasicScreenState,
    val gameData: GameData,
    val gameId: Int,
    val gridRowsState: Map<Int, GameGridRowState>,
    val currentSelection: List<GameDistributionCoordinates>,
    val lives: Int,
    val selectFailed: Boolean,
    val livesEarnedOnDone: Int,
    val nextGameId: Int,
) {
    companion object {
        fun initial(): GameViewState {
            return GameViewState(
                screenState = BasicScreenState.Loading,
                gameData = GameData.initial(),
                gridRowsState = emptyMap(),
                currentSelection = emptyList(),
                gameId = -1,
                livesEarnedOnDone = 0,
                nextGameId = -1,
                lives = 0,
                selectFailed = false,
            )
        }
    }

    val canSelectWords: Boolean
        get() = gameData.gameState == GameState.Playing
                && currentSelection.count() != GAME_WORD_AMOUNT


    val canDeselectWords: Boolean
        get() = gameData.gameState == GameState.Playing
                && currentSelection.isNotEmpty()


    val canSendRow: Boolean
        get() = gameData.gameState == GameState.Playing
                && !selectFailed
                && currentSelection.count() == GAME_WORD_AMOUNT

}