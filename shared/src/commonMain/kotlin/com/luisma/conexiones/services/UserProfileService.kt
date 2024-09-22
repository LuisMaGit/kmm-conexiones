package com.luisma.conexiones.services

import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.services_db.IUserProfileDBService


interface IUserProfileService {
    fun gameLivesByStateAndTries(gameState: GameState, guessedTries: Int): Int
    suspend fun getLives(): Int
    suspend fun updateLives(lives: Int)
    suspend fun appWasOpenedBefore(): Boolean
}

class UserProfileService(
    val userProfileDBService: IUserProfileDBService
) : IUserProfileService {

    override fun gameLivesByStateAndTries(gameState: GameState, guessedTries: Int): Int {

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

    override suspend fun getLives(): Int {
        return userProfileDBService.selectLives()
    }

    override suspend fun updateLives(lives: Int) {
        userProfileDBService.updateLives(lives)
    }


    override suspend fun appWasOpenedBefore(): Boolean {
        if (userProfileDBService.selectAppOpened() == 0) {
            userProfileDBService.updateAppOpened(opened = 1)
            return false
        }

        return true
    }
}