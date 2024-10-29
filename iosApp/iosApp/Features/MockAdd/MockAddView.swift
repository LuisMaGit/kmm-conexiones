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
    var body: some View {
        VStack {
            CText(
                text: "YOUR ARE WATCHING AN ADD,"
            )
            CText(
                text: "IT WILL END IN: \(vm.addTime)s"
            )
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
