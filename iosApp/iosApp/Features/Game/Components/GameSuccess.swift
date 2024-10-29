//
//  GameSuccess.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 10/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct GameSuccess: View {
    @EnvironmentObject var vm: GameViewModel

    func getWordCardSize(_ breakPoint: ScreenSizeBreakPoints) -> GameCardsContractsModel {
        switch breakPoint {
        case .mobile:
            return gameCardContracts(GameCardContracts.Mobile)
        case .tablet:
            return gameCardContracts(GameCardContracts.Tablet)
        }
    }

    var body: some View {
        ScreenSizeHelper { breakPoint, _ in
            let wordCardSize = getWordCardSize(breakPoint)
            let gameWordAmount = Double(GameContractsKt.GAME_WORD_AMOUNT)
            let gridWidth = wordCardSize.wordCardWidth * gameWordAmount + (wordCardSize.cardSpacing * gameWordAmount)
            let gridHeight = wordCardSize.cardHeight * gameWordAmount + (wordCardSize.cardSpacing * gameWordAmount) + cSpace24

            return ScrollView {
                VStack(
                    alignment: .leading,
                    spacing: 0
                ) {
                    GameTitle(level: vm.state.gameId)
                    GameGrid(
                        cardSize: wordCardSize
                    )
                    .padding([.top], cSpace24)
                    .frame(
                        width: gridWidth,
                        height: gridHeight
                    )
                    if vm.state.gameData.gameState == GameState.playing {
                        GameErrors()
                            .frame(width: gridWidth)
                            .padding([.bottom, .top], cSpace32)
                        GameControls(
                            gridWidth: gridWidth
                        )
                    }
                    GameDoneState()
                        .padding([.top], cSpace40)
                        .frame(width: gridWidth)
                    GameDoneLives()
                        .padding([.top], cSpace16)
                        .frame(width: gridWidth)
                    if vm.state.showButtonsOnDone {
                        Group {
                            if vm.state.lives == 0 {
                                GameDoneMoreLives()
                            } else {
                                CButton(
                                    color: CColors.orange,
                                    svg: .arrowRight,
                                    key: "reusable_level \(Int(vm.state.nextGameId), specifier: "%d")",
                                    onTap: {
                                        vm.sendEvent(event: .goToNextLevel(nextLevel: vm.state.nextGameId))
                                    }
                                )
                            }
                        }
                        .padding([.top], cSpace40)
                        .frame(width: gridWidth)
                    }
                }
            }
        }
    }
}


