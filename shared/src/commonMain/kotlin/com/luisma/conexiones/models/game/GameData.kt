package com.luisma.conexiones.models.game

data class GameData(
    val id: Int,
    val gameTiles: List<GameTile>,
    val gameState: GameState,
    val guessedTries: Int,
    val guessedDifficulty: Set<Int>,
    val gameDistribution: List<GameDistribution>,
    val remainingErrors: Int,
    val livesEarnedByWinning: Int,
) {

    companion object {
        fun initial(): GameData {
            return GameData(
                id = -1,
                gameTiles = emptyList(),
                gameState = GameState.Lock,
                guessedTries = 0,
                guessedDifficulty = emptySet(),
                livesEarnedByWinning = 0,
                remainingErrors = 0,
                gameDistribution = emptyList()
            )
        }
    }

    val livesEarnedFormatted: String
        get() {
            if (livesEarnedByWinning <= 0) {
                return livesEarnedByWinning.toString()
            }

            return "+$livesEarnedByWinning"
        }

}

