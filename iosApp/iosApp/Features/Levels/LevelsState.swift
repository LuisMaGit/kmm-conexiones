//
//  LevelsState.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 22/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared

struct LevelsState {
    var screenState: BasicScreenState
    var games: [GameData]
    var startPage: Int
    var initialScrollIdx: Int
    var reachFirstPage: Bool
    var reachLastPage: Bool
    var fetchedPages: Set<Int>
    var playingRowId: Int
    var playingRowIdx: Int
    var playingRowIsShowing: Bool
    var lives: Int
    var openTutorial: Bool

    var initialScrollDone: Bool

    init(
        screenState: BasicScreenState = .loading,
        games: [GameData] = [],
        startPage: Int = 0,
        initialScrollIdx: Int = 0,
        reachFirstPage: Bool = false,
        reachLastPage: Bool = false,
        fetchedPages: Set<Int> = [],
        playingRowId: Int = 0,
        playingRowIdx: Int = 0,
        playingRowIsShowing: Bool = false,
        lives: Int = -1,
        openTutorial: Bool = false,
        initialScrollDone: Bool = false
    ) {
        self.screenState = screenState
        self.games = games
        self.startPage = startPage
        self.initialScrollIdx = initialScrollIdx
        self.reachFirstPage = reachFirstPage
        self.reachLastPage = reachLastPage
        self.fetchedPages = fetchedPages
        self.playingRowId = playingRowId
        self.playingRowIdx = playingRowIdx
        self.playingRowIsShowing = playingRowIsShowing
        self.lives = lives
        self.openTutorial = openTutorial
        self.initialScrollDone = initialScrollDone
    }
}
