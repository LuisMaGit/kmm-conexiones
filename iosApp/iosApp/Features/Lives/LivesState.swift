//
//  LivesState.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 2/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LivesState {
    var lives: Int
    var screenState: BasicScreenState

    init(
        lives: Int = 0,
        screenState: BasicScreenState = .loading
    ) {
        self.lives = lives
        self.screenState = screenState
    }
}
