//
//  AppbarState.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 1/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct AppbarState {
    var showTutorial: Bool
    var showStats: Bool
    var showLives: Bool
    var lives: Int

    init(
        showTutorial: Bool = false,
        showStats: Bool = false,
        showLives: Bool = false,
        lives: Int = -1
    ) {
        self.showTutorial = showTutorial
        self.showStats = showStats
        self.lives = lives
        self.showLives = showLives
    }
}
