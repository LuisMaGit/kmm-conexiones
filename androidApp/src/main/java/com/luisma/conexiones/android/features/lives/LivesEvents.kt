package com.luisma.conexiones.android.features.lives

sealed class LivesEvents {
    data object InitLevels : LivesEvents()
    data object DisposeLevels : LivesEvents()
    data class EnableLivesButton(val enable: Boolean) : LivesEvents()
    data object OnAddWatched : LivesEvents()
}
