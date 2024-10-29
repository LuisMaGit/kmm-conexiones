//
//  GameDoneMoreLives.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct GameDoneMoreLives: View {
    @EnvironmentObject var vm: GameViewModel

    var body: some View {
        VStack(
            spacing: 0
        ) {
            CText(
                key: "level_lost_no_lives",
                fontSize: cFontSize14
            )
            .padding(.bottom, cSpace16)
            CButton(
                color: CColors.orange,
                svg: .heartFilled,
                key: "+1",
                onTap: {}
            )
        }
    }
}
