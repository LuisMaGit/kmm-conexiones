package com.luisma.conexiones.services.game

import com.luisma.conexiones.models.game.GameGridRowState
import com.luisma.conexiones.models.game.GameTileAnimationType
import com.luisma.conexiones.models.game.GameWordAnimationType


interface IGameAnimationService {
    fun toggleWordAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        column: Int,
        row: Int,
        animationType: GameWordAnimationType
    ): Map<Int, GameGridRowState>

    fun toggleTilesAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        rows: List<Int>,
        animationType: GameTileAnimationType
    ): Map<Int, GameGridRowState>
}

class GameAnimationService : IGameAnimationService {

    override fun toggleWordAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        column: Int,
        row: Int,
        animationType: GameWordAnimationType,
    ): Map<Int, GameGridRowState> {
        val output = mutableMapOf<Int, GameGridRowState>()

        gridRowState.forEach { mapEntry ->
            val newDistribution = mapEntry.value.distribution.map { it }.toMutableList()
            mapEntry.value.distribution.forEachIndexed { idx, dist ->
                val founded = dist.should.column == column && dist.should.row == row
                if (founded) {
                    newDistribution[idx] = newDistribution[idx].copy(
                        should = newDistribution[idx].should.copy(
                            animationType = animationType
                        )
                    )
                }
            }
            output[mapEntry.key] = mapEntry.value.copy(
                distribution = newDistribution
            )
        }

        return output
    }

    override fun toggleTilesAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        rows: List<Int>,
        animationType: GameTileAnimationType
    ): Map<Int, GameGridRowState> {
        val output = mutableMapOf<Int, GameGridRowState>()
        gridRowState.forEach { grid ->
            val row = rows.firstOrNull { r -> r == grid.key }
            if (row != null) {
                output[grid.key] = grid.value.copy(tileAnimationType = animationType)
            } else {
                output[grid.key] = grid.value
            }
        }

        return output
    }
}

