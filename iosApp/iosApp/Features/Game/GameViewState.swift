//
//  GameViewState.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct GameViewState {
    var screenState: BasicScreenState
    var gameData: GameData
    var gameId: Int
    var gridRowsState: [Int: GameGridRowState]
    var currentSelection: [GameDistributionCoordinates]
    var lives: Int
    var selectFailed: Bool
    var livesEarnedOnDone: Int
    var nextGameId: Int
    var notSolvedRowsOnLost: [Int: Int]
    var showOnDoneSignAnimation: Bool
    var showOnDoneLivesAnimation: Bool

    init(
        screenState: BasicScreenState = .loading,
        gameData: GameData = GameData.companion.initial(),
        gameId: Int = -1,
        gridRowsState: [Int: GameGridRowState] = [:],
        currentSelection: [GameDistributionCoordinates] = [],
        lives: Int = -1,
        selectFailed: Bool = false,
        livesEarnedOnDone: Int = 0,
        nextGameId: Int = -1,
        notSolvedRowsOnLost: [Int: Int] = [:],
        showOnDoneSignAnimation: Bool = false,
        showOnDoneLivesAnimation: Bool = false
    ) {
        self.screenState = screenState
        self.gameData = gameData
        self.gameId = gameId
        self.gridRowsState = gridRowsState
        self.currentSelection = currentSelection
        self.lives = lives
        self.selectFailed = selectFailed
        self.livesEarnedOnDone = livesEarnedOnDone
        self.nextGameId = nextGameId
        self.notSolvedRowsOnLost = notSolvedRowsOnLost
        self.showOnDoneSignAnimation = showOnDoneSignAnimation
        self.showOnDoneLivesAnimation = showOnDoneLivesAnimation
    }

    var canSelectWords: Bool {
        gameData.gameState == GameState.playing && currentSelection.count != GameContractsKt.GAME_WORD_AMOUNT
    }

    var canDeselectWords: Bool {
        gameData.gameState == GameState.playing && currentSelection.isEmpty
    }

    var canSendRow: Bool {
        gameData.gameState == GameState.playing && !selectFailed && currentSelection.count == GameContractsKt.GAME_WORD_AMOUNT
    }

    var showButtonsOnDone: Bool {
        let state = gameData.gameState

        if (state != .win && state != .lost) || nextGameId == -1 {
            return false
        }

        return state == .win ? !showOnDoneSignAnimation && !showOnDoneLivesAnimation : !showOnDoneSignAnimation
    }
}
