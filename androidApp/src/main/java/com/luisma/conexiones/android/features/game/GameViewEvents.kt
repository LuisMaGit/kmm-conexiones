package com.luisma.conexiones.android.features.game

sealed class GameViewEvents {
    data class OnCreate(val gameId: Int) : GameViewEvents()
    data class SelectWord(val col: Int, val row: Int) : GameViewEvents()
    data class DismissAnimation(val col: Int, val row: Int) : GameViewEvents()
    data class GoToNextLevel(val nextLevelId: Int) : GameViewEvents()
    data object MoreLives : GameViewEvents()
    data object MixGame : GameViewEvents()
    data object ClearSelection : GameViewEvents()
    data object Submit : GameViewEvents()
    data object OnBack : GameViewEvents()
}