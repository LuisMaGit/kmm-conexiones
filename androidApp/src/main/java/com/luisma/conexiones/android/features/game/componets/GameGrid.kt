package com.luisma.conexiones.android.features.game.componets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.components.game_cards.GameCardsContracts
import com.luisma.conexiones.android.core_ui.components.game_cards.GuessedCard
import com.luisma.conexiones.android.core_ui.components.game_cards.WordCard
import com.luisma.conexiones.android.core_ui.helpers.getColorByDomainColor
import com.luisma.conexiones.android.core_ui.helpers.getColorCoreByDifficulty
import com.luisma.conexiones.models.game.GameGridRowState

@Composable
fun GameGrid(
    modifier: Modifier = Modifier,
    cardType: GameCardsContracts,
    canSelectCards: Boolean,
    onTapCard: (column: Int, row: Int) -> Unit,
    gridRowsState: Map<Int, GameGridRowState>,
) {

    fun verticalOffset(row: Int): Dp {
        if (row == 0) {
            return 0.dp
        }
        return (cardType.cardHeight + cardType.cardSpacing) * row
    }

    fun horizontalOffset(column: Int): Dp {
        if (column == 0) {
            return 0.dp
        }

        return (cardType.wordCardWidth + cardType.cardSpacing) * column
    }


    Box(
        modifier = modifier,
    ) {
        gridRowsState.forEach { (rowIdx, rowData) ->
            if (rowData.solved) {

                GuessedCard(
                    modifier = Modifier.offset(
                        x = 0.dp,
                        y = verticalOffset(rowIdx)
                    ),
                    description = rowData.solvedTile.description,
                    words = rowData.solvedTile.wordsJoined,
                    color = getColorByDomainColor(getColorCoreByDifficulty(rowData.solvedTile.difficulty)),
                    sizeType = cardType,
                )
            } else {
                rowData.distribution.forEachIndexed { columnIdx, dist ->
                    WordCard(
                        modifier = Modifier.offset(
                            x = horizontalOffset(columnIdx),
                            y = verticalOffset(rowIdx),
                        ),
                        text = rowData.wordsRow[columnIdx],
                        selected = dist.selected,
                        onTap = onTapCard,
                        disabled = !dist.selected && !canSelectCards,
                        column = dist.should.column,
                        row = dist.should.row,
                        sizeType = cardType,
                    )
                }
            }
        }
    }
}