package com.luisma.conexiones.android.features.levels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.luisma.conexiones.android.features.levels.components.LevelsGrid
import com.luisma.conexiones.android.features.levels.components.LevelsLoading
import com.luisma.conexiones.android.features.levels.components.LevelsWrapper
import com.luisma.conexiones.models.BasicScreenState

@Composable
fun Levels(
    state: LevelsState,
    sendEvent: (event: LevelsEvents) -> Unit,
    resetFlag: String,
) {

    LaunchedEffect(key1 = resetFlag) {
        sendEvent(LevelsEvents.RefreshFromFlag(flag = resetFlag))
    }

    LevelsWrapper(
        lives = if (state.screenState == BasicScreenState.Success) state.lives else null,
        onTapLogo = { sendEvent(LevelsEvents.RefreshScreen) },
        openTutorial = state.openTutorial,
        onDismissTutorial = { sendEvent(LevelsEvents.DismissTutorial) },
        onDismissLives = { sendEvent(LevelsEvents.RefreshScreen) }
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
