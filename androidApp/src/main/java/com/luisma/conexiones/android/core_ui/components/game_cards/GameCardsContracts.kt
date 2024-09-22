package com.luisma.conexiones.android.core_ui.components.game_cards

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.theme.cFontSize14sp
import com.luisma.conexiones.android.core_ui.theme.cFontSize16sp
import com.luisma.conexiones.android.core_ui.theme.cFontSize20sp

sealed class GameCardsContracts(
    val wordCardWidth: Dp,
    val cardHeight: Dp,
    val wordCardFontSize: TextUnit,
    val cardSpacing: Dp,
    val guessedCardWidth: Dp,
    val guessedCardTitleFontSize: TextUnit,
    val guessedCardDescFontSize: TextUnit,
) {
    data object Mobile : GameCardsContracts(
        wordCardWidth = 80.dp,
        cardHeight = 60.dp,
        wordCardFontSize = cFontSize14sp,
        cardSpacing = 3.dp,
        guessedCardWidth = 329.dp, // 80 * 4 + 3 * 3
        guessedCardTitleFontSize = cFontSize16sp,
        guessedCardDescFontSize = cFontSize14sp
    )

    data object Tablet : GameCardsContracts(
        wordCardWidth = 150.dp,
        cardHeight = 80.dp,
        wordCardFontSize = cFontSize20sp,
        cardSpacing = 4.dp,
        guessedCardWidth = 612.dp, // 150 * 4 + 4 * 3
        guessedCardTitleFontSize = cFontSize20sp,
        guessedCardDescFontSize = cFontSize16sp
    )

}


