package com.luisma.conexiones.services_db

import com.luisma.conexiones.services_db.db_models.GameDBModel
import com.shared.db.Game
import com.shared.db.SelectLevelsPaginated

interface IGameDBService {
    suspend fun selectGamesPaginated(limit: Int, offset: Int): List<GameDBModel>
    suspend fun selectTotalGames(): Int
    suspend fun selectGameId(gameId: Int): Int?
    suspend fun selectCurrentlyPlayingGameRowId(): Int?
    suspend fun selectWinedGamesCount(): Int
    suspend fun selectLostGamesCount(): Int
    suspend fun selectTotalPlayedCount(): Int
    suspend fun selectGuessedTriesCount(guessedTries: Int): Int
    suspend fun selectGame(gameId: Int): GameDBModel
    suspend fun selectGameTries(gameId: Int): Int

    suspend fun selectGuessedDifficulty(gameId: Int): String
    suspend fun updateGameState(gameId: Int, state: String)
    suspend fun updateGameDistribution(gameId: Int, distribution: String)
    suspend fun updateGameTries(gameId: Int, guessedTries: Int)
    suspend fun updateGuessedDifficulty(gameId: Int, guessedDifficulty: String)
}

class GameDBService : IGameDBService {
    private val queries = DBInstance.db.gameQueries
    private fun levelsMapper(level: SelectLevelsPaginated): GameDBModel {
        return GameDBModel(
            id = level.game_id.toInt(),
            state = level.state ?: "",
            guessedTries = level.guessed_tries?.toInt() ?: 0,
            guessedDifficulty = "",
            game = "",
            gameDistribution = ""
        )
    }

    private fun gameMapper(game: Game): GameDBModel {
        return GameDBModel(
            id = game.game_id.toInt(),
            state = game.state ?: "",
            guessedTries = game.guessed_tries?.toInt() ?: 0,
            guessedDifficulty = game.guessed_difficulty ?: "",
            game = game.game ?: "",
            gameDistribution = game.game_distribution ?: ""
        )
    }

    override suspend fun selectGamesPaginated(limit: Int, offset: Int): List<GameDBModel> {
        return queries.selectLevelsPaginated(
            limit = limit.toLong(),
            offset = offset.toLong()
        )
            .executeAsList()
            .map { level -> levelsMapper(level = level) }
    }

    override suspend fun selectTotalGames(): Int {
        return queries.selectTotalGames().executeAsOne().toInt()
    }

    override suspend fun selectGameId(gameId: Int): Int? {
        return queries.selectGameId(gameId.toLong()).executeAsOneOrNull()?.toInt()
    }


    override suspend fun selectCurrentlyPlayingGameRowId(): Int? {
        return queries.selectCurrentlyPlayingGameRowId().executeAsOneOrNull()?.toInt()
    }

    override suspend fun selectWinedGamesCount(): Int {
        return queries.selectWinedGamesCount().executeAsOne().toInt()
    }

    override suspend fun selectLostGamesCount(): Int {
        return queries.selectLostGamesCount().executeAsOne().toInt()
    }

    override suspend fun selectTotalPlayedCount(): Int {
        return queries.selectTotalPlayedCount().executeAsOne().toInt()
    }

    override suspend fun selectGuessedTriesCount(guessedTries: Int): Int {
        return queries.selectGuessedTriesCount(guessedTries.toLong()).executeAsOne().toInt()
    }

    override suspend fun selectGame(gameId: Int): GameDBModel {
        return gameMapper(queries.selectGame(game_id = gameId.toLong()).executeAsOne())
    }

    override suspend fun selectGameTries(gameId: Int): Int {
        return queries.selectGameTries(game_id = gameId.toLong())
            .executeAsOne().guessed_tries?.toInt() ?: 0
    }

    override suspend fun selectGuessedDifficulty(gameId: Int): String {
        return queries.selectGuessedDifficulty(game_id = gameId.toLong())
            .executeAsOne().guessed_difficulty ?: ""
    }

    override suspend fun updateGameState(gameId: Int, state: String) {
        queries.updateGameState(game_id = gameId.toLong(), state = state)
    }

    override suspend fun updateGameDistribution(gameId: Int, distribution: String) {
        queries.updateGameDistribution(
            game_id = gameId.toLong(),
            game_distribution = distribution
        )
    }

    override suspend fun updateGameTries(gameId: Int, guessedTries: Int) {
        queries.updateGameTries(
            game_id = gameId.toLong(),
            guessed_tries = guessedTries.toLong()
        )
    }

    override suspend fun updateGuessedDifficulty(gameId: Int, guessedDifficulty: String) {
        queries.updateGuessedDifficulty(
            game_id = gameId.toLong(),
            guessed_difficulty = guessedDifficulty
        )
    }
}