package com.luisma.conexiones.android.features.levels

import com.luisma.conexiones.android.core_ui.components.LevelCardType
import com.luisma.conexiones.models.game.GameState

fun mapLevelsState(gameState: GameState): LevelCardType {
    return when(gameState) {
        GameState.Win -> LevelCardType.Win
        GameState.Lost -> LevelCardType.Lost
        GameState.Playing -> LevelCardType.Playing
        GameState.Free -> LevelCardType.Unlock
        GameState.Lock -> LevelCardType.Lock
    }
}