//
//  LevelsViewModel.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 22/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

enum LevelsEvents {
    case onAppear
    case refreshScreen
    case initialScrollDone
    case previousPage
    case nextPage
    case onVisibilityChangePlayingCard(show: Bool)
}

class LevelsViewModel: ObservableObject {
    // state
    @Published public private(set) var state: LevelsState
    
    private let gameLevelsService: GamesLevelsService
    private let userProfileService: UserProfileService
    private let paginationService: PaginationService
    
    init(
        state: LevelsState = .init(),
        gameLevelsService: GamesLevelsService = ServicesLocatorKt.gamesLevelsService(),
        userProfileService: UserProfileService = ServicesLocatorKt.userProfileService(),
        paginationService: PaginationService = ServicesLocatorKt.paginationService()
    ) {
        self.state = state
        self.gameLevelsService = gameLevelsService
        self.userProfileService = userProfileService
        self.paginationService = paginationService
    }
    
    func sendEvent(event: LevelsEvents) {
        switch event {
        case .onAppear:
            initViewModel(resetState: false)
        case .refreshScreen:
            initViewModel(resetState: true)
        case .initialScrollDone:
            initialScrollDone()
        case .previousPage:
            previousPage()
        case .nextPage:
            nextPage()
        case .onVisibilityChangePlayingCard(show: let show):
            onVisibilityChangePlayingCard(show: show)
        }
    }
    
    private func onVisibilityChangePlayingCard(show: Bool) {
        if state.playingRowIsShowing == show {
            return
        }
        
        Task {
            await MainActor.run {
                state.playingRowIsShowing = show
            }
        }
    }
    
    private func initViewModel(resetState: Bool) {
        Task {
            if resetState {
                await MainActor.run {
                    state = .init()
                }
            }
            
            async let playingPageResp = gameLevelsService.getLevelsPlayingPage()
            async let playingRowIdResp = gameLevelsService.getPlayingRowId()
            async let livesResp = userProfileService.getLives()
            async let appWasOpenedResp = userProfileService.appWasOpenedBefore()
            
            let playingPage = try await (playingPageResp)
            let playingRowId = try await (playingRowIdResp)
            let lives = try await (livesResp)
            let appWasOpened = try await (appWasOpenedResp)
            
            await MainActor.run {
                state.games = playingPage.games
                state.startPage = Int(playingPage.page)
                state.initialScrollIdx = Int(playingPage.firstPagePlayingLevelIdxOffset
                )
                state.reachFirstPage = paginationService.reachFirstPageByPageState(
                    pageState: playingPage.pageState
                )
                state.reachLastPage = paginationService.reachLastPageByPageState(
                    pageState: playingPage.pageState
                )
                state.fetchedPages = [Int(playingPage.page)]
                state.playingRowId = Int(truncating: playingRowId)
                state.playingRowIdx = Int(gameLevelsService.getIndexOfRowId(
                    games: playingPage.games,
                    rowId: Int32(truncating: playingRowId)
                ))
                state.playingRowIsShowing = true
                state.lives = Int(truncating: lives)
                state.openTutorial = !Bool(truncating: appWasOpened)
                state.screenState = .success
            }
        }
    }
    
    private func initialScrollDone() {
        if state.initialScrollDone {
            return
        }
        
        state.initialScrollDone = true
    }
    
    private func previousPage() {
        Task {
            await MainActor.run {
                state.screenState = .loading
            }

            let fetchedPages = paginationService.getPreviousPage(
                fetchedPages: Set(state.fetchedPages.map { value in KotlinInt(value: Int32(value)) })
            )
            let fetchedPagesSorted = fetchedPages.map {
                value in Int(truncating: value)
            }.sorted(by: <)
            let responsePlaying = try await gameLevelsService.getLevelsByPage(
                page: Int32(fetchedPagesSorted.first ?? 0)
            )

            await MainActor.run {
                state.games = responsePlaying.games + state.games
                state.reachFirstPage = paginationService.reachFirstPageByPageState(
                    pageState: responsePlaying.pageState
                )
                state.fetchedPages = Set(fetchedPagesSorted)
                state.playingRowIdx = Int(gameLevelsService.getIndexOfRowId(
                    games: state.games,
                    rowId: Int32(state.playingRowId)
                ))
                state.playingRowIsShowing = false
                state.screenState = .success
            }
        }
    }
    
    private func nextPage() {
        Task {
            let fetchedPages = paginationService.getNextPage(
                fetchedPages: Set(state.fetchedPages.map { value in KotlinInt(value: Int32(value)) })
            )
            let fetchedPagesSorted = fetchedPages.map {
                value in Int(truncating: value)
            }.sorted(by: <)
            
            let resposePlaying = try await gameLevelsService.getLevelsByPage(
                page: Int32(fetchedPagesSorted.last ?? 0)
            )
            await MainActor.run {
                state.games = state.games + resposePlaying.games
                state.reachLastPage = paginationService.reachLastPageByPageState(
                    pageState: resposePlaying.pageState
                )
                state.fetchedPages = Set(fetchedPagesSorted)
                state.playingRowIdx = Int(gameLevelsService.getIndexOfRowId(
                    games: state.games,
                    rowId: Int32(state.playingRowId)
                ))
            }
        }
    }
}
