package com.luisma.conexiones.models.game

import kotlinx.serialization.Serializable

data class GameTile(
    val words: List<String>,
    val description: String,
    val difficulty: Int,
    val wordsJoined: String
)

@Serializable
data class GameTileJson(
    val words: String,
    val description: String,
    val difficulty: Int
)