//
//  Levels.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 19/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct Levels: View {
    @StateObject var vm = LevelsViewModel()

    var body: some View {
        VStack {
            Appbar(
                showTutorial: vm.state.openTutorial,
                lives: vm.state.lives,
                onTapLogo: { vm.sendEvent(event: .refreshScreen) }
            )
            switch vm.state.screenState {
            case .loading:
                loader()
            case .success:
                LevelsGrid()
                    .environmentObject(vm)
            default:
                ZStack {}
            }
        }
        .onAppear {
            vm.sendEvent(event: .onAppear)
        }
    }

    @ViewBuilder func loader() -> some View {
        Spacer()
        CLoader()
        Spacer()
    }
}
