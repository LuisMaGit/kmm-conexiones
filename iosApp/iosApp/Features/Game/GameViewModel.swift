//
//  GameViewModel.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 10/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

class GameViewModel: ObservableObject {
    @Published public private(set) var state: GameViewState

    private let gamePlayService: GamePlayService
    private let gameSelectionService: GameSelectionService
    private let userProfileService: IUserProfileService
    private let numbUtilsService: INumbUtilsService
    private let gameAnimationService: IGameAnimationService
    private let router = Router.instance

    init(
        state: GameViewState = .init(),
        gamePlayService: GamePlayService = ServicesLocatorKt.gamePlayService(),
        gameSelectionService: GameSelectionService = ServicesLocatorKt.gameSelectionService(),
        userProfileService: IUserProfileService = ServicesLocatorKt.userProfileService(),
        numbUtilsService: INumbUtilsService = ServicesLocatorKt.numbUtilsService(),
        gameAnimationService: IGameAnimationService = ServicesLocatorKt.gameAnimationService()
    ) {
        self.state = state
        self.gamePlayService = gamePlayService
        self.gameSelectionService = gameSelectionService
        self.userProfileService = userProfileService
        self.numbUtilsService = numbUtilsService
        self.gameAnimationService = gameAnimationService
    }

    func sendEvent(event: GameViewEvents) {
        switch event {
        case .onAppear(gameId: let gameId):
            onAppear(gameId: gameId)
        case .selectWord(column: let column, row: let row):
            selectWord(column: column, row: row)
        case .dismissAnimation(column: let column, row: let row):
            dismissAnimation(column: column, row: row)
        case .dismissTileAnimation(row: let row):
            dissmissTileAnimation(row: row)
        case .dismissOnDoneAnimation:
            dismissOnDoneAnimation()
        case .dismissLivesAnimation:
            dismissLivesAnimation()
        case .goToNextLevel(nextLevel: let nextLevel):
            goToNextLevel(nextLevel: nextLevel)
        case .mixGame:
            mixGame()
        case .clearSelection:
            clearSelection()
        case .onBack:
            onBack()
        case .submit:
            submit()
        case .moreLives:
            moreLives()
        }
    }

    private func mapGridRowStateFromKt(_ kotlinGridRow: [KotlinInt: GameGridRowState]) -> [Int: GameGridRowState] {
        var gridRowsState: [Int: GameGridRowState] = [:]
        for idx in 0 ..< kotlinGridRow.count {
            gridRowsState[idx] = kotlinGridRow[KotlinInt(integerLiteral: idx)]
        }
        return gridRowsState
    }

    private func mapGridRowState2Kt(_ kotlinGridRow: [Int: GameGridRowState]) -> [KotlinInt: GameGridRowState] {
        var gridRowsState: [KotlinInt: GameGridRowState] = [:]
        for idx in 0 ..< kotlinGridRow.count {
            gridRowsState[KotlinInt(integerLiteral: idx)] = kotlinGridRow[idx]
        }
        return gridRowsState
    }

    private func onAppear(gameId: Int) {
        guard gameId != state.gameId || gameId != -1 else {
            return
        }

        startGame(gameId: gameId)
    }

    private func startGame(gameId: Int) {
        Task {
            async let gameResp = gamePlayService.getGame(gameId: Int32(gameId))
            async let livesResp = userProfileService.getLives()

            let game = try await gameResp
            let lives = try await livesResp

            await MainActor.run {
                state.gameId = gameId
                state.screenState = .success
                state.gameData = game.gameData
                state.gridRowsState = mapGridRowStateFromKt(game.gridRowsState)
                state.lives = Int(truncating: lives)
                state.livesEarnedOnDone = Int(game.livesEarnedOnDone)
                state.nextGameId = Int(game.nextGameId)
            }
        }
    }

    private func mixGame() {
        Task {
            let game = try await gamePlayService.mixGame(
                gameId: Int32(state.gameId),
                gameDistribution: state.gameData.gameDistribution,
                currentSelection: state.currentSelection
            )
            await MainActor.run {
                state.gameData = game.gameData
                state.gridRowsState = mapGridRowStateFromKt(game.gridRowsState)
                state.currentSelection = game.selection
            }
        }
    }

    private func selectWord(column: Int, row: Int) {
        let result = gameSelectionService.select(
            gridRowState: mapGridRowState2Kt(state.gridRowsState),
            currentSelection: state.currentSelection,
            newColumn: Int32(column),
            newRow: Int32(row)
        )
        Task {
            await MainActor.run {
                state.gridRowsState = mapGridRowStateFromKt(result.gridRowState)
                state.currentSelection = result.newSelection
                state.selectFailed = false
            }
        }
    }

    private func clearSelection() {
        let result = gameSelectionService.clearSelection(
            gridRowState: mapGridRowState2Kt(state.gridRowsState),
            currentSelection: state.currentSelection
        )

        Task {
            await MainActor.run {
                if let result = result {
                    state.gridRowsState = mapGridRowStateFromKt(result.gridRowState)
                    state.currentSelection = result.newSelection
                }
            }
        }
    }

    private func submit() {
        Task {
            let response = try await gamePlayService.submitSelection(
                gameId: Int32(state.gameId),
                gameDistribution: state.gameData.gameDistribution,
                currentSelection: state.currentSelection
            )

            await MainActor.run {
                var livesEarnedOnDone = state.livesEarnedOnDone
                var lives = state.lives

                if response.gameData.gameState == GameState.win ||
                    response.gameData.gameState == GameState.lost
                {
                    livesEarnedOnDone = Int(response.livesEarnedOnDone)
                    lives = Int(response.totalLivesAfterOnDone)
                }

                var notSolvedRowsOnLost: [Int: Int] = [:]
                response.notSolvedRowsOnLostIdx.forEach { key in
                    let keyInt = Int(truncating: key)
                    notSolvedRowsOnLost[keyInt] = Int(
                        truncating: response.notSolvedRowsOnLost[KotlinInt(integerLiteral: keyInt)] ?? 0
                    )
                }
                
                let gameData = response.gameData
                
                state.gameData = gameData
                state.gridRowsState = mapGridRowStateFromKt(response.gridRowsState)
                state.currentSelection = response.selection
                state.selectFailed = !response.rowGuessedOnSubmit
                state.livesEarnedOnDone = livesEarnedOnDone
                state.lives = lives
                state.nextGameId = Int(response.nextGameId)
                state.notSolvedRowsOnLost = notSolvedRowsOnLost
                state.showOnDoneSignAnimation = gameData.gameState == .win || gameData.gameState == .lost
                state.showOnDoneLivesAnimation = gameData.gameState == .win
            }
        }
    }

    private func goToNextLevel(nextLevel: Int) {
        state = .init()

        startGame(gameId: nextLevel)
    }

    func dismissAnimation(column: Int, row: Int) {
        let gridRowsState = gameAnimationService.toggleWordAnimation(
            gridRowState: mapGridRowState2Kt(state.gridRowsState),
            column: Int32(column),
            row: Int32(row),
            animationType: .noanimation
        )

        state.gridRowsState = mapGridRowStateFromKt(gridRowsState)
    }

    func dissmissTileAnimation(row: Int) {
        let gridRowsState = gameAnimationService.toggleTilesAnimation(
            gridRowState: mapGridRowState2Kt(state.gridRowsState),
            rows: [KotlinInt(integerLiteral: row)],
            animationType: .noanimation
        )

        state.gridRowsState = mapGridRowStateFromKt(gridRowsState)
    }

    func dismissOnDoneAnimation() {
        if !state.showOnDoneSignAnimation {
            return
        }

        state.showOnDoneSignAnimation = false
    }

    func dismissLivesAnimation() {
        if !state.showOnDoneLivesAnimation {
            return
        }

        state.showOnDoneLivesAnimation = false
    }

    private func onBack() {
        Task {
            await router.pop()
        }
    }

    private func moreLives() {
        Task {
            await router.goTo(page: .MockAdd)
        }
    }
}
