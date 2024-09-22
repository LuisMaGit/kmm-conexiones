//
//  Lives.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 2/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct Lives: View {
    @StateObject var vm = LivesViewModel()

    var body: some View {
        if vm.state.screenState == .loading {
            loading()
        } else {
            success()
        }
    }

    @ViewBuilder func loading() -> some View {
        VStack(
            alignment: .leading,
            spacing: 0
        ) {
            CSheetTitle(title: "lives_lives_title")
            Spacer()
            HStack(
                spacing: 0
            ) {
                Spacer()
                CLoader()
                Spacer()
            }
            Spacer()
        }
    }

    @ViewBuilder func success() -> some View {
        let zeroLives = vm.state.lives == 0
        let iconSize = 40.0
        VStack(
            alignment: .leading,
            spacing: 0
        ) {
            // title
            CSheetTitle(
                title: zeroLives ? "lives_no_lives_title" : "lives_lives_title"
            )
            // body
            ScrollView {
                VStack(
                    alignment: .leading,
                    spacing: 0
                ) {
                    // heart + lives
                    HStack(
                        alignment: .center,
                        spacing: cSpace4
                    ) {
                        Spacer()
                        CIcon(
                            type: zeroLives ? .heart : .heartFilled,
                            width: iconSize,
                            height: iconSize,
                            color: zeroLives ? nil : CColors.green
                        )
                        CText(
                            text: "x\(String(vm.state.lives))",
                            fontSize: cFontSize32
                        )
                        Spacer()
                    }
                    .padding([.bottom], cSpace16)
                    // description
                    CText(
                        key: zeroLives ? "lives_no_lives_subtitle" : "lives_lives_subtitle",
                        fontSize: zeroLives ? cFontSize16 : cFontSize20
                    )
                    .padding([.bottom], cSpace16)
                    Spacer()
                }
            }
            // button
            CButton(
                color: CColors.orange,
                svg: .heartFilled,
                key: "+1",
                onTap: {}
            )
        }
    }
}

#Preview {
    Lives()
}
