package com.luisma.conexiones.android.features.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luisma.conexiones.android.router.RouterService
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.services.IUserProfileService
import com.luisma.conexiones.services.game.GamePlayService
import com.luisma.conexiones.services.game.GameSelectionService
import com.luisma.conexiones.services.gamePlayService
import com.luisma.conexiones.services.gameSelectionService
import com.luisma.conexiones.services.userProfileService
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val gamePlayService: GamePlayService,
    private val gameSelectionService: GameSelectionService,
    private val userProfileService: IUserProfileService,
    private val routerService: RouterService
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(GameViewModel::class) {
                GameViewModel(
                    gamePlayService = gamePlayService(),
                    userProfileService = userProfileService(),
                    routerService = RouterService,
                    gameSelectionService = gameSelectionService()
                )
            }
        }
    }

    private val _state = MutableStateFlow(GameViewState.initial())
    val state = _state.asStateFlow()

    fun sendEvent(event: GameViewEvents) {
        when (event) {
            is GameViewEvents.OnCreate -> onCrate(gameId = event.gameId)
            is GameViewEvents.SelectWord -> selectWord(col = event.col, row = event.row)
            is GameViewEvents.GoToNextLevel -> goToNextLevel(nextLevelId = event.nextLevelId)
            GameViewEvents.MixGame -> mixGame()
            GameViewEvents.ClearSelection -> clearSelection()
            GameViewEvents.OnBack -> onBack()
            GameViewEvents.Submit -> submit()
            GameViewEvents.MoreLives -> {}// TODO:
        }
    }

    private fun onCrate(gameId: Int) {
        if (gameId == _state.value.gameId || gameId == -1) {
            return
        }
        startGame(gameId = gameId)
    }

    private fun startGame(gameId: Int) {
        viewModelScope.launch {
            val gameDef = async { gamePlayService.getGame(gameId = gameId) }
            val livesDef = async { userProfileService.getLives() }
            val game = gameDef.await()
            val lives = livesDef.await()
            _state.update {
                it.copy(
                    gameId = gameId,
                    screenState = BasicScreenState.Success,
                    gameData = game.gameData,
                    gridRowsState = game.gridRowsState,
                    lives = lives,
                    livesEarnedOnDone = game.livesEarnedOnDone,
                    nextGameId = game.nextGameId
                )
            }
        }
    }

    private fun mixGame() {
        viewModelScope.launch {
            val gameResponse = gamePlayService.mixGame(
                gameId = _state.value.gameId,
                gameDistribution = _state.value.gameData.gameDistribution,
                currentSelection = _state.value.currentSelection,
            )
            _state.update {
                it.copy(
                    gameData = gameResponse.gameData,
                    gridRowsState = gameResponse.gridRowsState,
                    currentSelection = gameResponse.selection
                )
            }
        }
    }

    private fun onBack() {
        viewModelScope.launch {
            routerService.goBack()
        }
    }

    private fun selectWord(col: Int, row: Int) {
        val result = gameSelectionService.select(
            gridRowState = _state.value.gridRowsState,
            currentSelection = _state.value.currentSelection,
            newCoordinates = GameDistributionCoordinates(
                column = col,
                row = row,
            )
        )

        _state.update {
            it.copy(
                gridRowsState = result.gridRowState,
                currentSelection = result.newSelection,
                selectFailed = false
            )
        }
    }

    private fun clearSelection() {
        val result = gameSelectionService.clearSelection(
            gridRowState = _state.value.gridRowsState,
            currentSelection = _state.value.currentSelection,
        )

        if (result == null) {
            return
        }

        _state.update {
            it.copy(
                gridRowsState = result.gridRowState,
                currentSelection = result.newSelection,
            )
        }
    }

    private fun submit() {
        viewModelScope.launch {
            val response = gamePlayService.submitSelection(
                gameId = _state.value.gameId,
                gameDistribution = _state.value.gameData.gameDistribution,
                currentSelection = _state.value.currentSelection
            )

            var livesEarnedOnDone = _state.value.livesEarnedOnDone
            var lives = _state.value.lives

            if (response.gamePlaying.gameData.gameState == GameState.Win ||
                response.gamePlaying.gameData.gameState == GameState.Lost
            ) {
                livesEarnedOnDone = response.gamePlaying.livesEarnedOnDone
                lives = response.totalLivesAfterOnDone
            }

            _state.update {
                it.copy(
                    gameData = response.gamePlaying.gameData,
                    gridRowsState = response.gamePlaying.gridRowsState,
                    currentSelection = response.gamePlaying.selection,
                    selectFailed = !response.rowGuessedOnSubmit,
                    livesEarnedOnDone = livesEarnedOnDone,
                    lives = lives,
                    nextGameId = response.gamePlaying.nextGameId
                )
            }
        }
    }

    private fun goToNextLevel(nextLevelId: Int) {
        _state.update {
            GameViewState.initial()
        }

        startGame(gameId = nextLevelId)
    }
}