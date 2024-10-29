//
//  GameTitle.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 11/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


struct GameTitle: View {
    let level: Int

    var body: some View {
        CText(
            key: "level_title \(Int(level), specifier: "%d")",
            fontSize: cFontSize32
        )
        .padding([.top, .bottom], cSpace8)
    }
}
