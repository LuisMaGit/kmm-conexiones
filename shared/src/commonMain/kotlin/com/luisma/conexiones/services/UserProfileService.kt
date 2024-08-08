package com.luisma.conexiones.services

import com.luisma.conexiones.models.GameState
import com.luisma.conexiones.services_db.IUserProfileDBService

class UserProfileService(
    val userProfileDBService: IUserProfileDBService
) {

    fun gameLivesByStateAndTries(gameState: GameState, guessedTries: Int): Int {

        if (gameState == GameState.Lost) {
            return -1
        }

        if (gameState == GameState.Win) {
            if (guessedTries == 0) {
                return 3
            }
            if (guessedTries == 1) {
                return 2
            }
            if (guessedTries == 2 || guessedTries == 3) {
                return 1
            }
        }

        return 0

    }

    suspend fun getLives(): Int {
        return userProfileDBService.selectLives()
    }

    suspend fun updateLives(lives: Int) {
        userProfileDBService.updateLives(lives)
    }


    suspend fun appWasOpenedBefore(): Boolean {
        if (userProfileDBService.selectAppOpened() == 0) {
            userProfileDBService.updateAppOpened(opened = 1)
            return false
        }

        return true
    }
}