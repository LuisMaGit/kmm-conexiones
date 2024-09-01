//
//  GameDifficultyColors.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 31/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

func getColorByDifficulty(difficulty: Int) -> GameDifficultyColors {
    switch difficulty {
    case 0:
        return GameDifficultyColors.green
    case 1:
        return GameDifficultyColors.yellow
    case 2:
        return GameDifficultyColors.orange
    case 3:
        return GameDifficultyColors.red
    default: return GameDifficultyColors.green
    }
}

func getColorByDomainColor(color: GameDifficultyColors) -> Color {
    switch color {
    case .green:
        return CColors.green
    case .yellow:
        return CColors.yellow
    case .orange:
        return CColors.orange
    case .red:
        return CColors.red
    default:
        return CColors.black
    }
}
