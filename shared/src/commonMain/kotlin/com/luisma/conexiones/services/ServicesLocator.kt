package com.luisma.conexiones.services

import com.luisma.conexiones.contracts.GAME_LEVELS_IN_PAGE
import com.luisma.conexiones.services_db.gameDBService
import com.luisma.conexiones.services_db.userProfileDBService

fun stringUtilsService(): IStringUtilsService {
    return StringUtilsService()
}

fun userProfileService(): UserProfileService {
    return UserProfileService(
        userProfileDBService = userProfileDBService()
    )
}

fun paginationService(): PaginationService {
    return PaginationService(
        gameLevelsInPage = GAME_LEVELS_IN_PAGE
    )
}

fun gamesLevelsService(): GamesLevelsService {
    return GamesLevelsService(
        dbService = gameDBService(),
        paginationService = paginationService(),
        gameDBMappersService = gameDBMappersService(),
        gameLevelsInPage = GAME_LEVELS_IN_PAGE
    )
}

fun statsService(): StatsService {
    return StatsService(
        dbService = gameDBService()
    )
}

fun gameDBMappersService(): GameDBMappersService {
    return GameDBMappersService(
        strUtilsService = stringUtilsService(),
        userProfileService = userProfileService()
    )
}

