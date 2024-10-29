//
//  LevelsEvents.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 29/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum LevelsEvents {
    case onAppear
    case refreshScreen
    case initialScrollDone
    case previousPage
    case nextPage
    case onVisibilityChangePlayingCard(show: Bool)
    case goToLevel(gameId: Int)
}
