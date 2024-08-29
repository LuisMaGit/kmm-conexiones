package com.luisma.conexiones.services_db

import com.luisma.conexiones.platform.conSqlDriver
import com.luisma.conexiones.services_db.db_models.GameDBModel
import com.shared.db.ConexionesDatabase
import com.shared.db.SelectLevelsPaginated

interface IGameDBService {
    suspend fun selectGamesPaginated(limit: Int, offset: Int): List<GameDBModel>
    suspend fun selectTotalGames(): Int
    suspend fun selectCurrentlyPlayingGameRowId(): Int
    suspend fun selectWinedGamesCount(): Int
    suspend fun selectLostGamesCount(): Int
    suspend fun selectTotalPlayedCount(): Int
    suspend fun selectGuessedTriesCount(guessedTries: Int): Int
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

    override suspend fun selectCurrentlyPlayingGameRowId(): Int {
        return queries.selectCurrentlyPlayingGameRowId().executeAsOne().toInt()
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
}