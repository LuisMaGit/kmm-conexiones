package com.luisma.conexiones.android.features.levels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luisma.conexiones.android.router.RoutePayload
import com.luisma.conexiones.android.router.RouterService
import com.luisma.conexiones.android.router.Routes
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.services.IUserProfileService
import com.luisma.conexiones.services.PaginationService
import com.luisma.conexiones.services.game.GamesLevelsService
import com.luisma.conexiones.services.gamesLevelsService
import com.luisma.conexiones.services.paginationService
import com.luisma.conexiones.services.userProfileService
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LevelsViewModel(
    private val gamesLevelsService: GamesLevelsService,
    private val paginationService: PaginationService,
    private val userProfileService: IUserProfileService,
    private val routerService: RouterService
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(LevelsViewModel::class) {
                LevelsViewModel(
                    gamesLevelsService = gamesLevelsService(),
                    paginationService = paginationService(),
                    userProfileService = userProfileService(),
                    routerService = RouterService
                )
            }
        }
    }

    private val _state = MutableStateFlow(LevelsState.initial())
    val state = _state.asStateFlow()

    init {
        initVM()
    }

    fun sendEvent(event: LevelsEvents) {
        when (event) {
            LevelsEvents.SetInitialScrollDone -> setInitialScrollDone()
            LevelsEvents.RefreshScreen -> refreshScreen()
            LevelsEvents.GetPreviousPage -> getPreviousPage()
            LevelsEvents.GetNextPage -> getNextPage()
            LevelsEvents.DismissTutorial -> dismissTutorial()
            is LevelsEvents.RefreshFromFlag -> refreshFromFlag(flag = event.flag)
            is LevelsEvents.OnVisibilityChangePlayingCard -> onVisibilityChange(show = event.show)
            is LevelsEvents.OnTapLevel -> onTapLevel(gameId = event.gameId)
        }
    }

    private fun initVM() {
        _state.update {
            LevelsState.initial().copy(
                refreshOnBackControlFlag = _state.value.refreshOnBackControlFlag,
            )
        }
        viewModelScope.launch {
            val responsePlayingDef = async { gamesLevelsService.getLevelsPlayingPage() }
            val responseLivesDef = async { userProfileService.getLives() }
            val appWasOpenedBeforeDef = async { userProfileService.appWasOpenedBefore() }
            val responsePlaying = responsePlayingDef.await()
            val responseLives = responseLivesDef.await()
            val appWasOpenedBefore = appWasOpenedBeforeDef.await()

            _state.update {
                it.copy(
                    screenState = BasicScreenState.Success,
                    games = responsePlaying.games,
                    startPage = responsePlaying.page,
                    initialScrollIdx = responsePlaying.firstPagePlayingLevelIdxOffset,
                    reachFirstPage = paginationService.reachFirstPageByPageState(
                        responsePlaying.pageState
                    ),
                    reachLastPage = paginationService.reachLastPageByPageState(
                        responsePlaying.pageState
                    ),
                    fetchedPages = setOf(responsePlaying.page),
                    playingRowId = responsePlaying.playingRowId,
                    playingRowIdx = gamesLevelsService.getIndexOfRowId(
                        games = responsePlaying.games,
                        rowId = responsePlaying.playingRowId
                    ),
                    playingRowIsShowing = true,
                    lives = responseLives,
                    openTutorial = !appWasOpenedBefore,
                )
            }
        }
    }

    private fun refreshScreen() {
        initVM()
    }

    private fun dismissTutorial() {
        if (_state.value.openTutorial) {
            _state.update {
                it.copy(openTutorial = false)
            }
        }
    }

    private fun setInitialScrollDone() {
        if (!_state.value.initialScrollDone) {
            _state.update {
                it.copy(
                    initialScrollDone = true,
                )
            }
        }
    }

    private fun getPreviousPage() {
        _state.update {
            it.copy(
                screenState = BasicScreenState.Loading,
            )
        }

        viewModelScope.launch {
            val fetchedPages = paginationService.getPreviousPage(
                fetchedPages = _state.value.fetchedPages
            )
            val responsePlaying = gamesLevelsService.getLevelsByPage(
                page = fetchedPages.first()
            )
            val games = responsePlaying.games + _state.value.games
            _state.update {
                it.copy(
                    screenState = BasicScreenState.Success,
                    games = games,
                    reachFirstPage = paginationService.reachFirstPageByPageState(
                        responsePlaying.pageState
                    ),
                    fetchedPages = fetchedPages,
                    playingRowIdx = gamesLevelsService.getIndexOfRowId(
                        games = games,
                        rowId = _state.value.playingRowId
                    ),
                    playingRowIsShowing = false
                )
            }
        }
    }

    private fun getNextPage() {
        viewModelScope.launch {
            val fetchedPages = paginationService.getNextPage(
                fetchedPages = _state.value.fetchedPages
            )
            val responsePlaying = gamesLevelsService.getLevelsByPage(
                page = fetchedPages.last()
            )
            val games = _state.value.games + responsePlaying.games
            _state.update {
                it.copy(
                    games = games,
                    reachLastPage = paginationService.reachLastPageByPageState(
                        responsePlaying.pageState
                    ),
                    fetchedPages = fetchedPages,
                    playingRowIdx = gamesLevelsService.getIndexOfRowId(
                        games = games,
                        rowId = _state.value.playingRowId
                    ),
                )
            }
        }

    }

    private fun onVisibilityChange(show: Boolean) {
        if (show == _state.value.playingRowIsShowing) {
            return
        }
        _state.update {
            it.copy(playingRowIsShowing = show)
        }
    }

    private fun onTapLevel(gameId: Int) {
        val canGo = gamesLevelsService.canGoToLevel(
            gameId = gameId,
            games = _state.value.games,
            lives = _state.value.lives,
        )
        if (!canGo) {
            return
        }

        viewModelScope.launch {
            routerService.setRoute(
                RoutePayload(
                    route = Routes.Game,
                    payload = gameId.toString()
                )
            )
        }
    }

    private fun refreshFromFlag(flag: String) {
        if (_state.value.refreshOnBackControlFlag == flag) {
            return
        }

        _state.update {
            it.copy(refreshOnBackControlFlag = flag)
        }

        refreshScreen()
    }

}