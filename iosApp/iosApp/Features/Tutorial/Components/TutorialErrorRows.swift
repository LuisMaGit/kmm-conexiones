//
//  TutorialErrorRows.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 31/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TutorialErrorRows: View {
    var body: some View {
        TutorialErrorRow(
            errors: 0,
            hearts: 3,
            errorKey: "kh_errors"
        )
        .padding([.top], cSpace16)
        .padding([.bottom], cSpace8)
        TutorialErrorRow(
            errors: 1,
            hearts: 2,
            errorKey: "kh_error"
        )
        .padding([.bottom], cSpace8)
        TutorialErrorRow(
            errors: 2,
            hearts: 1,
            errorKey: "kh_errors"
        )
        .padding([.bottom], cSpace8)
        TutorialErrorRow(
            errors: 3,
            hearts: 1,
            errorKey: "kh_errors"
        )
        .padding([.bottom], cSpace16)
    }
}

struct TutorialErrorRow: View {
    let errors: Int
    let hearts: Int
    let errorKey: LocalizedStringKey

    init(
        errors: Int,
        hearts: Int,
        errorKey: LocalizedStringKey
    ) {
        self.errors = errors
        self.hearts = hearts
        self.errorKey = errorKey
    }

    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        let background = CThemeColors(colorScheme: colorScheme).screenBackground
        HStack(
            spacing: 0
        ) {
            Spacer()
            ZStack {
                CText(
                    text: String(errors),
                    font: CFont.ceraBold,
                    fontSize: cFontSize14,
                    fixedFont: true
                )
            }
            .frame(width: 20)
            ZStack(
                alignment: .topLeading
            ) {
                CThemeColors(colorScheme: colorScheme).screenBackground
                CText(
                    key: errorKey,
                    font: CFont.ceraRegular,
                    fontSize: cFontSize14,
                    fixedFont: true
                )
            }
            .frame(width: 60)
            ZStack {
                CText(
                    text: "=",
                    font: CFont.ceraBold,
                    fontSize: cFontSize14,
                    fixedFont: true
                )
            }
            .frame(width: 20)
            ZStack(
                alignment: .topLeading
            ) {
                background
                HStack(
                    spacing: 0
                ) {
                    ForEach(
                        0 ..< hearts,
                        id: \.self
                    ) { _ in
                        CIcon(
                            type: .heartFilled16px,
                            width: 16,
                            height: 16
                        )
                    }
                }
            }
            .frame(width: 50)
            Spacer()
        }
    }
}
