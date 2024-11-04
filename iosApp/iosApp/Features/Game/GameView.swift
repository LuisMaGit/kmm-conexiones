//
//  GameView.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 10/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct GameView: View {
    @StateObject var vm = GameViewModel()

    let gameId: Int

    init(gameId: Int) {
        self.gameId = gameId
    }

    var body: some View {
        Group {
            VStack(
                spacing: 0
            ) {
                Appbar(
                    lives: vm.state.lives,
                    onTapBack: { vm.sendEvent(event: .onBack) },
                    leadingComponentType: .backArrow
                )
                switch vm.state.screenState {
                case .loading:
                    loader()
                case .success:
                    GameSuccess()
                        .environmentObject(vm)
                default:
                    ZStack {}
                }
            }
        }.onAppear {
            vm.sendEvent(event: .onAppear(gameId: gameId))
        }
    }

    @ViewBuilder func loader() -> some View {
        Spacer()
        CLoader()
        Spacer()
    }
}
