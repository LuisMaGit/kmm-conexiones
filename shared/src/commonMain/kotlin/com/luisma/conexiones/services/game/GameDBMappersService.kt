package com.luisma.conexiones.services.game

import com.luisma.conexiones.contracts.GAME_AMOUNT_TRIES
import com.luisma.conexiones.models.game.GameData
import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.models.game.GameTile
import com.luisma.conexiones.models.game.GameTileJson
import com.luisma.conexiones.services.IStringUtilsService
import com.luisma.conexiones.services.IUserProfileService
import com.luisma.conexiones.services_db.db_models.GameDBModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface IGameDBMappersService {
    fun gameFromDBToGameData(db: GameDBModel): GameData
    fun decodeGameTiles(json: String): List<GameTile>
    fun decodeGameDistribution(json: String?): List<GameDistribution>

    fun decodeGuessedDifficulty(dbGuessedDifficulty: String): Set<Int>
    fun encodeGuessedDifficulty(difficulties: Set<Int>): String
    fun encodeGameDistribution(gameDistribution: List<GameDistribution>): String

}

class GameDBMappersService(
    private val strUtilsService: IStringUtilsService,
    private val userProfileService: IUserProfileService
) : IGameDBMappersService {

    override fun gameFromDBToGameData(db: GameDBModel): GameData {
        val id = db.id
        val gameTiles = decodeGameTiles(db.game)
        val gameState = GameState.fromRaw(db.state)
        val guessedTries = db.guessedTries
        val guessedDifficulty = decodeGuessedDifficulty(db.guessedDifficulty)
        val gameDistribution = decodeGameDistribution(db.gameDistribution)
        val livesEarnedByWinning = userProfileService.gameLivesByStateAndTries(
            gameState = gameState,
            guessedTries = guessedTries
        )
        var remainingErrors = GAME_AMOUNT_TRIES - guessedTries
        remainingErrors = if (remainingErrors <= 0) 0 else remainingErrors

        return GameData(
            id = id,
            gameTiles = gameTiles,
            gameState = gameState,
            guessedTries = guessedTries,
            guessedDifficulty = guessedDifficulty,
            livesEarnedByWinning = livesEarnedByWinning,
            gameDistribution = gameDistribution,
            remainingErrors = remainingErrors
        )
    }


    override fun decodeGameTiles(json: String): List<GameTile> {
        if (json.isEmpty()) {
            return emptyList()
        }
        val gameTileJson = Json.decodeFromString<List<GameTileJson>>(json)
        return gameTileJson.map { decoded ->
            val words = strUtilsService.separatedWordsToWordList(
                str = decoded.words
            )
            val wordsSeparated = strUtilsService.joinWords(
                words = words
            )
            GameTile(
                words = words,
                difficulty = decoded.difficulty,
                description = decoded.description,
                wordsJoined = wordsSeparated,
            )
        }.toList()
    }

    override fun decodeGameDistribution(json: String?): List<GameDistribution> {
        if (json.isNullOrEmpty()) {
            return emptyList()
        }
        return Json.decodeFromString<List<GameDistribution>>(json)
    }

    override fun decodeGuessedDifficulty(dbGuessedDifficulty: String): Set<Int> {
        return strUtilsService
            .arrayStrToArrayInt(dbGuessedDifficulty)
            .toSet()
    }

    override fun encodeGuessedDifficulty(difficulties: Set<Int>): String {
        return strUtilsService.joinDifficulties(difficulties)
    }

    override fun encodeGameDistribution(gameDistribution: List<GameDistribution>): String {
        return Json.encodeToString<List<GameDistribution>>(gameDistribution)
    }


}