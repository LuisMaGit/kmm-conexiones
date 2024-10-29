//
//  LevelCard.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 19/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

let levelCardSize = 140.0

struct LevelCardData {
    let type: LevelCardType
    let level: String
    let livesReward: String

    init(
        type: LevelCardType,
        level: String = "",
        livesReward: String = ""
    ) {
        self.type = type
        self.level = level
        self.livesReward = livesReward
    }
}

enum LevelCardType {
    case Lost
    case Win
    case Lock
    case Playing
    case Unlock
}

struct LevelCard: View {
    let isLoading: Bool
    let cardData: LevelCardData
    let onTap: (() -> Void)?

    init(
        isLoading: Bool = false,
        cardData: LevelCardData,
        onTap: (() -> Void)? = nil
    ) {
        self.isLoading = isLoading
        self.cardData = cardData
        self.onTap = onTap
    }

    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        let colorBox = CThemeColors(colorScheme: colorScheme).softContrastScreenBackground

        let rect = rectangle(
            colorBox: colorBox
        )

        if isLoading {
            rect
        } else {
            RippleButton(
                transparent: false,
                action: {
                    if onTap != nil {
                        onTap!()
                    }
                },
                content: {
                    ZStack(
                        alignment: .center
                    ) {
                        rect
                        VStack(
                            alignment: .center,
                            spacing: 0
                        ) {
                            stateRectangle(
                                type: cardData.type,
                                colorBox: colorBox
                            )
                            CText(
                                text: cardData.level,
                                fontSize: cFontSize64,
                                fixedFont: true
                            )
                            bottomItem(
                                type: cardData.type,
                                colorBox: colorBox,
                                livesReward: cardData.livesReward
                            )
                        }
                    }
                }
            )
        }
    }

    @ViewBuilder func bottomItem(
        type: LevelCardType,
        colorBox: Color,
        livesReward: String
    ) -> some View {
        if type == .Lock {
            CIcon(
                type: .lock
            )
        } else if type == .Unlock || type == .Playing {
            CIcon(
                type: .unlock
            )
        } else if type == .Lost || type == .Win {
            ZStack {
                CIcon(
                    type: .heartFilled,
                    color: type == .Win ? CColors.green : nil
                )
                CText(
                    text: livesReward,
                    color: colorBox,
                    fontSize: cFontSize12,
                    fixedFont: true
                )
            }
        }
    }

    @ViewBuilder func rectangle(colorBox: Color) -> some View {
        RoundedRectangle(
            cornerRadius: cFontSize8
        )
        .fill(colorBox)
        .frame(
            width: levelCardSize,
            height: levelCardSize
        )
    }

    @ViewBuilder func stateRectangle(
        type: LevelCardType,
        colorBox: Color
    ) -> some View {
        if type == .Lock || type == .Unlock {
            Rectangle()
                .frame(
                    width: 0,
                    height: 20.0
                )
        } else {
            let color = switch type {
            case .Win:
                CColors.green
            case .Lost:
                CColors.red
            case .Playing:
                CColors.yellow
            default: colorBox
            }

            let text: LocalizedStringKey = switch type {
            case .Win:
                "levels_card_win"
            case .Lost:
                "levels_card_lost"
            case .Playing:
                "levels_card_playing"
            default: ""
            }

            ZStack {
                RoundedRectangle(
                    cornerRadius: cBorderRadius4
                )
                .fill(color)
                .frame(
                    width: 68,
                    height: 20
                )
                CText(
                    key: text,
                    color: colorBox,
                    fontSize: cFontSize12,
                    fixedFont: true
                )
            }
        }
    }
}

#Preview {
    ZStack {
        Color.green
        VStack(
            spacing: 10
        ) {
            LevelCard(
                cardData: LevelCardData(
                    type: .Win,
                    level: "2",
                    livesReward: "+3"
                )
            )
            LevelCard(
                cardData: LevelCardData(
                    type: .Lost,
                    level: "2",
                    livesReward: "-1"
                )
            )
            LevelCard(
                cardData: LevelCardData(
                    type: .Unlock,
                    level: "2"
                )
            )
            LevelCard(
                cardData: LevelCardData(
                    type: .Lock,
                    level: "2"
                )
            )
            LevelCard(
                cardData: LevelCardData(
                    type: .Playing,
                    level: "2"
                )
            )
        }
    }
    .frame(width: Double.infinity, height: Double.infinity)
}
