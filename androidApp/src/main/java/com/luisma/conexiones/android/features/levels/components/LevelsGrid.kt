package com.luisma.conexiones.android.features.levels.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CButton
import com.luisma.conexiones.android.core_ui.components.LevelCard
import com.luisma.conexiones.android.core_ui.components.LevelCardData
import com.luisma.conexiones.android.core_ui.components.LevelFloatingButton
import com.luisma.conexiones.android.core_ui.components.LevelLoadingCard
import com.luisma.conexiones.android.core_ui.components.levelsCardSize
import com.luisma.conexiones.android.core_ui.helpers.header
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.features.levels.LevelsEvents
import com.luisma.conexiones.android.features.levels.LevelsState
import com.luisma.conexiones.android.features.levels.mapLevelsState
import kotlinx.coroutines.launch


@Composable
fun LevelsGrid(
    state: LevelsState,
    sendEvent: (event: LevelsEvents) -> Unit
) {
    // ui
    val cardsPadding = 12.dp
    val gridPaddingBottom = 50.dp
    val levelsCardAmount = 2

    // loading cards
    val topLoadingHeader = if (state.reachFirstPage) 0 else 1
    val bottomLoadingCards =
        if (state.reachLastPage) 0 else levelsCardAmount

    // scroll
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(
        key1 = Unit
    ) {
        if (!state.initialScrollDone
            && state.initialScrollIdx > 0
        ) {
            gridState.scrollToItem(index = state.initialScrollIdx + topLoadingHeader)
            sendEvent(LevelsEvents.SetInitialScrollDone)
        }
    }

    // wrapper box: grid + floating button
    Box {
        // grid
        LazyVerticalGrid(
            columns = GridCells.FixedSize(
                size = levelsCardSize + cardsPadding * 2
            ),
            horizontalArrangement = Arrangement.Center,
            state = gridState,
            contentPadding = PaddingValues(bottom = gridPaddingBottom)
        ) {

            val cardModifier = Modifier.padding(
                all = cardsPadding,
            )

            // top loaders
            if (topLoadingHeader > 0) {
                header {
                    CButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = cardsPadding
                            ),
                        text = stringResource(id = R.string.levels_previous_levels_btn),
                        iconId = R.drawable.ic_arrow_up_solid,
                        onTap = {
                            sendEvent(LevelsEvents.GetPreviousPage)
                        }
                    )
                }
            }

            // grid levels
            items(
                count = state.games.count()
            ) { levelIdx ->
                val game = state.games[levelIdx]
                LevelCard(
                    modifier = cardModifier,
                    card = LevelCardData(
                        id = game.id,
                        type = mapLevelsState(game.gameState),
                        level = game.id.toString(),
                        livesReward = game.livesEarnedFormatted,
                    ),
                    checkVisibility = game.id == state.playingRowId,
                    onAppear = { sendEvent(LevelsEvents.OnVisibilityChangePlayingCard(show = true)) },
                    onDestroy = { sendEvent(LevelsEvents.OnVisibilityChangePlayingCard(show = false)) },
                    onTap = { id -> sendEvent(LevelsEvents.OnTapLevel(gameId = id)) }
                )
            }

            // bottom loaders
            if (bottomLoadingCards > 0) {
                items(
                    count = bottomLoadingCards
                ) { idx ->
                    LevelLoadingCard(
                        modifier = cardModifier,
                        idx = idx,
                        onAppear = { sendEvent(LevelsEvents.GetNextPage) },
                    )
                }
            }
        }

        // floating button
        if (!state.playingRowIsShowing) {
            LevelFloatingButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                onTap = {
                    coroutineScope.launch {
                        gridState.scrollToItem(index = state.playingRowIdx)
                    }
                }
            )
        }
    }
}


@Preview
@Composable
private fun LevelsGridPreview() {
    CThemeProvider {
        LevelsGrid(
            state = LevelsState.initial(),
            sendEvent = {}
        )
    }
}