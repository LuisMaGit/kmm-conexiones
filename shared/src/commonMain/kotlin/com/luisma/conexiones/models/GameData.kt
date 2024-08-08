package com.luisma.conexiones.models

data class GameData(
    val id: Int,
    val gameTiles: List<GameTile>,
    val gameState: GameState,
    val guessedTries: Int,
    val guessedDifficulty: Set<Int>,
    val livesEarnedByWinning: Int
) {
    val livesEarnedFormatted: String
        get() {
            if (livesEarnedByWinning <= 0) {
               return livesEarnedByWinning.toString()
            }

            return "+$livesEarnedByWinning"
        }
}

