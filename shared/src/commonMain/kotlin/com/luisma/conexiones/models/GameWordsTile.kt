package com.luisma.conexiones.models

import kotlinx.serialization.Serializable

data class GameTile(
    val words: List<String>,
    val description: String,
    val difficulty: Int
)

@Serializable
data class GameTileJson(
    val words: String,
    val description: String,
    val difficulty: Int
)