package com.luisma.conexiones.android.core_ui.components.game_cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4
import com.luisma.conexiones.android.core_ui.theme.cSpace4

@Composable
fun GuessedCard(
    modifier: Modifier = Modifier,
    description: String,
    words: String,
    color: Color,
    sizeType: GameCardsContracts = GameCardsContracts.Mobile,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(cBorderRadius4))
            .background(color = color)
            .size(width = sizeType.guessedCardWidth, height = sizeType.cardHeight)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CText(
                modifier = Modifier.padding(bottom = cSpace4),
                text = description,
                fontSize = sizeType.guessedCardTitleFontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            CText(
                text = words,
                fontSize = sizeType.guessedCardDescFontSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview
@Composable
private fun GuessedCardPreview() {
    CThemeProvider {
        Column {
            GuessedCard(
                modifier = Modifier.padding(vertical = 20.dp),
                description = "GATO",
                words = "MIAU, ARENA, PELUDO, TOM",
                color = CColor.red
            )
            GuessedCard(
                modifier = Modifier.padding(vertical = 20.dp),
                description = "GATO",
                words = "MIAU, ARENA, PELUDO, TOM",
                color = CColor.green,
                sizeType = GameCardsContracts.Tablet
            )
        }
    }
}