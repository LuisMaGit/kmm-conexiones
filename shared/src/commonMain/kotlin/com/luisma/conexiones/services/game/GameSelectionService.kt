package com.luisma.conexiones.services.game

import com.luisma.conexiones.contracts.GAME_WORD_AMOUNT
import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.models.game.GameGridRowState

class GameSelectionService(
    private val columns: Int = GAME_WORD_AMOUNT
) {

    fun select(
        gridRowState: Map<Int, GameGridRowState>,
        currentSelection: List<GameDistributionCoordinates>,
        newCoordinates: GameDistributionCoordinates
    ): GameSelectionSelectResponse {

        val newSelectedCoordinates = currentSelection.map { sel ->
            sel.copy()
        }.toMutableList()

        val idxNC = newSelectedCoordinates.indexOfFirst { new ->
            newCoordinates.row == new.row && newCoordinates.column == new.column
        }



        if (idxNC != -1) {
            newSelectedCoordinates.removeAt(idxNC)
        } else {
            newSelectedCoordinates.add(newCoordinates)
        }

        val newGrid = updateSelection(
            newSelectedCoordinates = newSelectedCoordinates,
            gridRowState = gridRowState
        )
        return GameSelectionSelectResponse(
            gridRowState = newGrid,
            newSelection = newSelectedCoordinates
        )
    }

    fun selectionAfterRotation(
        beforeRotationDistribution: List<GameDistribution>,
        afterRotationDistribution: List<GameDistribution>,
        currentSelection: List<GameDistributionCoordinates>,
    ): List<GameDistributionCoordinates> {
        val whoHasTheItsInThisShould = mutableListOf<GameDistributionCoordinates>()
        currentSelection.forEach { sel ->
            whoHasTheItsInThisShould.add(beforeRotationDistribution.first { it.its == sel }.should)
        }
        val shouldOfTheIts = mutableListOf<GameDistributionCoordinates>()
        whoHasTheItsInThisShould.forEach { sel ->
            shouldOfTheIts.add(afterRotationDistribution.first { it.should == sel }.its)
        }
        return shouldOfTheIts
    }

    fun updateSelection(
        newSelectedCoordinates: List<GameDistributionCoordinates>,
        gridRowState: Map<Int, GameGridRowState>,
    ): Map<Int, GameGridRowState> {
        val newGridRowState = mutableMapOf<Int, GameGridRowState>()
        newSelectedCoordinates.forEach { selected ->
            gridRowState.forEach { mapEntry ->
                val idxSelected = mapEntry.value.distribution.indexOfFirst { dist ->
                    dist.should == selected
                }

                val newDistribution = mapEntry.value.distribution.map { it }.toMutableList()
                if (idxSelected != -1) {
                    newDistribution[idxSelected] =
                        newDistribution[idxSelected].copy(selected = true)
                }
                newGridRowState[mapEntry.key] = mapEntry.value.copy(distribution = newDistribution)
            }
        }
        gridRowState.forEach { mapEntry ->
            val newDistribution = mapEntry.value.distribution.map { it }.toMutableList()
            mapEntry.value.distribution.forEachIndexed { idx, dist ->
                val founded = newSelectedCoordinates.find { sel -> sel == dist.should }
                if (!dist.selected && founded != null) {
                    newDistribution[idx] = newDistribution[idx].copy(selected = true)
                } else if (dist.selected && founded == null) {
                    newDistribution[idx] = newDistribution[idx].copy(selected = false)
                }
            }
            newGridRowState[mapEntry.key] = mapEntry.value.copy(
                distribution = newDistribution
            )
        }

        return newGridRowState
    }

    fun clearSelection(
        gridRowState: Map<Int, GameGridRowState>,
        currentSelection: List<GameDistributionCoordinates>,
    ): GameSelectionSelectResponse? {
        if (currentSelection.isEmpty()) {
            return null
        }

        val newGridRowState = mutableMapOf<Int, GameGridRowState>()
        gridRowState.forEach { mapEntry ->
            val newDistribution = mapEntry.value.distribution.map { it.copy(selected = false) }
            newGridRowState[mapEntry.key] = mapEntry.value.copy(distribution = newDistribution)
        }

        return GameSelectionSelectResponse(
            gridRowState = newGridRowState,
            newSelection = emptyList(),
        )
    }

    fun checkRowGuessed(
        currentDistribution: List<GameDistribution>,
        currentSelection: List<GameDistributionCoordinates>,
    ): Int {
        var row = -1
        for (idx in 0..<currentSelection.count() - 1) {
            val sel = currentSelection[idx]
            val nextSel = currentSelection[idx + 1]
            row = currentDistribution.first { dist -> dist.its == sel }.should.row
            val nextRow = currentDistribution.first { dist -> dist.its == nextSel }.should.row
            if (row != nextRow) {
                row = -1
                break
            }
        }
        return row
    }

    fun insertRowGuessedInDistribution(
        rowGuessed: Int,
        currentDistribution: List<GameDistribution>
    ): GameSelectionSubmitResponse {
        val rowGuessedDistributionSorted = List(columns) { col ->
            val dist = GameDistributionCoordinates(column = col, row = rowGuessed)
            GameDistribution(
                should = dist,
                its = dist
            )
        }

        val outputDistribution = mutableListOf<GameDistribution>()

        currentDistribution.forEach {
            outputDistribution.add(it)
        }

        rowGuessedDistributionSorted.forEach { wordGuessed ->
            val idxWhooping = outputDistribution.indexOfFirst {
                it.its == wordGuessed.should
            }
            val idxBig = outputDistribution.indexOfFirst {
                it.should == wordGuessed.should
            }
            val whopping = outputDistribution[idxWhooping]
            val big = outputDistribution[idxBig]
            outputDistribution[idxWhooping] = whopping.copy(its = big.its)
            outputDistribution[idxBig] = wordGuessed
        }

        return GameSelectionSubmitResponse(
            newDistribution = outputDistribution,
            rowGuessed = rowGuessed
        )
    }

    fun submitSelection(
        currentDistribution: List<GameDistribution>,
        currentSelection: List<GameDistributionCoordinates>,
    ): GameSelectionSubmitResponse {
        val rowGuessed = checkRowGuessed(
            currentDistribution = currentDistribution,
            currentSelection = currentSelection
        )

        if (rowGuessed == -1) {
            return GameSelectionSubmitResponse(
                newDistribution = emptyList(),
                rowGuessed = rowGuessed
            )
        }

        return insertRowGuessedInDistribution(
            rowGuessed = rowGuessed,
            currentDistribution = currentDistribution
        )
    }
}

data class GameSelectionSubmitResponse(
    val newDistribution: List<GameDistribution>,
    val rowGuessed: Int,
)

data class GameSelectionSelectResponse(
    val gridRowState: Map<Int, GameGridRowState>,
    val newSelection: List<GameDistributionCoordinates>,
)





