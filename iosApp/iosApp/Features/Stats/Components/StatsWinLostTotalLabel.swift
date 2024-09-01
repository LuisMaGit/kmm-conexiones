//
//  StatsWinLostTotalLabel.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 1/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct StatsWinLostTotalLabel: View {
    let amount: Int
    let color: Color
    let percentage: Int
    let name: LocalizedStringKey

    init(
        amount: Int,
        color: Color,
        percentage: Int,
        name: LocalizedStringKey
    ) {
        self.amount = amount
        self.color = color
        self.percentage = percentage
        self.name = name
    }

    var body: some View {
        VStack(
            spacing: 0
        ) {
            CText(
                text: String(amount),
                color: color,
                fontSize: cFontSize32
            )
            CText(
                key: name,
                fontSize: cFontSize12
            )
            CText(
                text: "[\(percentage)%]",
                fontSize: cFontSize12
            )
        }
    }
}
