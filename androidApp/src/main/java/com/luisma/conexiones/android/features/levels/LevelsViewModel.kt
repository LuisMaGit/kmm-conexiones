package com.luisma.conexiones.android.features.levels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.services.GamesLevelsService
import com.luisma.conexiones.services.PaginationService
import com.luisma.conexiones.services.UserProfileService
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
    private val userProfileService: UserProfileService
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(LevelsViewModel::class) {
                LevelsViewModel(
                    gamesLevelsService = gamesLevelsService(),
                    paginationService = paginationService(),
                    userProfileService = userProfileService()
                )
            }
        }
    }

    private val _state = MutableStateFlow(LevelsState.initial())
    val state = _state.asStateFlow()

    init {
        initVM(resetState = false)
    }

    fun sendEvent(event: LevelsEvents) {
        when (event) {
            LevelsEvents.SetInitialScrollDone -> setInitialScrollDone()
            LevelsEvents.RefreshScreen -> refreshScreen()
            LevelsEvents.GetPreviousPage -> getPreviousPage()
            LevelsEvents.GetNextPage -> getNextPage()
            is LevelsEvents.OnVisibilityChangePlayingCard -> onVisibilityChange(show = event.show)
        }
    }

    private fun initVM(resetState: Boolean) {
        if (resetState) {
            _state.update {
                LevelsState.initial()
            }
        }
        viewModelScope.launch {
            val responsePlayingDef = async { gamesLevelsService.getLevelsPlayingPage() }
            val responsePlayingRowIdDef = async { gamesLevelsService.getPlayingRowId() }
            val responseLivesDef = async { userProfileService.getLives() }
            val appWasOpenedBeforeDef = async { userProfileService.appWasOpenedBefore() }
            val responsePlaying = responsePlayingDef.await()
            val responsePlayingRowId = responsePlayingRowIdDef.await()
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
                    playingRowId = responsePlayingRowId,
                    playingRowIdx = gamesLevelsService.getIndexOfRowId(
                        games = responsePlaying.games,
                        rowId = responsePlayingRowId
                    ),
                    playingRowIsShowing = true,
                    lives = responseLives,
                    openTutorial = !appWasOpenedBefore
                )
            }
        }
    }

    private fun refreshScreen() {
        initVM(resetState = true)
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
}