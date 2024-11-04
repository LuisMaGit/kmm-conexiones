package com.luisma.conexiones.android.features.lives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.services.IUserProfileService
import com.luisma.conexiones.services.userProfileService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LivesViewModel(
    val userProfileService: IUserProfileService,
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
            LivesEvents.OnAddWatched -> onAddWatched()
            is LivesEvents.EnableLivesButton -> enableLivesButton(event.enable)
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

    private fun enableLivesButton(enable: Boolean) {
        if (_state.value.enabledLivesButton == enable) return
        _state.update {
            it.copy(
                enabledLivesButton = enable
            )
        }
    }

    private fun onAddWatched() {
        viewModelScope.launch {
            val lives = userProfileService.getLives()
            userProfileService.updateLives(lives + 1)
        }
    }

}