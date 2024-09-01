//
//  StatsState.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 1/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

struct StatsState {
    var statsData: StatsData?
    var screenState: BasicScreenState

    init(
        statsData: StatsData? = nil,
        screenState: BasicScreenState = .loading
    ) {
        self.statsData = statsData
        self.screenState = screenState
    }
}
