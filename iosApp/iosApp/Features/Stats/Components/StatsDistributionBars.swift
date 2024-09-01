//
//  StatsDistributionBars.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 1/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct StatsDistributionBars: View {
    let percentage: [Int]
    let values: [Int]
    let maxIdx: Int

    init(
        percentage: [Int],
        values: [Int],
        maxIdx: Int
    ) {
        self.percentage = percentage
        self.values = values
        self.maxIdx = maxIdx
    }

    var body: some View {
        if values.count != percentage.count
            || values.isEmpty
            || values.allSatisfy({ val in val == 0 })
        {
            ZStack {}
        } else {
            VStack(
                alignment: .leading,
                spacing: 0
            ) {
                ForEach(
                    0 ..< values.count,
                    id: \.self
                ) { idx in
                    let perc = percentage[idx]
                    let value = values[idx]
                    StatsDistributionBar(
                        index: idx,
                        percentage: perc,
                        amount: value,
                        isMax: maxIdx == idx
                    )
                }
            }
        }
    }
}

struct StatsDistributionBar: View {
    let index: Int
    let percentage: Int
    let amount: Int
    let isMax: Bool

    init(
        index: Int,
        percentage: Int,
        amount: Int,
        isMax: Bool
    ) {
        self.index = index
        self.percentage = percentage
        self.amount = amount
        self.isMax = isMax
    }

    @Environment(\.colorScheme) var colorScheme
    var body: some View {
        let barH = 20.0
        let barTotalH = barH + cSpace16
        let barColor = isMax ? CColors.green : CThemeColors(colorScheme: colorScheme).textDefault
        GeometryReader { geo in
            HStack(
                spacing: 0
            ) {
                CText(
                    text: String(index + 1),
                    fontSize: cFontSize16,
                    fixedFont: true
                )
                .frame(width: 25)
                RoundedRectangle(
                    cornerRadius: cBorderRadius4
                )
                .fill(barColor)
                .frame(
                    width: geo.size.width * 0.7 * (Double(percentage) / 100),
                    height: barH
                )
                CText(
                    text: amount == 0 ? "" : String(amount),
                    color: barColor,
                    fontSize: cFontSize16,
                    fixedFont: true
                )
                .frame(width: 30)
            }
        }
        .frame(height: barTotalH)
    }
}
