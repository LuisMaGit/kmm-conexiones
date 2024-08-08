package com.luisma.conexiones.android.features.levels

sealed class LevelsEvents {
    data object SetInitialScrollDone : LevelsEvents()
    data object RefreshScreen : LevelsEvents()
    data object GetPreviousPage : LevelsEvents()
    data object GetNextPage : LevelsEvents()
    data class OnVisibilityChangePlayingCard(val show: Boolean) : LevelsEvents()
}