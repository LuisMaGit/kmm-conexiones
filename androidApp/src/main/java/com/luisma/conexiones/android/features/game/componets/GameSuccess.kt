package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.components.CTryChip
import com.luisma.conexiones.android.core_ui.components.ScreenSizeBreakPoints
import com.luisma.conexiones.android.core_ui.components.ScreenSizeHelper
import com.luisma.conexiones.android.core_ui.components.game_cards.GameCardsContracts
import com.luisma.conexiones.android.core_ui.theme.cFontSize16sp
import com.luisma.conexiones.android.core_ui.theme.cFontSize36sp
import com.luisma.conexiones.android.core_ui.theme.cSpace16
import com.luisma.conexiones.android.core_ui.theme.cSpace24
import com.luisma.conexiones.android.core_ui.theme.cSpace4
import com.luisma.conexiones.android.core_ui.theme.cSpace40
import com.luisma.conexiones.android.core_ui.theme.cSpace8
import com.luisma.conexiones.android.features.game.GameViewEvents
import com.luisma.conexiones.android.features.game.GameViewState
import com.luisma.conexiones.contracts.GAME_WORD_AMOUNT
import com.luisma.conexiones.models.game.GameState

@Composable
fun GameSuccess(
    state: GameViewState,
    sendEvent: (event: GameViewEvents) -> Unit
) {
    val scrollState = rememberScrollState()
    ScreenSizeHelper { breakPoint, _ ->
        val wordCardSize = when (breakPoint) {
            ScreenSizeBreakPoints.Mobile -> GameCardsContracts.Mobile
            ScreenSizeBreakPoints.Tablet -> GameCardsContracts.Tablet
        }
        val gridWidth = wordCardSize.wordCardWidth * GAME_WORD_AMOUNT +
                (wordCardSize.cardSpacing * GAME_WORD_AMOUNT)

        val gridHeight = wordCardSize.cardHeight * GAME_WORD_AMOUNT +
                (wordCardSize.cardSpacing * GAME_WORD_AMOUNT) +
                cSpace24

        val winOrLost =
            state.gameData.gameState == GameState.Win || state.gameData.gameState == GameState.Lost

        GameWrapper(
            lives = state.lives,
            onBack = { sendEvent(GameViewEvents.OnBack) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GameTitle(
                    modifier = Modifier.width(gridWidth),
                    level = state.gameId
                )
                GameGrid(
                    modifier = Modifier
                        .padding(top = cSpace24)
                        .width(gridWidth)
                        .height(gridHeight),
                    cardType = wordCardSize,
                    gridRowsState = state.gridRowsState,
                    canSelectCards = state.canSelectWords,
                    onTapCard = { column, row ->
                        sendEvent(
                            GameViewEvents.SelectWord(
                                col = column,
                                row = row
                            )
                        )
                    },
                )
                if (state.gameData.gameState == GameState.Playing) {
                    GameErrors(
                        modifier = Modifier.padding(bottom = cSpace24),
                        remainingErrors = state.gameData.remainingErrors
                    )
                    GameControls(
                        modifier = Modifier.width(width = gridWidth),
                        sendEvent = sendEvent,
                        canDeselectWords = state.canDeselectWords,
                        canSendRow = state.canSendRow
                    )
                } else if (winOrLost) {
                    GameDoneState(
                        modifier = Modifier.padding(top = cSpace24),
                        win = state.gameData.gameState == GameState.Win
                    )
                }
                if (state.gameData.gameState == GameState.Win) {
                    GameDoneLives(
                        modifier = Modifier.padding(top = cSpace16),
                        lives = state.livesEarnedOnDone
                    )
                }
                if (winOrLost && state.nextGameId != -1) {
                    if (state.lives == 0) {
                        GameDoneMoreLives(
                            modifier = Modifier
                                .width(width = gridWidth)
                                .padding(top = cSpace40),
                            onTapMoreLives = { sendEvent(GameViewEvents.MoreLives) }
                        )
                    } else {
                        GameDoneButton(
                            modifier = Modifier
                                .width(width = gridWidth)
                                .padding(top = cSpace40),
                            onTapNextLevel = { sendEvent(GameViewEvents.GoToNextLevel(state.nextGameId)) },
                            nextLevelId = state.nextGameId
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GameErrors(
    modifier: Modifier = Modifier,
    remainingErrors: Int
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        CText(
            modifier = Modifier.padding(end = cSpace8),
            text = stringResource(id = R.string.level_errors),
            fontSize = cFontSize16sp
        )
        for (error in 0..<remainingErrors) {
            CTryChip(
                modifier = Modifier.padding(end = cSpace4)
            )
        }
    }

}
