package com.luisma.conexiones.android.features.app_bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppBarViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppBarState.initial())
    val state = _state.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(AppBarViewModel::class) {
                AppBarViewModel()
            }
        }
    }

    fun sendEvent(event: AppBarEvents) {
        when (event) {
            is AppBarEvents.ToggleStats -> toggleStats()
            is AppBarEvents.ToggleLives -> toggleLives()
            is AppBarEvents.ToggleTutorial -> toggleTutorial()
            is AppBarEvents.SetLives -> setLives(event.lives)
        }
    }

    private fun toggleStats() {
        _state.update {
            it.copy(showStats = !_state.value.showStats)
        }
    }

    private fun toggleLives() {
        _state.update {
            it.copy(showLives = !_state.value.showLives)
        }
    }

    private fun toggleTutorial() {
        _state.update {
            it.copy(showTutorial = !_state.value.showTutorial)
        }
    }

    private fun setLives(lives: Int?) {
        if (lives == null || lives == _state.value.lives) {
            return
        }

        _state.update {
            it.copy(
                lives = lives,
                showLives = lives == 0
            )
        }
    }
}