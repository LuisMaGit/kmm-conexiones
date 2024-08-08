package com.luisma.conexiones.android.features.levels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LevelsBuilder() {
    val levelsViewModel = viewModel<LevelsViewModel>(factory = LevelsViewModel.Factory)
    val levelsState by levelsViewModel.state.collectAsState()
    Levels(
        state = levelsState,
        sendEvent = { levelsViewModel.sendEvent(it) }
    )
}