package com.luisma.conexiones.android.features.lives

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CBottomSheet
import com.luisma.conexiones.android.core_ui.components.CBottomSheetLoader
import com.luisma.conexiones.models.BasicScreenState

@Composable
fun Lives(
    state: LivesState,
    sendEvent: (event: LivesEvents) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        sendEvent(LivesEvents.InitLevels)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            sendEvent(LivesEvents.DisposeLevels)
        }
    }

    if (state.screenState == BasicScreenState.Success) {
        LivesContent(
            state = state,
            sendEvent = { sendEvent(it) }
        )
    } else if (state.screenState == BasicScreenState.Loading) {
        CBottomSheetLoader()
    }
}

@Composable
fun LivesBottomSheet(
    onDismiss: () -> Unit,
    state: LivesState,
    sendEvent: (event: LivesEvents) -> Unit
) {

    CBottomSheet(
        title = if (state.hasLife) {
            stringResource(id = R.string.lives_lives_title)
        } else {
            stringResource(id = R.string.lives_no_lives_title)
        },
        show = true,
        onDismiss = { onDismiss() }
    ) {
        Lives(
            state = state,
            sendEvent = { sendEvent(it) }
        )
    }

}