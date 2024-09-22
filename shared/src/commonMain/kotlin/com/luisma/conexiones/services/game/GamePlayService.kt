package com.luisma.conexiones.services.game

import com.luisma.conexiones.contracts.GAME_AMOUNT_DIFFICULTIES
import com.luisma.conexiones.contracts.GAME_AMOUNT_TRIES
import com.luisma.conexiones.models.game.GameData
import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.models.game.GameGridRowState
import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.services.IUserProfileService
import com.luisma.conexiones.services_db.IGameDBService
import com.luisma.conexiones.services_db.IUserProfileDBService

class GamePlayService(
    private val gameDBService: IGameDBService,
    private val mapperService: IGameDBMappersService,
    private val gameSortService: GameSortService,
    private val gameSelectionService: GameSelectionService,
    private val userProfileService: IUserProfileService,
    private val userProfileDBService: IUserProfileDBService
) {

    private suspend fun getGameData(gameId: Int): GameData {
        val db = gameDBService.selectGame(gameId = gameId)
        return mapperService.gameFromDBToGameData(db = db)
    }

    private suspend fun existsNextGame(gameId: Int): Int {
        val nextGameId = gameId + 1
        return if (gameDBService.selectGameId(gameId = nextGameId) != null) nextGameId else -1
    }

    suspend fun getGame(gameId: Int): GamePlayingResponse {
        var gameData = getGameData(gameId = gameId)
        // Lock -> should never be called here
        // Free -> Put as playing, shuffle all rows (and save), get data
        // Playing, Win, Lost -> just return
        if (gameData.gameState == GameState.Free) {
            gameDBService.updateGameState(
                gameId = gameId,
                state = GameState.Playing.rawValue
            )
            val gameDistribution = gameSortService.mixGameDistribution(
                gameDistribution = gameSortService.fullGameListSorted()
            )
            val gameDistributionJson = mapperService.encodeGameDistribution(
                gameDistribution = gameDistribution
            )
            gameDBService.updateGameDistribution(
                gameId = gameId,
                distribution = gameDistributionJson
            )
            gameData = getGameData(gameId = gameId)
        }

        return GamePlayingResponse(
            gameData = gameData,
            gridRowsState = gameSortService.resolveGridRowState(gameData),
            selection = emptyList(),
            livesEarnedOnDone = userProfileService.gameLivesByStateAndTries(
                gameState = gameData.gameState,
                guessedTries = gameData.guessedTries,
            ),
            nextGameId = existsNextGame(gameId)
        )
    }

    suspend fun mixGame(
        gameId: Int,
        gameDistribution: List<GameDistribution>,
        currentSelection: List<GameDistributionCoordinates>,
    ): GamePlayingResponse {

        val newDistribution = gameSortService.mixGame(
            gameDistribution = gameDistribution
        )
        val gameDistributionJson = mapperService.encodeGameDistribution(
            gameDistribution = newDistribution
        )
        gameDBService.updateGameDistribution(
            gameId = gameId,
            distribution = gameDistributionJson
        )

        val gameData = getGameData(gameId = gameId)
        val gridRowStateWithOutSelection = gameSortService.resolveGridRowState(gameData)

        val updatedSelectionAfterRotation = gameSelectionService.selectionAfterRotation(
            beforeRotationDistribution = gameDistribution,
            afterRotationDistribution = newDistribution,
            currentSelection = currentSelection
        )
        val gridWithSelection = gameSelectionService.updateSelection(
            newSelectedCoordinates = updatedSelectionAfterRotation,
            gridRowState = gridRowStateWithOutSelection
        )

        return GamePlayingResponse.initial().copy(
            gameData = gameData,
            gridRowsState = gridWithSelection,
            selection = updatedSelectionAfterRotation,
        )
    }

    suspend fun submitSelection(
        gameId: Int,
        gameDistribution: List<GameDistribution>,
        currentSelection: List<GameDistributionCoordinates>
    ): GamePlayingSubmitResponse {
        val submitResponse = gameSelectionService.submitSelection(
            currentDistribution = gameDistribution,
            currentSelection = currentSelection
        )
        val rowGuessed = submitResponse.rowGuessed
        var selectionOutput = emptyList<GameDistributionCoordinates>()
        // not guessed
        if (rowGuessed == -1) {
            val tries = gameDBService.selectGameTries(gameId)
            val newTries = tries + 1
            if (newTries >= GAME_AMOUNT_TRIES) {
                gameDBService.updateGameTries(gameId = gameId, guessedTries = GAME_AMOUNT_TRIES)
                gameDBService.updateGameState(gameId = gameId, state = GameState.Lost.rawValue)
                val distributionSortedJson = mapperService.encodeGameDistribution(
                    gameSortService.fullGameListSorted()
                )
                gameDBService.updateGameDistribution(
                    gameId = gameId,
                    distribution = distributionSortedJson
                )
            } else {
                selectionOutput = currentSelection
                gameDBService.updateGameTries(gameId = gameId, guessedTries = newTries)
            }
            // guessed
        } else {
            val guessedDifficultyStr = gameDBService.selectGuessedDifficulty(gameId)
            val guessedDifficulty = mapperService.decodeGuessedDifficulty(
                guessedDifficultyStr
            ).toMutableSet()
            guessedDifficulty.add(rowGuessed)
            val guessedDifficultySet = guessedDifficulty.sorted().toSet()
            if (guessedDifficultySet.count() == GAME_AMOUNT_DIFFICULTIES) {
                gameDBService.updateGameState(gameId = gameId, state = GameState.Win.rawValue)
            }
            val distributionSortedJson = mapperService.encodeGameDistribution(
                submitResponse.newDistribution
            )
            gameDBService.updateGameDistribution(
                gameId = gameId,
                distribution = distributionSortedJson
            )
            gameDBService.updateGuessedDifficulty(
                gameId = gameId,
                guessedDifficulty = mapperService.encodeGuessedDifficulty(guessedDifficultySet)
            )
        }

        val gameData = getGameData(gameId = gameId)
        var grid = gameSortService.resolveGridRowState(gameData)
        if (selectionOutput.isNotEmpty()) {
            grid = gameSelectionService.updateSelection(
                newSelectedCoordinates = selectionOutput,
                gridRowState = grid
            )
        }

        var livesEarnedOnDone = 0
        var totalLivesAfterOnDone = 0
        var nextGameId = -1
        if (gameData.gameState == GameState.Win || gameData.gameState == GameState.Lost) {
            livesEarnedOnDone = userProfileService.gameLivesByStateAndTries(
                gameState = gameData.gameState,
                guessedTries = gameData.guessedTries
            )

            val currentLives = userProfileDBService.selectLives()
            val updatedLives = if (currentLives > 0) currentLives + livesEarnedOnDone else 0
            totalLivesAfterOnDone = updatedLives
            userProfileDBService.updateLives(updatedLives)

            nextGameId = existsNextGame(gameId)
            if (nextGameId != -1) {
                gameDBService.updateGameState(gameId = nextGameId, state = GameState.Free.rawValue)
            }
        }

        return GamePlayingSubmitResponse(
            gamePlaying = GamePlayingResponse(
                gameData = gameData,
                gridRowsState = grid,
                selection = selectionOutput,
                livesEarnedOnDone = livesEarnedOnDone,
                nextGameId = nextGameId
            ),
            rowGuessedOnSubmit = rowGuessed != -1,
            totalLivesAfterOnDone = totalLivesAfterOnDone
        )
    }
}


data class GamePlayingResponse(
    val gameData: GameData,
    val gridRowsState: Map<Int, GameGridRowState>,
    val selection: List<GameDistributionCoordinates>,
    val livesEarnedOnDone: Int,
    val nextGameId: Int
) {
    companion object {
        fun initial(): GamePlayingResponse {
            return GamePlayingResponse(
                gameData = GameData.initial(),
                gridRowsState = emptyMap(),
                selection = emptyList(),
                livesEarnedOnDone = 0,
                nextGameId = -1
            )
        }
    }
}

data class GamePlayingSubmitResponse(
    val gamePlaying: GamePlayingResponse,
    val rowGuessedOnSubmit: Boolean,
    val totalLivesAfterOnDone: Int,
)
