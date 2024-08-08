package com.luisma.conexiones.services

import com.luisma.conexiones.models.GameData
import com.luisma.conexiones.models.GameState
import com.luisma.conexiones.models.GameTile
import com.luisma.conexiones.models.GameTileJson
import com.luisma.conexiones.services_db.db_models.GameDBModel
import kotlinx.serialization.json.Json

class GameDBMappersService(
    private val strUtilsService: IStringUtilsService,
    private val userProfileService: UserProfileService
) {

    fun gameFromDBToGameData(db: GameDBModel): GameData {
        val id = db.id
        val gameTiles = decodeGameTiles(db.game)
        val gameState = GameState.fromRaw(db.state)
        val guessedTries = db.guessedTries
        val guessedDifficulty = strUtilsService
            .arrayStrToArrayInt(db.guessedDifficulty)
            .toSet()

        val livesEarnedByWinning = userProfileService.gameLivesByStateAndTries(
            gameState = gameState,
            guessedTries = guessedTries
        )
        return GameData(
            id = id,
            gameTiles = gameTiles,
            gameState = gameState,
            guessedTries = guessedTries,
            guessedDifficulty = guessedDifficulty,
            livesEarnedByWinning = livesEarnedByWinning
        )
    }


    fun decodeGameTiles(json: String): List<GameTile> {
        if (json.isEmpty()) {
            return emptyList()
        }
        val gameTileJson = Json.decodeFromString<List<GameTileJson>>(json)
        return gameTileJson.map { decoded ->
            GameTile(
                words = strUtilsService.separatedWordsToWordList(
                    str = decoded.words
                ),
                difficulty = decoded.difficulty,
                description = decoded.description,
            )
        }.toList()
    }

}