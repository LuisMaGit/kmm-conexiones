//
//  GameCardsContracts.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 9/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum GameCardContracts {
    case Mobile
    case Tablet
}

struct GameCardsContractsModel {
    let contract: GameCardContracts
    let wordCardWidth: Double
    let cardHeight: Double
    let wordCardFontSize: Double
    let cardSpacing: Double
    let guessedCardWidth: Double
    let guessedCardTitleFontSize: Double
    let guessedCardDescFontSize: Double
}

func gameCardContracts(_ contract: GameCardContracts) -> GameCardsContractsModel {
    switch contract {
    case .Mobile:
        return GameCardsContractsModel(
            contract: .Mobile,
            wordCardWidth: 80,
            cardHeight: 60,
            wordCardFontSize: cFontSize14,
            cardSpacing: 3,
            guessedCardWidth: 329, // 80 * 4 + 3 * 3
            guessedCardTitleFontSize: cFontSize16,
            guessedCardDescFontSize: cFontSize14
        )
    case .Tablet:
        return GameCardsContractsModel(
            contract: .Tablet,
            wordCardWidth: 150,
            cardHeight: 80,
            wordCardFontSize: cFontSize20,
            cardSpacing: 4,
            guessedCardWidth: 612, // 150 + 4 + 4 *3
            guessedCardTitleFontSize: cFontSize20,
            guessedCardDescFontSize: cFontSize16
        )
    }
}
