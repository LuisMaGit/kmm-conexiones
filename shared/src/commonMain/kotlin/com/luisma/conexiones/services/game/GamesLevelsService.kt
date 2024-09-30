package com.luisma.conexiones.services.game

import com.luisma.conexiones.models.game.GameData
import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.services.PageState
import com.luisma.conexiones.services.PaginationService
import com.luisma.conexiones.services_db.IGameDBService

class GamesLevelsService(
    private val dbService: IGameDBService,
    private val paginationService: PaginationService,
    private val gameDBMappersService: IGameDBMappersService,
    private val gameLevelsInPage: Int,
) {

    suspend fun getLevelsPlayingPage(): GamesPageResponse {
        var playingRowId = dbService.selectCurrentlyPlayingGameRowId()
        if (playingRowId == null) {
            playingRowId = dbService.selectTotalGames()
        }
        val playingPageIdxOffset = paginationService.getPlayingPageIdxScrollOffset(
            rowId = playingRowId
        )
        val playingPage = paginationService.getPageByRowId(rowId = playingRowId)
        val pageData = getLevelsByPage(page = playingPage)

        return pageData.copy(
            firstPagePlayingLevelIdxOffset = playingPageIdxOffset,
            playingRowId = playingRowId
        )
    }

    suspend fun getLevelsByPage(page: Int): GamesPageResponse {
        val dbResponse = dbService.selectGamesPaginated(
            limit = gameLevelsInPage, offset = paginationService.getOffsetByPage(page)
        )

        if (dbResponse.isEmpty()) {
            return GamesPageResponse(
                pageState = PageState.FirstAndLast, page = 1, games = emptyList()
            )
        }
        return GamesPageResponse(
            pageState = paginationService.getPageState(
                itemsInPage = dbResponse.count(), page = page
            ), games = dbResponse.map {
                gameDBMappersService.gameFromDBToGameData(it)
            }, page = page
        )
    }


    fun getIndexOfRowId(games: List<GameData>, rowId: Int): Int {
        val idx = games.indexOfFirst { it.id == rowId }
        return if (idx == -1) 0 else idx
    }

    fun canGoToLevel(
        games: List<GameData>,
        lives: Int,
        gameId: Int,
    ): Boolean {
        if (lives > 0) {
            return true
        }

        val game = games.first { it.id == gameId }
        return game.gameState == GameState.Win || game.gameState == GameState.Lost
    }
}

data class GamesPageResponse(
    val pageState: PageState,
    val page: Int,
    val games: List<GameData>,
    val firstPagePlayingLevelIdxOffset: Int = 0,
    val playingRowId: Int = 0,
)




