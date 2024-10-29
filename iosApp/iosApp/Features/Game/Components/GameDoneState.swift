//
//  GameDoneState.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 11/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct GameDoneState: View {
    @EnvironmentObject var vm: GameViewModel
    @State var scaleAnimation = false
    
    func animationHandler(trigger: Bool) {
        if #available(iOS 17.0, *) {
            if !trigger {
                if scaleAnimation {
                    scaleAnimation = false
                }
            } else if trigger {
                withAnimation(.easeOut(duration: guessedCardAnimationDurationInSec)
                    .delay(Double(vm.state.notSolvedRowsOnLost.count) * guessedCardAnimationDurationInSec)
                ) {
                    scaleAnimation = true
                } completion: {
                    vm.sendEvent(event: .dismissOnDoneAnimation)
                }
            }
        }
    }
    
    var body: some View {
        let win = vm.state.gameData.gameState == GameState.win
        let lost = vm.state.gameData.gameState == GameState.lost
        
        if win || lost {
            HStack(
                spacing: 0
            ) {
                Spacer()
                CText(
                    key: win ? "level_win" : "level_lost",
                    fontSize: cFontSize36
                )
                .onAppear {
                    animationHandler(trigger: vm.state.showOnDoneSignAnimation)
                }
                Spacer()
            }
            .scaleEffect(
                !vm.state.showOnDoneSignAnimation
                    ? 1
                    : scaleAnimation
                    ? 1.05
                    : 0
            )
        }
    }
}
