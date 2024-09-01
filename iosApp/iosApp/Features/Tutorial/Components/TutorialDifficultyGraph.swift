//
//  TutorialDifficultyGraph.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 31/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared


struct TutorialDifficultyGraph: View {
    let chipSize = 20.0

    var body: some View {
        HStack(
            spacing: cSpace16
        ) {
            Spacer()
            colors()
            arrow()
            Spacer()
        }
    }

    @ViewBuilder func arrow() -> some View {
        VStack(
            spacing: 0
        ) {
            CText(
                key: "kh_ex_graph_simple",
                fontSize: cFontSize12,
                fixedFont: true
            )
            .padding([.bottom], cSpace4)
            CIcon(
                type: .picArrowDown,
                height: 58
            )
            .padding([.bottom], cSpace8)
            CText(
                key: "kh_ex_graph_hard",
                fontSize: cFontSize12,
                fixedFont: true
            )
        }
    }

    @ViewBuilder func colors() -> some View {
        VStack(
            spacing: cSpace4
        ) {
            ForEach(
                0 ..< GameContractsKt.GAME_AMOUNT_DIFFICULTIES,
                id: \.self
            ) { diff in
                let colorDomain = getColorByDifficulty(difficulty: Int(diff))
                let color = getColorByDomainColor(color: colorDomain)
                chip(color: color)
            }
        }
    }

    @ViewBuilder func chip(color: Color) -> some View {
        RoundedRectangle(
            cornerRadius: cBorderRadius4
        )
        .fill(color)
        .frame(width: chipSize, height: chipSize)
    }
}

