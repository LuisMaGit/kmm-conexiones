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
    let onTapLogo: (() -> Void)?
    let onTapBack: (() -> Void)?
    let leadingComponentType: AppBarLeadingComponentType

    init(
        showTutorial: Bool = false,
        lives: Int,
        onTapLogo: (() -> Void)? = nil,
        onTapBack: (() -> Void)? = nil,
        leadingComponentType: AppBarLeadingComponentType = .logo
    ) {
        self.showTutorial = showTutorial
        self.lives = lives
        self.onTapLogo = onTapLogo
        self.onTapBack = onTapBack
        self.leadingComponentType = leadingComponentType
    }

    @StateObject var vm: AppbarViewModel = .init()

    var body: some View {
        let sizeIcon = 24.0
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
            switch leadingComponentType {
            case .logo:
                RippleButton(
                    transparent: false,
                    action: {
                        if let onTap = onTapLogo {
                            onTap()
                        }
                    }
                ) {
                    CLogo(size: sizeIcon)
                }
            case .backArrow:
                RippleButton(
                    transparent: false,
                    action: {
                        if let onTap = onTapBack {
                            onTap()
                        }
                    }
                ) {
                    CIcon(
                        type: .arrowLeft,
                        height: sizeIcon
                    )
                }
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
            .padding([.trailing], cSpace16)
            // stats
            RippleButton(
                transparent: false,
                action: {
                    vm.sendEvent(event: .openStats(show: true))
                }
            ) {
                CIcon(type: .chart)
            }
            .padding([.trailing], cSpace16)
            // lives
            AppbarLives(
                lives: "x\(String(livesToShow))",
                emptyLives: vm.state.lives == 0,
                onTapLives: { vm.sendEvent(event: .openLives(show: true)) }
            )
        }
        .padding([.leading, .trailing], cSpace16)
        .padding([.bottom], cSpace16)
        .onChange(of: lives) { lives in
            vm.sendEvent(event: .setLives(lives: lives))
        }
        .onChange(of: showTutorial) { show in
            vm.sendEvent(event: .openTutorial(show: show))
        }
        .modifier(
            CSheet(
                isPresented: showTutorialBinding,
                onDismiss: {
                    vm.sendEvent(event: .openTutorial(show: false))
                }
            ) {
                Tutorial()
            }
        )
        .modifier(
            CSheet(
                isPresented: showStatsBinding,
                onDismiss: {
                    vm.sendEvent(event: .openStats(show: false))
                },
                useCustomHeight: true,
                customHeightFraction: 0.6
            ) {
                Stats()
            }
        )
        .modifier(
            CSheet(
                isPresented: showLivesBinding,
                onDismiss: {
                    vm.sendEvent(event: .openLives(show: false))
                },
                useCustomHeight: true,
                customHeightFraction: 0.4
            ) {
                Lives()
            }
        )
    }
}

enum AppBarLeadingComponentType {
    case logo
    case backArrow
}
