package com.luisma.conexiones.android.features.app_bar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luisma.conexiones.android.features.lives.LivesBottomSheet
import com.luisma.conexiones.android.features.lives.LivesViewModel
import com.luisma.conexiones.android.features.stats.StatsBottomSheet
import com.luisma.conexiones.android.features.stats.StatsViewModel
import com.luisma.conexiones.android.features.tutorial.TutorialBottomSheet

@Composable
fun AppBarBuilder(
    leadingComponentType: AppBarLeadingComponentType,
    lives: Int? = null,
    onTapLogo: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    openTutorial: Boolean = false,
    onDismissTutorial: (() -> Unit)? = null
) {

    val appBarViewModel = viewModel<AppBarViewModel>(factory = AppBarViewModel.Factory)
    val appBarState by appBarViewModel.state.collectAsState()

    AppBar(
        sendEvent = { appBarViewModel.sendEvent(event = it) },
        leadingComponentType = leadingComponentType,
        lives = lives,
        state = appBarState,
        onTapLogo = onTapLogo,
        onBack = onBack,
        openTutorial = openTutorial
    )

    // stats
    if (appBarState.showStats) {
        val statsViewModel = viewModel<StatsViewModel>(factory = StatsViewModel.Factory)
        val statsState by statsViewModel.state.collectAsState()
        StatsBottomSheet(
            sendEvent = { statsViewModel.sendEvent(it) },
            state = statsState,
            onDismiss = { appBarViewModel.sendEvent(AppBarEvents.ToggleStats) }
        )
    }

    // lives
    if (appBarState.showLives) {
        val livesViewModel = viewModel<LivesViewModel>(factory = LivesViewModel.Factory)
        val livesState by livesViewModel.state.collectAsState()
        LivesBottomSheet(
            sendEvent = { livesViewModel.sendEvent(it) },
            state = livesState,
            onDismiss = { appBarViewModel.sendEvent(AppBarEvents.ToggleLives) }
        )
    }

    // tutorial
    if (appBarState.showTutorial) {
        TutorialBottomSheet(
            onDismiss = {
                if (onDismissTutorial != null) {
                    onDismissTutorial()
                }
                appBarViewModel.sendEvent(AppBarEvents.ToggleTutorial)
            }
        )
    }
}