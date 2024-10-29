//
//  WordCard.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 9/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct WordCard: View {
    let disabled: Bool
    let sizeType: GameCardContracts
    let text: String
    let selected: Bool
    let onTap: (_ column: Int, _ row: Int) -> Void
    let column: Int
    let row: Int
    let animationType: WordCarAnimationType
    let dismissAnimation: (_ column: Int, _ row: Int) -> Void

    @Environment(\.colorScheme) var colorScheme
    @State var scaleAnimation = false
    @State var translationX = 0.0
    let translateXBase = 5.0

    // -20 at 50
    // 20 at 100
    // -20 at 150
    // 20 at 200
    // 0 at 250

    var body: some View {
        if disabled {
            roundedRect()
        }
        else {
            RippleButton(
                transparent: false,
                action: { onTap(column, row) },
                content: {
                    roundedRect()
                }
            )
            .onChange(of: animationType) { anim in
                if #available(iOS 17.0, *) {
                    if anim == WordCarAnimationType.noAnimation {
                        if scaleAnimation {
                            scaleAnimation = false
                        }
                        if translationX != 0 {
                            translationX = 0
                        }
                    }
                    else if anim == WordCarAnimationType.scale {
                        withAnimation(.easeOut(duration: 0.1)) {
                            scaleAnimation = true
                        } completion: {
                            dismissAnimation(column, row)
                        }
                    }
                    else if anim == WordCarAnimationType.jump {
                        // 50ms
                        withAnimation(.easeInOut(duration: 0.05)) {
                            translationX = -translateXBase
                        }
                        // 100ms
                        withAnimation(.easeInOut(duration: 0.05).delay(0.05)) {
                            translationX = translateXBase
                        }
                        // 150ms
                        withAnimation(.easeInOut(duration: 0.05).delay(0.1)) {
                            translationX = -translateXBase
                        }
                        // 200ms
                        withAnimation(.easeInOut(duration: 0.05).delay(0.15)) {
                            translationX = translateXBase
                        }
                        // 250ms
                        withAnimation(.easeInOut(duration: 0.05).delay(0.2)) {
                            translationX = -translateXBase
                        } completion: {
                            dismissAnimation(column, row)
                        }
                    }
                }
            }
        }
    }

    @ViewBuilder func roundedRect() -> some View {
        let colors = CThemeColors(colorScheme: colorScheme)
        let colorBox = selected ? colors.textDefault : colors.softContrastScreenBackground
        let colorText = selected ? colors.softContrastScreenBackground : colors.textDefault
        let sizes = gameCardContracts(sizeType)
        ZStack(
            alignment: .center
        ) {
            RoundedRectangle(
                cornerRadius: cBorderRadius4
            )
            .fill(colorBox)
            CText(
                text: text,
                color: colorText,
                fontSize: sizes.wordCardFontSize,
                lineLimit: 2
            )
            .multilineTextAlignment(.center)
        }
        .scaleEffect(
            scaleAnimation ? 1.05 : 1
        )
        .offset(
            x: translationX
        )
        .frame(
            width: sizes.wordCardWidth,
            height: sizes.cardHeight
        )
    }
}

enum WordCarAnimationType {
    case scale
    case jump
    case noAnimation
}
