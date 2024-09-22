package com.luisma.conexiones.services_db.db_models

data class GameDBModel(
    val id: Int,
    val game: String,
    val state: String,
    val guessedTries: Int,
    val guessedDifficulty: String,
    val gameDistribution: String,
)