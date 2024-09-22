package com.luisma.conexiones.services.mocks

import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.services.IUserProfileService

class UserProfileMockService : IUserProfileService {
    override fun gameLivesByStateAndTries(gameState: GameState, guessedTries: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getLives(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateLives(lives: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun appWasOpenedBefore(): Boolean {
        TODO("Not yet implemented")
    }
}

fun userProfileMockService(): IUserProfileService {
    return UserProfileMockService()
}