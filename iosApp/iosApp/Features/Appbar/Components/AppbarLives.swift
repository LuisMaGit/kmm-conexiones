//
//  AppbarHeart.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 27/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AppbarLives: View {
    let lives: String
    let emptyLives: Bool
    let onTapLives: () -> Void

    init(
        lives: String,
        emptyLives: Bool,
        onTapLives: @escaping () -> Void
    ) {
        self.lives = lives
        self.emptyLives = emptyLives
        self.onTapLives = onTapLives
    }

    @State var animate: Bool = false

    var body: some View {
        HStack {
            RippleButton(
                action: onTapLives
            ) {
                if emptyLives {
                    emtpyIcon()
                } else {
                    filledIcon()
                }
            }
            CText(text: lives)
        }
    }

    @ViewBuilder func filledIcon() -> some View {
        CIcon(
            type: .heartFilled,
            color: CColors.green
        )
    }

    @ViewBuilder func emtpyIcon() -> some View {
        CIcon(
            type: .heart
        )
        .scaleEffect(animate ? 1.8 : 1, anchor: .center)
        .onAppear {
            withAnimation(.easeInOut(duration: 1).repeatForever(autoreverses: true)) {
                animate = true
            }
        }
        .onDisappear {
            animate = false
        }
    }
}
