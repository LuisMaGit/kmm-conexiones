//
//  AppbarViewModel.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 26/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum AppbarEvents {
    case openTutorial(show: Bool)
    case openLives(show: Bool)
    case openStats(show: Bool)
    case setLives(lives: Int)
}

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

class AppbarViewModel: ObservableObject {
    // state
    @Published public private(set) var state: AppbarState

    init(
        state: AppbarState = .init()
    ) {
        self.state = state
    }

    func sendEvent(event: AppbarEvents) {
        switch event {
        case .openTutorial(show: let show):
            openTutorial(show: show)
        case .openStats(show: let show):
            openStats(show: show)
        case .openLives(show: let show):
            openLives(show: show)
        case .setLives(lives: let lives):
            setLives(lives: lives)
        }
    }

    private func setLives(lives: Int) {
        if state.lives == lives {
            return
        }
        
        state.lives = lives
        state.showLives = lives == 0
    }

    private func openTutorial(show: Bool) {
        if state.showTutorial == show {
            return
        }
        
        state.showTutorial = show
    }

    private func openStats(show: Bool) {
        if state.showStats == show {
            return
        }

        state.showStats = show
    }

    private func openLives(show: Bool) {
        if state.showLives == show {
            return
        }

        state.showLives = show
    }
}
