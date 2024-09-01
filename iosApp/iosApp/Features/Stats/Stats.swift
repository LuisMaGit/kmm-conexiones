//
//  Stats.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 29/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct Stats: View {
    @Environment(\.colorScheme) var colorScheme
    @ObservedObject var vm: StatsViewModel = .init()

    var body: some View {
        ScrollView {
            VStack(
                alignment: .leading,
                spacing: 0
            ) {
                // title
                HStack(
                    spacing: 0
                ) {
                    CSheetTitle(title: "stats_title")
                    Spacer()
                }
                if vm.state.screenState == .success {
                    success()
                } else {
                    loading()
                }
            }
        }
    }

    @ViewBuilder func success() -> some View {
        let played = vm.state.statsData?.playedBar
        let playedAmount = played?.value ?? 0
        if playedAmount == 0 {
            HStack {
                Spacer()
                CText(
                    key: "stats_empty"
                )
                .multilineTextAlignment(.center)
                Spacer()
            }
        } else {
            let win = vm.state.statsData?.winedBar
            let lost = vm.state.statsData?.lostBar
            let distBarsPercentages = vm.state.statsData?.distributionBars.map { val in Int(val.percentage) } ?? []
            let distBarsValues = vm.state.statsData?.distributionBars.map { val in Int(val.value) } ?? []
            let distBarsMaxIdx = Int(vm.state.statsData?.maxDistributionIdx ?? 0)
            // bars
            StatsTotalBars(
                winPercent: Int(win?.percentage ?? 0),
                lostPercent: Int(lost?.percentage ?? 0)
            )
            // winned, lost and played
            HStack(
                spacing: 0
            ) {
                StatsWinLostTotalLabel(
                    amount: Int(win?.value ?? 0),
                    color: CColors.green,
                    percentage: Int(win?.percentage ?? 0),
                    name: "stats_win"
                )
                Spacer()
                StatsWinLostTotalLabel(
                    amount: Int(lost?.value ?? 0),
                    color: CColors.red,
                    percentage: Int(lost?.percentage ?? 0),
                    name: "stats_lost"
                )
                Spacer()
                StatsWinLostTotalLabel(
                    amount: Int(played?.value ?? 0),
                    color: CThemeColors(colorScheme: colorScheme).textDefault,
                    percentage: Int(played?.percentage ?? 0),
                    name: "stats_played \(Int(played?.total ?? 0), specifier: "%d")"
                )
            }
            .padding([.bottom], cSpace32)
            // bars title
            CText(
                key: "stats_graph_title",
                fontSize: cFontSize16
            )
            .padding([.bottom], cSpace16)
            // bars
            StatsDistributionBars(
                percentage: distBarsPercentages,
                values: distBarsValues,
                maxIdx: distBarsMaxIdx
            )
        }
    }

    @ViewBuilder func loading() -> some View {
        HStack(
            spacing: 0
        ) {
            Spacer()
            CLoader()
            Spacer()
        }
        .padding([.top], cSpace32)
    }
}
