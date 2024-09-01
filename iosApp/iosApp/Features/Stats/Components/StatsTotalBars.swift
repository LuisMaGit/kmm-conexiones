//
//  StatsTotalBars.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 1/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct StatsTotalBars: View {
    let winPercent: Int
    let lostPercent: Int

    init(
        winPercent: Int,
        lostPercent: Int
    ) {
        self.winPercent = winPercent
        self.lostPercent = lostPercent
    }

    var body: some View {
        let barH = 20.0
        GeometryReader { geo in
            HStack(
                spacing: 0
            ) {
                // win bar
                RoundedRectangle(
                    cornerRadius: cBorderRadius4
                )
                .fill(CColors.green)
                .frame(
                    width: geo.size.width * (Double(winPercent) / 100),
                    height: barH
                )
                RoundedRectangle(
                    cornerRadius: cBorderRadius4
                )
                // lost bar
                .fill(CColors.red)
                .frame(
                    width: geo.size.width * (Double(lostPercent) / 100),
                    height: barH
                )
            }
        }
        .frame(height: barH + cSpace16)
    }
}
