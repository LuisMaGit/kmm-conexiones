package com.luisma.conexiones.android.core_ui.components.game_cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4


@Composable
fun WordCard(
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    sizeType: GameCardsContracts = GameCardsContracts.Mobile,
    text: String,
    selected: Boolean,
    onTap: (column: Int, row: Int) -> Unit,
    column: Int,
    row: Int,
) {
    val color = CTheme.colors
    val baseModifier = modifier
        .size(width = sizeType.wordCardWidth, height = sizeType.cardHeight)
        .clip(RoundedCornerShape(cBorderRadius4))
        .background(
            if (selected) {
                color.textDefault
            } else {
                color.softContrastScreenBackground
            }
        )
    val optionalModifier = if (disabled) baseModifier else
        baseModifier.clickable { onTap(column, row) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = optionalModifier
    ) {
        CText(
            text = text,
            fontSize = sizeType.wordCardFontSize,
            color = if (selected) {
                color.softContrastScreenBackground
            } else {
                color.textDefault
            },
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
        )
    }
}

@Preview
@Composable
private fun WordCardPrev() {
    CThemeProvider {
        Column {
            WordCard(
                text = "CAT",
                selected = false,
                onTap = { _, _ -> },
                column = 0,
                row = 0
            )
            Box(modifier = Modifier.size(40.dp))
            WordCard(
                text = "SUPER CAT",
                selected = true,
                onTap = { _, _ -> },
                column = 0,
                row = 0
            )
            Box(modifier = Modifier.size(40.dp))
            WordCard(
                text = "CAT",
                sizeType = GameCardsContracts.Tablet,
                selected = false,
                onTap = { _, _ -> },
                column = 0,
                row = 0
            )
            Box(modifier = Modifier.size(40.dp))
            WordCard(
                text = "SUPER CAT",
                sizeType = GameCardsContracts.Tablet,
                selected = true,
                onTap = { _, _ -> },
                column = 0,
                row = 0
            )
        }

    }
}