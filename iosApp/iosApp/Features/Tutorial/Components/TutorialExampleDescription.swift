//
//  TutorialExampleDescription.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 31/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TutorialExampleDescrip: View {
    let defaultFont = Font.custom(
        CFont.ceraRegular.rawValue,
        size: cFontSize14
    )
    let boldFont = Font.custom(
        CFont.ceraBold.rawValue,
        size: cFontSize14
    )

    @Environment(\.colorScheme) var colorScheme
    var body: some View {
        let space = baseText(key: " ")
            .font(defaultFont)
        let coma = baseText(key: ", ")
            .font(defaultFont)
        let or = baseText(key: " o ")
            .font(defaultFont)
        let dot = baseText(key: ".")
            .font(defaultFont)
        baseText(key: "kh_ex_text_1")
            .font(defaultFont)
            + space
            + baseText(key: "kh_ex_text_1_substring_1")
            .font(boldFont)
            + coma
            + baseText(key: "kh_ex_text_1_substring_2")
            .font(boldFont)
            + or
            + baseText(key: "kh_ex_text_1_substring_3")
            .font(boldFont)
            + dot
    }

    @ViewBuilder func baseText(key: LocalizedStringKey) -> Text {
        Text(key)
            .foregroundColor(
                CThemeColors(colorScheme: colorScheme).textDefault
            )
    }
}
