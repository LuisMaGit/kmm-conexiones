//
//  LevelFloatingButton.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 22/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LevelFloatingButton: View {
    let onTap: () -> Void

    init(onTap: @escaping () -> Void) {
        self.onTap = onTap
    }

    var body: some View {
        RippleButton(
            transparent: false,
            action: onTap
        ) {
            ZStack(alignment: .center) {
                Rectangle()
                    .fill(CColors.orange)
                    .frame(
                        maxWidth: .infinity, maxHeight: LevelFloatingButtonContracts.HEIGHT
                    )
                CText(
                    key: "levels_floating_button",
                    color: CColors.white
                )
            }
        }
    }
}

enum LevelFloatingButtonContracts {
    static let HEIGHT = 50.0
}

#Preview {
    LevelFloatingButton(
        onTap: {}
    )
}
