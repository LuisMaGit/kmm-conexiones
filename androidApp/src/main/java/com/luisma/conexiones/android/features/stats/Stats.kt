package com.luisma.conexiones.android.features.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CBottomSheet
import com.luisma.conexiones.android.core_ui.components.CBottomSheetLoader
import com.luisma.conexiones.models.BasicScreenState

@Composable
fun Stats(
    state: StatsState,
    sendEvent: (event: StatsEvents) -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        sendEvent(StatsEvents.InitStats)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            sendEvent(StatsEvents.DisposeStats)
        }
    }

    if (state.screenState == BasicScreenState.Success) {
        StatsContent(state = state)
    } else if (state.screenState == BasicScreenState.Loading) {
        CBottomSheetLoader()
    }

}

@Composable
fun StatsBottomSheet(
    state: StatsState,
    sendEvent: (event: StatsEvents) -> Unit,
    onDismiss: () -> Unit
) {
    CBottomSheet(
        title = stringResource(id = R.string.stats_title),
        show = true,
        onDismiss = { onDismiss() }
    ) {
        Stats(
            state = state,
            sendEvent = { sendEvent(it) }
        )
    }
}