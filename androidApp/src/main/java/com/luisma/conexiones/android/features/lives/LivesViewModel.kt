package com.luisma.conexiones.android.features.lives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.services.UserProfileService
import com.luisma.conexiones.services.userProfileService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LivesViewModel(
    val userProfileService: UserProfileService
) : ViewModel() {

    private val _state = MutableStateFlow(LivesState.initial())
    val state = _state.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(LivesViewModel::class) {
                LivesViewModel(
                    userProfileService = userProfileService()
                )
            }
        }
    }

    fun sendEvent(event: LivesEvents) {
        when (event) {
            LivesEvents.InitLevels -> initLives()
            LivesEvents.DisposeLevels -> disposeLives()
            LivesEvents.GetLives -> getLives()
        }
    }

    private fun initLives() {
        if (!_state.value.initialized) {
            viewModelScope.launch {
                val lives = userProfileService.getLives()
                _state.update {
                    it.copy(
                        screenState = BasicScreenState.Success,
                        initialized = true,
                        lives = lives
                    )
                }
            }
        }
    }

    private fun disposeLives() {
        _state.update {
            LivesState.initial()
        }
    }

    private fun getLives() {
        viewModelScope.launch {
            userProfileService.updateLives(lives = 0)
        }
    }

}