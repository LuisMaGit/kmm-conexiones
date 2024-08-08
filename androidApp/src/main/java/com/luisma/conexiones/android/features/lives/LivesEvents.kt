package com.luisma.conexiones.android.features.lives

sealed class LivesEvents {
    data object InitLevels : LivesEvents()
    data object DisposeLevels : LivesEvents()
    data object GetLives : LivesEvents()
}