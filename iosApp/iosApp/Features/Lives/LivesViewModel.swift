//
//  LivesViewModel.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 2/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI



class LivesViewModel: ObservableObject {
    @Published public private(set) var state: LivesState

    private let userProfileService: IUserProfileService

    init(
        state: LivesState = .init(),
        userProfileService: IUserProfileService = ServicesLocatorKt.userProfileService()
    ) {
        self.state = state
        self.userProfileService = userProfileService
        getLives()
    }

    private func getLives() {
        Task {
            let lives = try await userProfileService.getLives()
            await MainActor.run {
                state.lives = Int(truncating: lives)
                state.screenState = .success
            }
        }
    }
}
