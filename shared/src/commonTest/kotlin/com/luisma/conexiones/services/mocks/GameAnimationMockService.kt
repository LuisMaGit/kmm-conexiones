package com.luisma.conexiones.services.mocks

import com.luisma.conexiones.models.game.GameGridRowState
import com.luisma.conexiones.models.game.GameTileAnimationType
import com.luisma.conexiones.models.game.GameWordAnimationType
import com.luisma.conexiones.services.game.IGameAnimationService

class GameAnimationMockService : IGameAnimationService {
    override fun toggleWordAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        column: Int,
        row: Int,
        animationType: GameWordAnimationType
    ): Map<Int, GameGridRowState> {
        TODO("Not yet implemented")
    }

    override fun toggleTilesAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        rows: List<Int>,
        animationType: GameTileAnimationType
    ): Map<Int, GameGridRowState> {
        TODO("Not yet implemented")
    }

}


fun gameAnimationMockService() : IGameAnimationService {
    return GameAnimationMockService()
}