package com.luisma.conexiones.android.features.levels

import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.models.GameData

data class LevelsState(
    val screenState: BasicScreenState,
    val games: List<GameData>,
    val initialScrollIdx: Int,
    val initialScrollDone: Boolean,
    val playingRowId: Int,
    val playingRowIdx: Int,
    val playingRowIsShowing: Boolean,
    val lives: Int,
    val openTutorial: Boolean,
    // paging
    val startPage: Int,
    val reachFirstPage: Boolean,
    val reachLastPage: Boolean,
    val fetchedPages: Set<Int>,
) {
    companion object {
        fun initial(): LevelsState {
            return LevelsState(
                screenState = BasicScreenState.Loading,
                games = emptyList(),
                startPage = 0,
                reachFirstPage = false,
                reachLastPage = false,
                initialScrollIdx = 0,
                initialScrollDone = false,
                fetchedPages = emptySet(),
                playingRowId = 0,
                playingRowIdx = 0,
                playingRowIsShowing = false,
                lives = 0,
                openTutorial = false
            )
        }
    }
}