//
//  GameDoneLives.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 11/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct GameDoneLives: View {
    @EnvironmentObject var vm: GameViewModel

    var body: some View {
        if vm.state.gameData.gameState == .win {
            ZStack {
                HStack(
                    spacing: 0
                ) {
                    Spacer()
                    ForEach(
                        0 ..< GameContractsKt.GAME_MAX_LIVES,
                        id: \.self
                    ) { _ in
                        DoneLive(
                            filledLive: false,
                            animate: false,
                            live: 0,
                            lives: 0,
                            disposeAnimation: {}
                        )
                    }
                    Spacer()
                }
                HStack(
                    spacing: 0
                ) {
                    Spacer()
                    ForEach(
                        0 ..< vm.state.livesEarnedOnDone,
                        id: \.self
                    ) { live in
                        DoneLive(
                            filledLive: true,
                            animate: vm.state.showOnDoneLivesAnimation,
                            live: Int(live),
                            lives: vm.state.livesEarnedOnDone,
                            disposeAnimation: {
                                vm.sendEvent(event: .dismissLivesAnimation)
                            }
                        )
                    }
                    ForEach(
                        0 ..< Int(GameContractsKt.GAME_MAX_LIVES) - vm.state.livesEarnedOnDone,
                        id: \.self
                    ) { _ in
                        ZStack {}
                            .frame(
                                width: doneLiveSize,
                                height: doneLiveSize
                            )
                            .padding(.horizontal, cSpace16)
                    }
                    Spacer()
                }
            }
        }
    }
}

private let doneLiveSize = 34.0

private struct DoneLive: View {
    let filledLive: Bool
    let animate: Bool
    let live: Int
    let lives: Int
    let disposeAnimation: () -> Void

    @Environment(\.colorScheme) var colorScheme
    @State var scaleAnimation = false

    func animationHandler(trigger: Bool) {
        if #available(iOS 17.0, *) {
            if !trigger {
                if scaleAnimation {
                    scaleAnimation = false
                }
            } else if trigger {
                withAnimation(.easeOut(duration: guessedCardAnimationDurationInSec)
                    .delay(Double(live) * guessedCardAnimationDurationInSec)
                ) {
                    scaleAnimation = true
                } completion: {
                    if live == lives - 1 {
                        disposeAnimation()
                    }
                }
            }
        }
    }

    var body: some View {
        Group {
            if filledLive {
                CIcon(
                    type: .heartFilled,
                    width: doneLiveSize,
                    height: doneLiveSize,
                    color: CColors.green
                )
                .scaleEffect(
                    !animate
                        ? 1.05
                        : scaleAnimation
                        ? 1.05
                        : 0
                )
                .onAppear {
                    animationHandler(trigger: animate)
                }
            } else {
                CIcon(
                    type: .heart,
                    width: doneLiveSize,
                    height: doneLiveSize,
                    color: CThemeColors(colorScheme: colorScheme).textDefault
                )
            }
        }
        .padding(.horizontal, cSpace16)
    }
}
