package com.luisma.conexiones.android.features.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luisma.conexiones.android.router.RoutePayload
import com.luisma.conexiones.android.router.RouterService
import com.luisma.conexiones.android.router.Routes
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.models.game.GameTileAnimationType
import com.luisma.conexiones.models.game.GameWordAnimationType
import com.luisma.conexiones.services.INumbUtilsService
import com.luisma.conexiones.services.IUserProfileService
import com.luisma.conexiones.services.game.GamePlayService
import com.luisma.conexiones.services.game.GameSelectionService
import com.luisma.conexiones.services.game.IGameAnimationService
import com.luisma.conexiones.services.gameAnimationService
import com.luisma.conexiones.services.gamePlayService
import com.luisma.conexiones.services.gameSelectionService
import com.luisma.conexiones.services.numbUtilsService
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
    private val routerService: RouterService,
    private val numbUtilsService: INumbUtilsService,
    private val gameAnimationService: IGameAnimationService
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(GameViewModel::class) {
                GameViewModel(
                    gamePlayService = gamePlayService(),
                    userProfileService = userProfileService(),
                    routerService = RouterService,
                    gameSelectionService = gameSelectionService(),
                    numbUtilsService = numbUtilsService(),
                    gameAnimationService = gameAnimationService()
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
            is GameViewEvents.DismissWordAnimation -> dismissWordAnimation(
                col = event.col,
                row = event.row
            )
            is GameViewEvents.DismissTileAnimation -> dismissTileAnimation(row = event.row)
            is GameViewEvents.DismissLivesAnimation -> dismissLivesAnimation()
            is GameViewEvents.DismissDoneSignAnimation -> dismissDoneSignAnimation()
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
                    nextGameId = game.nextGameId,
                    showOnDoneSignAnimation = game.showOnDoneSignAnimation,
                    showOnDoneLivesAnimation = game.showOnDoneLivesAnimation
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
            routerService.setRoute(
                RoutePayload(
                    route = Routes.BackWithRefresh,
                    payload = numbUtilsService.getRandomInt().toString()
                )
            )
        }
    }

    private fun selectWord(col: Int, row: Int) {
        val result = gameSelectionService.select(
            gridRowState = _state.value.gridRowsState,
            currentSelection = _state.value.currentSelection,
            newColumn = col,
            newRow = row
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

            if (response.gameData.gameState == GameState.Win ||
                response.gameData.gameState == GameState.Lost
            ) {
                livesEarnedOnDone = response.livesEarnedOnDone
                lives = response.totalLivesAfterOnDone
            }

            _state.update {
                it.copy(
                    gameData = response.gameData,
                    gridRowsState = response.gridRowsState,
                    currentSelection = response.selection,
                    selectFailed = !response.rowGuessedOnSubmit,
                    livesEarnedOnDone = livesEarnedOnDone,
                    lives = lives,
                    nextGameId = response.nextGameId,
                    notSolvedRowsOnLost = response.notSolvedRowsOnLost,
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

    private fun dismissWordAnimation(
        col: Int,
        row: Int,
    ) {
        val resp = gameAnimationService.toggleWordAnimation(
            gridRowState = _state.value.gridRowsState,
            column = col,
            row = row,
            animationType = GameWordAnimationType.NoAnimation
        )
        _state.update {
            it.copy(
                gridRowsState = resp
            )
        }
    }

    private fun dismissTileAnimation(
        row: Int,
    ) {
        val resp = gameAnimationService.toggleTilesAnimation(
            gridRowState = _state.value.gridRowsState,
            rows = listOf(row),
            animationType = GameTileAnimationType.NoAnimation
        )
        _state.update {
            it.copy(
                gridRowsState = resp
            )
        }
    }


    private fun dismissLivesAnimation() {
        if(!_state.value.showOnDoneLivesAnimation) return

        _state.update {
            it.copy(showOnDoneLivesAnimation = false)
        }
    }
    private fun dismissDoneSignAnimation() {
        if(!_state.value.showOnDoneSignAnimation) return

        _state.update {
            it.copy(showOnDoneSignAnimation = false)
        }
    }

}