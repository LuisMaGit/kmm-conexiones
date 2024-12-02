package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.components.game_cards.GameCardsContracts
import com.luisma.conexiones.android.core_ui.components.game_cards.GuessedCard
import com.luisma.conexiones.android.core_ui.components.game_cards.GuessedCardAnimationType
import com.luisma.conexiones.android.core_ui.components.game_cards.WordCard
import com.luisma.conexiones.android.core_ui.components.game_cards.WordCardAnimationType
import com.luisma.conexiones.android.core_ui.helpers.getColorByDomainColor
import com.luisma.conexiones.android.core_ui.helpers.getColorCoreByDifficulty
import com.luisma.conexiones.models.game.GameGridRowState
import com.luisma.conexiones.models.game.GameTileAnimationType
import com.luisma.conexiones.models.game.GameWordAnimationType

@Composable
fun GameGrid(
    modifier: Modifier = Modifier,
    cardType: GameCardsContracts,
    canSelectCards: Boolean,
    onTapCard: (column: Int, row: Int) -> Unit,
    dismissWordCardAnimation: (column: Int, row: Int) -> Unit,
    dismissTileCardAnimation: (row: Int) -> Unit,
    gridRowsState: Map<Int, GameGridRowState>,
    notSolvedRowsOnLost: Map<Int, Int>
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

    fun mapWordCardAnimation(animation: GameWordAnimationType): WordCardAnimationType {
        return when (animation) {
            GameWordAnimationType.NoAnimation -> WordCardAnimationType.NoAnimation
            GameWordAnimationType.Tap -> WordCardAnimationType.Scale
            GameWordAnimationType.Fail -> WordCardAnimationType.TranslateX
        }
    }

    fun mapGuessedCardAnimation(animation: GameTileAnimationType): GuessedCardAnimationType {
        return when (animation) {
            GameTileAnimationType.NoAnimation -> GuessedCardAnimationType.NoAnimation
            GameTileAnimationType.Reveal -> GuessedCardAnimationType.Scale
        }
    }

    Box(
        modifier = modifier,
    ) {
        gridRowsState.forEach { (rowIdx, rowData) ->
            rowData.distribution.forEachIndexed { columnIdx, dist ->
                WordCard(
                    modifier = Modifier.offset(
                        x = horizontalOffset(columnIdx),
                        y = verticalOffset(rowIdx),
                    ),
                    text = rowData.wordsRow[columnIdx],
                    selected = dist.selected,
                    onTap = onTapCard,
                    disabled = rowData.solved || (!dist.selected && !canSelectCards),
                    column = columnIdx,
                    row = rowIdx,
                    sizeType = cardType,
                    dismissAnimation = dismissWordCardAnimation,
                    animationType = mapWordCardAnimation(dist.should.animationType)
                )
            }
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
                    row = rowIdx,
                    dismissAnimation = dismissTileCardAnimation,
                    animationType = mapGuessedCardAnimation(rowData.tileAnimationType),
                    scaleOrder = if (notSolvedRowsOnLost.isEmpty() || notSolvedRowsOnLost[rowIdx] == null) {
                        0
                    } else {
                        notSolvedRowsOnLost[rowIdx]!!
                    }
                )
            }
        }
    }
}