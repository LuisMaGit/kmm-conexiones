//
//  MockAddView.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 29/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct MockAddView: View {
    @StateObject var vm = MockAddViewModel()
    @Environment(\.colorScheme) var colorScheme
    var body: some View {
        ZStack {
            CThemeColors(colorScheme: colorScheme).screenBackground
            VStack(
                spacing: cSpace8
            ) {
                CText(
                    text: "YOUR ARE WATCHING AN ADD,\nIT WILL END IN:",
                    fontSize: cFontSize20
                )
                .multilineTextAlignment(.center)
                CText(
                    text: "\(vm.addTime)s",
                    fontSize: cFontSize64
                )
            }
        }
    }
}

class MockAddViewModel: ObservableObject {
    @Published public private(set) var addTime: Int
    private let userProfileService: IUserProfileService
    private let router: Router = .instance

    init(
        addTime: Int = 3,
        userProfileService: IUserProfileService = ServicesLocatorKt.userProfileService()
    ) {
        self.addTime = addTime
        self.userProfileService = userProfileService
        startAdd()
    }

    func startAdd() {
        Timer.scheduledTimer(withTimeInterval: 1, repeats: true) { _ in
            Task {
                await MainActor.run {
                    self.addTime -= 1
                }

                if self.addTime == 0 {
                    let lives = try await self.userProfileService.getLives()
                    try await self.userProfileService.updateLives(lives: Int32(truncating: lives) + 1)
                    await self.router.pop()
                }
            }
        }
    }
}
