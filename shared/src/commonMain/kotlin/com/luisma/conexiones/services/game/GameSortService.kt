package com.luisma.conexiones.services.game

import com.luisma.conexiones.contracts.GAME_WORD_AMOUNT
import com.luisma.conexiones.models.game.GameData
import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.models.game.GameGridRowState
import com.luisma.conexiones.models.game.GameState
import com.luisma.conexiones.services.INumbUtilsService
import kotlin.math.sign

class GameSortService(
    private val numbUtilsService: INumbUtilsService,
    private val columns: Int = GAME_WORD_AMOUNT,
    private val rows: Int = GAME_WORD_AMOUNT,
) {

    private fun getColumnAndRowByIndex(
        index: Int,
        columnCount: Int
    ): Pair<Int, Int> {
        // 4x4 exp:
        // 5  1  3  12
        // 9  8  0  15
        // 10 14 4  7
        // 11 13 6  2

        // [15] -> 7 - 4 (row) = 3 (colum), 1 (row)
        // [7] -> 11 - 4 (row) - 4 (row) = 3 (colum), 2 (row)
        // [2] -> 15 - 4 (row) - 4 (row) - 4 (row) = 3 (colum), 3 (row)
        // [12] -> 3 - 4 = -1 -> 3 (column) - 0 (row)

        val columns = List(size = columnCount) { it }

        var multiply = 1
        var row = 0
        var column = 0
        do {
            val position = index - (multiply * columnCount)
            if (position.sign == -1) {
                column = index
                row = 0
            } else {
                val potentialColumn = columns.firstOrNull { it == position }
                if (potentialColumn != null) {
                    column = potentialColumn
                    row = multiply
                } else {
                    multiply++
                }
            }
        } while (position.sign != -1 && !columns.contains(position))

        return Pair(column, row)
    }

    fun getRowFromDistribution(
        row: Int,
        distribution: List<GameDistribution>,
    ): List<GameDistribution> {
        if (distribution.isEmpty()) {
            return emptyList()
        }
        val fromIdx = columns * row
        val toIdx = fromIdx + columns
        return distribution.subList(fromIndex = fromIdx, toIndex = toIdx)
    }

    fun mixGameDistribution(
        gameDistribution: List<GameDistribution>,
        columnCount: Int = columns,
        rowCount: Int = rows,
    ): List<GameDistribution> {

        // 1- loop over the sorted list (X), for each item,
        // 2- find the zero, one, etc (Y) in the unsorted list
        // 3- find the idx of Y
        // 4- make the math depending of the idx of Y to extract it's new col and row
        // 5- X has a new value for the "its" object

        val unsortedList = numbUtilsService.unsortedListWithSize(
            size = columnCount * rowCount
        )

        val output = mutableListOf<GameDistribution>()
        gameDistribution.forEachIndexed { idxSortedGame, distribution ->
            val idxUnsorted = unsortedList.indexOf(idxSortedGame)
            val (column, row) = getColumnAndRowByIndex(
                index = idxUnsorted,
                columnCount = columnCount,
            )
            output.add(
                GameDistribution(
                    should = distribution.should,
                    its = GameDistributionCoordinates(
                        column = column,
                        row = row
                    )
                )
            )
        }

        return output
    }

    fun resolveGridRowState(
        gameData: GameData
    ): Map<Int, GameGridRowState> {
        val output = mutableMapOf<Int, GameGridRowState>()
        gameData.gameTiles.forEachIndexed { row, tile ->
            val solved = gameData.gameState == GameState.Win ||
                    gameData.gameState == GameState.Lost ||
                    gameData.guessedDifficulty.contains(row)
            val rowDistribution = getRowFromDistribution(
                row = row,
                distribution = gameData.gameDistribution
            )
            val wordsRow = mutableListOf<String>()
            for (column in 0..<columns) {
                val idx = gameData.gameDistribution.indexOfFirst { dist ->
                    dist.its.column == column && dist.its.row == row
                }
                val (x, y) = getColumnAndRowByIndex(index = idx, columnCount = columns)
                wordsRow.add(gameData.gameTiles[y].words[x])
            }

            output[row] = GameGridRowState(
                solved = solved,
                solvedTile = tile,
                distribution = rowDistribution,
                wordsRow = wordsRow
            )
        }

        return output
    }

    fun fullGameListSorted(): List<GameDistribution> {
        val output = mutableListOf<GameDistribution>()
        List(rows) { row ->
            List(columns) { column ->
                val coordinates = GameDistributionCoordinates(column = column, row = row)
                output.add(GameDistribution(should = coordinates, its = coordinates))
            }
        }
        return output
    }

    fun extractMixedDistributionAndRows(
        gameDistribution: List<GameDistribution>
    ): Pair<List<Int>, List<GameDistribution>> {
        val toMixDistribution = mutableListOf<GameDistribution>()
        val mixedRows = mutableListOf<Int>()
        for (row in 0..<rows) {
            val fromIdx = columns * row
            val toIdx = fromIdx + columns
            val rowDistribution = gameDistribution.subList(
                fromIndex = fromIdx,
                toIndex = toIdx
            )
            if (!rowDistribution.all { dist ->
                    dist.should.column == dist.its.column &&
                            dist.should.row == dist.its.row
                }) {
                toMixDistribution.addAll(rowDistribution)
                mixedRows.add(row)
            }
        }
        return Pair(mixedRows, toMixDistribution)
    }

    fun mixOnlyNotSortedDistributions(
        toMixRows: List<Int>,
        toMixDistribution: List<GameDistribution>
    ): List<GameDistribution> {

        val sortedDistribution = fullGameListSorted()
        if (toMixRows.isEmpty()) {
            return sortedDistribution
        }

        val mixedDistributionWithoutRowsOffset = mixGameDistribution(
            gameDistribution = toMixDistribution,
            rowCount = toMixRows.count(),
            columnCount = columns
        )

        // the mix is only applied to the toMixRows, then the result of
        // a 4x2 matrix will only reflect the rows from this matrix,
        // so rows will only be 0 and 1, here we make sure the row gets the full matrix offset
        val mixGameDistribution = mutableListOf<GameDistribution>()
        mixedDistributionWithoutRowsOffset.forEach { dist ->
            mixGameDistribution.add(
                dist.copy(
                    its = dist.its.copy(
                        row = toMixRows[dist.its.row]
                    )
                )
            )
        }

        val newDistribution = mutableListOf<GameDistribution>()
        var mixedCounter = 0
        for (row in 0..<rows) {
            if (toMixRows.contains(row)) {
                val fromIdx = columns * mixedCounter
                val toIdx = fromIdx + columns
                newDistribution.addAll(
                    mixGameDistribution.subList(
                        fromIndex = fromIdx,
                        toIndex = toIdx,
                    )
                )
                mixedCounter++
            } else {
                val fromIdx = columns * row
                val toIdx = fromIdx + columns
                newDistribution.addAll(
                    sortedDistribution.subList(
                        fromIndex = fromIdx,
                        toIndex = toIdx
                    )
                )
            }
        }

        return newDistribution
    }

    fun mixGame(
        gameDistribution: List<GameDistribution>
    ): List<GameDistribution> {
        val (mixedRows, toMixDistribution) = extractMixedDistributionAndRows(
            gameDistribution
        )
        return mixOnlyNotSortedDistributions(
            toMixRows = mixedRows,
            toMixDistribution = toMixDistribution
        )
    }

}