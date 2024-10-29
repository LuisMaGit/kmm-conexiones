//
//  GuessedCard.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 9/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct GuessedCard: View {
    let row: Int
    let description: String
    let words: String
    let color: Color
    let sizeType: GameCardContracts
    let animationType: GuessedCardAnimationType
    let scaleOrder: Int
    let dissmissAnimation: (_ row: Int) -> Void

    @State var scaleAnimation = false
    
    func animationHandler(anim: GuessedCardAnimationType) {
        if #available(iOS 17.0, *) {
            if anim == .noAnimation {
                if scaleAnimation {
                    scaleAnimation = false
                }
            } else if anim == .scale {
                withAnimation(.easeOut(duration: guessedCardAnimationDurationInSec)
                    .delay(Double(scaleOrder) * guessedCardAnimationDurationInSec)
                ) {
                    scaleAnimation = true
                } completion: {
                    dissmissAnimation(row)
                }
            }
        }
    }

    var body: some View {
        return roundedRect()
            .onAppear {
                animationHandler(anim: animationType)
            }
            .onChange(of: animationType) { anim in
                animationHandler(anim: anim)
            }
    }

    @ViewBuilder func roundedRect() -> some View {
        let sizes = gameCardContracts(sizeType)
        ZStack(
            alignment: .center
        ) {
            RoundedRectangle(
                cornerRadius: cBorderRadius4
            )
            .fill(color)
            VStack(
                alignment: .center,
                spacing: 0
            ) {
                CText(
                    text: description,
                    color: CColors.black,
                    fontSize: animationType == .noAnimation
                        ? sizes.guessedCardTitleFontSize
                        : scaleAnimation ? sizes.guessedCardTitleFontSize * guessedCardScaleFactor : 0
                )
                .multilineTextAlignment(.center)
                .padding([.bottom], cSpace4)
                CText(
                    text: words,
                    color: CColors.black,
                    font: .ceraRegular,
                    fontSize: animationType == .noAnimation
                        ? sizes.guessedCardTitleFontSize
                        : scaleAnimation ? sizes.guessedCardTitleFontSize * guessedCardScaleFactor : 0
                )
                .multilineTextAlignment(.center)
            }
        }
        .scaleEffect(
            animationType == .noAnimation ? 1 : scaleAnimation ? guessedCardScaleFactor : 0
        )
        .frame(
            width: sizes.guessedCardWidth,
            height: sizes.cardHeight
        )
    }
}

let guessedCardAnimationDurationInSec = 0.5
let guessedCardScaleFactor = 1.02

enum GuessedCardAnimationType {
    case noAnimation
    case scale
}
