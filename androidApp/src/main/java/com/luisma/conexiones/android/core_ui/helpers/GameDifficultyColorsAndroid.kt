package com.luisma.conexiones.android.core_ui.helpers

import androidx.compose.ui.graphics.Color
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.contracts.GameDifficultyColors


fun getColorCoreByDifficulty(difficulty: Int): GameDifficultyColors {
    return when (difficulty) {
        0 -> GameDifficultyColors.GREEN
        1 -> GameDifficultyColors.YELLOW
        2 -> GameDifficultyColors.ORANGE
        3 -> GameDifficultyColors.RED
        else -> GameDifficultyColors.GREEN
    }
}

fun getColorByDomainColor(color: GameDifficultyColors): Color {
    return when (color) {
        GameDifficultyColors.GREEN -> CColor.green
        GameDifficultyColors.YELLOW -> CColor.yellow
        GameDifficultyColors.ORANGE -> CColor.orange
        GameDifficultyColors.RED -> CColor.red
    }
}