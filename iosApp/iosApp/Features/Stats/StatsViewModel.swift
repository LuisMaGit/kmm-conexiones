//
//  StatsViewModel.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 29/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class StatsViewModel: ObservableObject {
    // state
    @Published public private(set) var state: StatsState

    private let statsService: StatsService

    init(
        state: StatsState = .init(),
        statsService: StatsService = ServicesLocatorKt.statsService()
    ) {
        self.state = state
        self.statsService = statsService
        initViewModel()
    }

    private func initViewModel() {
        Task {
            let statsData = try await statsService.calculateStats()
            await MainActor.run {
                state.statsData = statsData
                state.screenState = .success
            }
        }
    }
}
