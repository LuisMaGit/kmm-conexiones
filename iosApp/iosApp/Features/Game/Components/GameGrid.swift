//
//  GameGrid.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 11/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct GameGrid: View {
    @EnvironmentObject var vm: GameViewModel

    let cardSize: GameCardsContractsModel

    var body: some View {
        func verticalOffset(row: Int) -> Double {
            if row == 0 {
                return 0
            }

            return (cardSize.cardHeight + cardSize.cardSpacing) * Double(row)
        }

        func horizontalOffset(column: Int) -> Double {
            if column == 0 {
                return 0
            }

            return (cardSize.wordCardWidth + cardSize.cardSpacing) * Double(column)
        }

        func mapWordCardAnimation(_ animation: GameWordAnimationType) -> WordCarAnimationType {
            switch animation {
            case GameWordAnimationType.noanimation:
                return WordCarAnimationType.noAnimation
            case GameWordAnimationType.tap:
                return WordCarAnimationType.scale
            case GameWordAnimationType.fail:
                return WordCarAnimationType.jump
            default:
                return WordCarAnimationType.noAnimation
            }
        }

        func mapGuessedCardAnimation(_ animation: GameTileAnimationType) -> GuessedCardAnimationType {
            switch animation {
            case GameTileAnimationType.noanimation:
                return GuessedCardAnimationType.noAnimation
            case GameTileAnimationType.reveal:
                return GuessedCardAnimationType.scale
            default:
                return GuessedCardAnimationType.noAnimation
            }
        }

        return ZStack(
            alignment: .topLeading
        ) {
            Color.clear
            ForEach(
                Array(vm.state.gridRowsState.keys),
                id: \.self
            ) { rowIdx in
                let rowData = vm.state.gridRowsState[rowIdx]!
                let distribution = rowData.distribution

                ForEach(
                    0 ..< distribution.count,
                    id: \.self
                ) { columnIdx in
                    let dist = distribution[columnIdx]
                    return WordCard(
                        disabled: !dist.selected && !vm.state.canSelectWords,
                        sizeType: cardSize.contract,
                        text: rowData.wordsRow[columnIdx],
                        selected: dist.selected,
                        onTap: { column, row in
                            vm.sendEvent(event: .selectWord(column: column, row: row))
                        },
                        column: columnIdx,
                        row: rowIdx,
                        animationType: mapWordCardAnimation(dist.should.animationType),
                        dismissAnimation: { column, row in
                            vm.sendEvent(event: .dismissAnimation(column: column, row: row))
                        }
                    )
                    .offset(
                        x: horizontalOffset(column: columnIdx),
                        y: verticalOffset(row: rowIdx)
                    )
                }

                if rowData.solved {
                    GuessedCard(
                        row: rowIdx,
                        description: rowData.solvedTile.description_,
                        words: rowData.solvedTile.wordsJoined,
                        color: getColorByDomainColor(color: getColorByDifficulty(difficulty: Int(rowData.solvedTile.difficulty))),
                        sizeType: cardSize.contract,
                        animationType: mapGuessedCardAnimation(rowData.tileAnimationType),
                        scaleOrder: (vm.state.notSolvedRowsOnLost.isEmpty || vm.state.notSolvedRowsOnLost[rowIdx] == nil ? 0 : vm.state.notSolvedRowsOnLost[rowIdx]) ?? 0,
                        dissmissAnimation: { row in vm.sendEvent(event: .dismissTileAnimation(row: row)) }
                    )
                    .offset(
                        x: 0,
                        y: verticalOffset(row: rowIdx)
                    )
                }
            }
        }
    }
}
