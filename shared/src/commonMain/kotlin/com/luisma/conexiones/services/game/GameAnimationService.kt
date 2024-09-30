package com.luisma.conexiones.services.game

import com.luisma.conexiones.models.game.GameAnimationType
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.models.game.GameGridRowState


interface IGameAnimationService {
    fun toggleAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        column: Int,
        row: Int,
        animationType: GameAnimationType
    ): Map<Int, GameGridRowState>
}

class GameAnimationService : IGameAnimationService {

    override fun toggleAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        column: Int,
        row: Int,
        animationType: GameAnimationType,
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
}

