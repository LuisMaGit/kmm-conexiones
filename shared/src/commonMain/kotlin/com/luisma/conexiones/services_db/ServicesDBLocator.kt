package com.luisma.conexiones.services_db

fun gameDBService(): IGameDBService {
    return GameDBService()
}

fun userProfileDBService(): IUserProfileDBService {
    return UserProfileDBService()
}