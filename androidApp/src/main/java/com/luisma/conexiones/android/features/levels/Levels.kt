package com.luisma.conexiones.android.features.levels

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
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
        openTutorial = state.openTutorial
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

@Preview
@Composable
private fun LevelsPreview() {
    CThemeProvider {
        Levels(
            state = LevelsState.initial(),
            sendEvent = {}
        )
    }
}