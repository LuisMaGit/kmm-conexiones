package com.luisma.conexiones.services.mocks

import com.luisma.conexiones.models.game.GameAnimationType
import com.luisma.conexiones.models.game.GameGridRowState
import com.luisma.conexiones.services.game.IGameAnimationService

class GameAnimationMockService : IGameAnimationService {
    override fun toggleAnimation(
        gridRowState: Map<Int, GameGridRowState>,
        column: Int,
        row: Int,
        animationType: GameAnimationType
    ): Map<Int, GameGridRowState> {
        TODO("Not yet implemented")
    }
}


fun gameAnimationMockService() : IGameAnimationService {
    return GameAnimationMockService()
}