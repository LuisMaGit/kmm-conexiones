//
//  GameControls.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 11/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


struct GameControls: View {
    @EnvironmentObject var vm: GameViewModel

    let gridWidth: Double

    init(gridWidth: Double) {
        self.gridWidth = gridWidth
    }

    var body: some View {
        let btnSmallW = gridWidth / 2 - cSpace4

        VStack(
            spacing: 0
        ) {
            HStack(
                spacing: 0
            ) {
                CButton(
                    color: CColors.white,
                    width: btnSmallW,
                    svg: .broom,
                    key: "level_btn_uncheck",
                    disabled: vm.state.canDeselectWords,
                    onTap: {
                        vm.sendEvent(event: .clearSelection)
                    }
                )
                Spacer()
                CButton(
                    color: CColors.white,
                    width: btnSmallW,
                    svg: .shuffle,
                    key: "level_btn_rotate",
                    onTap: {
                        vm.sendEvent(event: .mixGame)
                    }
                )
            }
            CButton(
                width: gridWidth,
                svg: .rocket,
                key: "level_btn_send",
                disabled: !vm.state.canSendRow,
                onTap: {
                    vm.sendEvent(event: .submit)
                }
            )
            .padding([.top], cSpace16)
            
        }
        .frame(width: gridWidth)
    }
}
