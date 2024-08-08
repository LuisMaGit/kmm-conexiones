package com.luisma.conexiones.android.features.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luisma.conexiones.models.BasicScreenState
import com.luisma.conexiones.services.StatsService
import com.luisma.conexiones.services.statsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatsViewModel(
    private val statsService: StatsService
) : ViewModel() {

    private val _state = MutableStateFlow(StatsState.initial())
    val state = _state.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            addInitializer(StatsViewModel::class) {
                StatsViewModel(
                    statsService = statsService(),
                )
            }
        }
    }

    fun sendEvent(event: StatsEvents) {
        when (event) {
            StatsEvents.InitStats -> initStats()
            StatsEvents.DisposeStats -> disposeStats()
        }
    }

    private fun initStats() {
        if (!_state.value.initialized) {
            viewModelScope.launch {
                val statsData = statsService.calculateStats()
                _state.update {
                    it.copy(
                        initialized = true,
                        statsData = statsData,
                        screenState = BasicScreenState.Success
                    )
                }
            }
        }
    }

    private fun disposeStats() {
        _state.update {
            StatsState.initial()
        }
    }
}