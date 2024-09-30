package com.luisma.conexiones.android.features.levels

sealed class LevelsEvents {
    data object SetInitialScrollDone : LevelsEvents()
    data object RefreshScreen : LevelsEvents()
    data object GetPreviousPage : LevelsEvents()
    data object GetNextPage : LevelsEvents()
    data class RefreshFromFlag(val flag: String) : LevelsEvents()
    data class OnVisibilityChangePlayingCard(val show: Boolean) : LevelsEvents()
    data class OnTapLevel(val gameId: Int) : LevelsEvents()

    data object  DismissTutorial  : LevelsEvents()
}