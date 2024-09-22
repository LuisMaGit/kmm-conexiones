package com.luisma.conexiones.services.mocks

import com.luisma.conexiones.models.game.GameData
import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameTile
import com.luisma.conexiones.services.game.IGameDBMappersService
import com.luisma.conexiones.services_db.db_models.GameDBModel

class GameMapperMockService : IGameDBMappersService {
    override fun gameFromDBToGameData(db: GameDBModel): GameData {
        TODO("Not yet implemented")
    }

    override fun decodeGameTiles(json: String): List<GameTile> {
        TODO("Not yet implemented")
    }

    override fun decodeGameDistribution(json: String?): List<GameDistribution> {
        TODO("Not yet implemented")
    }

    override fun decodeGuessedDifficulty(dbGuessedDifficulty: String): Set<Int> {
        TODO("Not yet implemented")
    }

    override fun encodeGuessedDifficulty(difficulties: Set<Int>): String {
        TODO("Not yet implemented")
    }

    override fun encodeGameDistribution(gameDistribution: List<GameDistribution>): String {
        TODO("Not yet implemented")
    }
}

fun gameDBMapperMockService() : IGameDBMappersService {
    return GameMapperMockService()
}