package com.luisma.conexiones.services_db

interface IUserProfileDBService {
    suspend fun selectLives(): Int
    suspend fun updateLives(lives: Int)
    suspend fun selectAppOpened(): Int
    suspend fun updateAppOpened(opened: Int)
}

class UserProfileDBService : IUserProfileDBService {

    private val queries = DBInstance.db.user_profileQueries

    override suspend fun selectLives(): Int {
        return queries.selectLives().executeAsOne().lives?.toInt() ?: 0
    }

    override suspend fun updateLives(lives: Int) {
        return queries.updateLives(lives = lives.toLong())
    }

    override suspend fun selectAppOpened(): Int {
        return queries.selectAppOpened().executeAsOne().app_oppened?.toInt() ?: 0
    }

    override suspend fun updateAppOpened(opened: Int) {
        return queries.updateAppOpened(opened = opened.toLong())
    }

}