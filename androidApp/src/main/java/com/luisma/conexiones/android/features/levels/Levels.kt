package com.luisma.conexiones.android.features.levels

import androidx.compose.runtime.Composable
import com.luisma.conexiones.android.features.levels.components.LevelsGrid
import com.luisma.conexiones.android.features.levels.components.LevelsLoading
import com.luisma.conexiones.android.features.levels.components.LevelsWrapper
import com.luisma.conexiones.models.BasicScreenState

@Composable
fun Levels(
    state: LevelsState,
    sendEvent: (event: LevelsEvents) -> Unit
) {
    LevelsWrapper(
        lives = if (state.screenState == BasicScreenState.Success) state.lives else null,
        onTapLogo = { sendEvent(LevelsEvents.RefreshScreen) },
        openTutorial = state.openTutorial,
        onDismissTutorial = { sendEvent(LevelsEvents.DismissTutorial) }
    ) {
        if (state.screenState == BasicScreenState.Success) {
            LevelsGrid(
                state = state,
                sendEvent = sendEvent
            )
        } else if (state.screenState == BasicScreenState.Loading) {
            LevelsLoading()
        }
    }
}
