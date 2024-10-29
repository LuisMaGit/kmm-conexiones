//
//  GameViewEvents.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

enum GameViewEvents {
    case onAppear(gameId: Int)
    case selectWord(column: Int, row: Int)
    case dismissAnimation(column: Int, row: Int)
    case goToNextLevel(nextLevel: Int)
    case dismissTileAnimation(row: Int)
    case dismissOnDoneAnimation
    case dismissLivesAnimation
    case mixGame
    case clearSelection
    case onBack
    case submit
    case moreLives
}
