package com.luisma.conexiones.models.game

data class GameGridRowState(
    val solved: Boolean,
    val solvedTile: GameTile,
    val distribution: List<GameDistribution>,
    val wordsRow: List<String>,
    val tileAnimationType: GameTileAnimationType = GameTileAnimationType.NoAnimation
)
