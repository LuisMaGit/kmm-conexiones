package com.luisma.conexiones.android.features.game

sealed class GameViewEvents {
    data class OnCreate(val gameId: Int) : GameViewEvents()
    data class Refresh(val gameId: Int) : GameViewEvents()
    data class SelectWord(val col: Int, val row: Int) : GameViewEvents()
    data class DismissWordAnimation(val col: Int, val row: Int) : GameViewEvents()
    data class DismissTileAnimation(val row: Int) : GameViewEvents()
    data class GoToNextLevel(val nextLevelId: Int) : GameViewEvents()
    data object EnableLivesButton : GameViewEvents()
    data object OnAddWatched: GameViewEvents()
    data object MixGame : GameViewEvents()
    data object ClearSelection : GameViewEvents()
    data object Submit : GameViewEvents()
    data object OnBack : GameViewEvents()
    data object DismissLivesAnimation : GameViewEvents()
    data object DismissDoneSignAnimation : GameViewEvents()
}