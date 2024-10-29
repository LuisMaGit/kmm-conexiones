//
//  GameErrors.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 11/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct GameErrors: View {
    @EnvironmentObject var vm: GameViewModel

    var body: some View {
        HStack(
            alignment: .center,
            spacing: 0
        ) {
            Spacer()
            CText(
                key: "level_errors",
                fontSize: cFontSize16
            )
            .padding([.trailing], cSpace8)
            HStack(
                spacing: cSpace4
            ) {
                ForEach(
                    0 ..< GameContractsKt.GAME_AMOUNT_TRIES,
                    id: \.self
                ) { error in
                    CTryChip(
                        visible: error + 1 <= vm.state.gameData.remainingErrors
                    )
                }
                Spacer()
            }
            .frame(width: 100)
            Spacer()
        }
    }
}
