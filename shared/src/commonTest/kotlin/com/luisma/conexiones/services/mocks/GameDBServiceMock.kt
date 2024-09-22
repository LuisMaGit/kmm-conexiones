package com.luisma.conexiones.services.mocks

import com.luisma.conexiones.services_db.IGameDBService
import com.luisma.conexiones.services_db.db_models.GameDBModel

class GameDBServiceMock : IGameDBService {
    override suspend fun selectGamesPaginated(limit: Int, offset: Int): List<GameDBModel> {
        TODO("Not yet implemented")
    }

    override suspend fun selectTotalGames(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun selectCurrentlyPlayingGameRowId(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun selectWinedGamesCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun selectLostGamesCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun selectTotalPlayedCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun selectGuessedTriesCount(guessedTries: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun selectGame(gameId: Int): GameDBModel {
        TODO("Not yet implemented")
    }

    override suspend fun selectGameTries(gameId: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun selectGuessedDifficulty(gameId: Int): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateGameState(gameId: Int, state: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGameDistribution(gameId: Int, distribution: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGameTries(gameId: Int, guessedTries: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGuessedDifficulty(gameId: Int, guessedDifficulty: String) {
        TODO("Not yet implemented")
    }
}


fun gameDBMockService(): IGameDBService {
    return GameDBServiceMock()
}