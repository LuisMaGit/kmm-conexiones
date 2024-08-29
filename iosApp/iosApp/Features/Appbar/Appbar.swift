//
//  Appbar.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 26/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct Appbar: View {
    let showTutorial: Bool
    let lives: Int
    let onTapLogo: () -> Void

    init(
        showTutorial: Bool,
        lives: Int,
        onTapLogo: @escaping () -> Void
    ) {
        self.showTutorial = showTutorial
        self.lives = lives
        self.onTapLogo = onTapLogo
    }

    @StateObject var vm: AppbarViewModel = .init()

    var body: some View {
        let showTutorialBinding: Binding<Bool> = Binding(
            get: { vm.state.showTutorial },
            set: { _ in }
        )
        let showStatsBinding: Binding<Bool> = Binding(
            get: { vm.state.showStats },
            set: { _ in }
        )
        let showLivesBinding: Binding<Bool> = Binding(
            get: { vm.state.showLives },
            set: { _ in }
        )

        let livesToShow = vm.state.lives < 0 ? 0 : vm.state.lives
        HStack {
            RippleButton(
                transparent: false,
                action: onTapLogo
            ) {
                CLogo()
            }
            Spacer()
            // tutorial
            RippleButton(
                transparent: false,
                action: {
                    vm.sendEvent(event: .openTutorial(show: true))
                }
            ) {
                CIcon(type: .question)
            }
            .padding([.trailing], AppBarContracts.PADDING_ICONS)
            // stats
            RippleButton(
                transparent: false,
                action: {
                    vm.sendEvent(event: .openStats(show: true))
                }
            ) {
                CIcon(type: .chart)
            }
            .padding([.trailing], AppBarContracts.PADDING_ICONS)
            // lives
            AppbarLives(
                lives: "x\(String(livesToShow))",
                emptyLives: vm.state.lives == 0,
                onTapLives: { vm.sendEvent(event: .openLives(show: true)) }
            )
        }
        .padding([.leading, .trailing], AppBarContracts.PADDING_H_BAR)
        .padding([.bottom], AppBarContracts.PADDING_V_BAR)
        .onChange(of: lives) { lives in
            vm.sendEvent(event: .setLives(lives: lives))
        }
        .onChange(of: showTutorial) { show in
            vm.sendEvent(event: .openTutorial(show: show))
        }
        .sheet(
            isPresented: showTutorialBinding,
            onDismiss: {
                vm.sendEvent(event: .openTutorial(show: false))
            }
        ) {
            CText(text: "TUTORIAL")
        }
        .sheet(
            isPresented: showStatsBinding,
            onDismiss: {
                vm.sendEvent(event: .openStats(show: false))
            }
        ) {
            CText(text: "STATS")
        }
        .sheet(
            isPresented: showLivesBinding,
            onDismiss: {
                vm.sendEvent(event: .openLives(show: false))
            }
        ) {
            CText(text: "LIVES")
        }
    }
}

enum AppBarContracts {
    static let PADDING_ICONS = 16.0
    static let PADDING_H_BAR = 16.0
    static let PADDING_V_BAR = 16.0
}
