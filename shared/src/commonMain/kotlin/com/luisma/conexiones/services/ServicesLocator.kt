package com.luisma.conexiones.services

import com.luisma.conexiones.contracts.GAME_LEVELS_IN_PAGE
import com.luisma.conexiones.services.game.GameDBMappersService
import com.luisma.conexiones.services.game.GamePlayService
import com.luisma.conexiones.services.game.GameSelectionService
import com.luisma.conexiones.services.game.GameSortService
import com.luisma.conexiones.services.game.GamesLevelsService
import com.luisma.conexiones.services.game.IGameDBMappersService
import com.luisma.conexiones.services_db.gameDBService
import com.luisma.conexiones.services_db.userProfileDBService

fun stringUtilsService(): IStringUtilsService {
    return StringUtilsService()
}

fun numbUtilsService(): INumbUtilsService {
    return NumbUtilsService()
}

fun userProfileService(): IUserProfileService {
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

fun gameDBMappersService(): IGameDBMappersService {
    return GameDBMappersService(
        strUtilsService = stringUtilsService(),
        userProfileService = userProfileService()
    )
}

fun gamePlayService(): GamePlayService {
    return GamePlayService(
        gameDBService = gameDBService(),
        mapperService = gameDBMappersService(),
        gameSortService = gameSortService(),
        gameSelectionService = gameSelectionService(),
        userProfileService = userProfileService(),
        userProfileDBService = userProfileDBService(),
    )
}

fun gameSortService(): GameSortService {
    return GameSortService(
        numbUtilsService = numbUtilsService(),
    )
}

fun gameSelectionService(): GameSelectionService {
    return GameSelectionService()
}


