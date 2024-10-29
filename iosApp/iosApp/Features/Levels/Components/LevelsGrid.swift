//
//  LevelsGrid.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

let levelsSpacingGrid = 20.0
let levelsSizeGrid = levelCardSize + levelsSpacingGrid
let levelsSizePrevButton = 2 * levelsSizeGrid

struct LevelsGrid: View {
    @EnvironmentObject var vm: LevelsViewModel

    let columns = [
        GridItem(
            .adaptive(
                minimum: levelsSizeGrid,
                maximum: levelsSizeGrid
            )
        ),
        GridItem(
            .adaptive(
                minimum: levelsSizeGrid,
                maximum: levelsSizeGrid
            )
        ),
    ]

    func mapLevelCardType(state: GameState) -> LevelCardType {
        switch state {
        case GameState.win:
            return .Win
        case GameState.lost:
            return .Lost
        case GameState.free:
            return .Unlock
        case GameState.lock:
            return .Lock
        case GameState.playing:
            return .Playing
        default:
            return .Lock
        }
    }

    var body: some View {
        let bottomCardLoading = vm.state.reachLastPage ? 0 : 1
        let count = vm.state.reachLastPage ? vm.state.games.count : vm.state.games.count + 1
        return ScrollViewReader { scroll in
            ZStack(
                alignment: .bottom
            ) {
                ScrollView {
                    VStack {
                        // btn previous page
                        if !vm.state.reachFirstPage {
                            CButton(
                                width: levelsSizePrevButton,
                                svg: .arrowUp,
                                key: "levels_previous_levels_btn",
                                onTap: {
                                    vm.sendEvent(event: .previousPage)
                                }
                            )
                            .padding([.top, .bottom], cSpace8)
                        }

                        // grid levels
                        LazyVGrid(
                            columns: columns,
                            spacing: levelsSpacingGrid
                        ) {
                            ForEach(
                                0 ..< count,
                                id: \.self
                            ) { idx in
                                // btns loading and levels card
                                if bottomCardLoading == 1 && idx == vm.state.games.count {
                                    LevelCard(
                                        isLoading: true,
                                        cardData: LevelCardData(type: .Lock)
                                    )
                                    .onAppear {
                                        vm.sendEvent(event: .nextPage)
                                    }
                                } else {
                                    let game = vm.state.games[idx]
                                    LevelCard(
                                        cardData: LevelCardData(
                                            type: mapLevelCardType(state: game.gameState),
                                            level: String(game.id),
                                            livesReward: game.livesEarnedFormatted
                                        ),
                                        onTap: {
                                            vm.sendEvent(event: .goToLevel(gameId: Int(game.id)))
                                        }
                                    )
                                    .onAppear {
                                        if game.id == vm.state.playingRowId {
                                            vm.sendEvent(event: .onVisibilityChangePlayingCard(show: true))
                                        }
                                    }
                                    .onDisappear {
                                        if game.id == vm.state.playingRowId {
                                            vm.sendEvent(event: .onVisibilityChangePlayingCard(show: false))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                .onAppear {
                    if !vm.state.initialScrollDone {
                        scroll.scrollTo(vm.state.initialScrollIdx, anchor: .top)
                        vm.sendEvent(event: .initialScrollDone)
                    }
                }
                // btn go to last played
                if !vm.state.playingRowIsShowing {
                    LevelFloatingButton(
                        onTap: { scroll.scrollTo(vm.state.playingRowIdx, anchor: .top) }
                    )
                }
            }
        }
    }
}
