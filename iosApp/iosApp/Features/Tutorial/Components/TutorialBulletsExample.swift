//
//  TutorialBulletsExample.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 31/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TutorialBulletsExample: View {
    var body: some View {
        TutorialBulletExample(
            titleKey: "kh_ex_bullet_1",
            descKey: "kh_ex_words_1"
        )
        TutorialBulletExample(
            titleKey: "kh_ex_bullet_2",
            descKey: "kh_ex_words_2"
        )
        .padding([.bottom], cSpace16)
    }
}

struct TutorialBulletExample: View {
    let titleKey: LocalizedStringKey
    let descKey: LocalizedStringKey

    init(titleKey: LocalizedStringKey, descKey: LocalizedStringKey) {
        self.titleKey = titleKey
        self.descKey = descKey
    }

    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        let defaultFont = Font.custom(
            CFont.ceraRegular.rawValue,
            size: cFontSize12
        )
        let boldFont = Font.custom(
            CFont.ceraBold.rawValue,
            size: cFontSize12
        )
        TutorialBullet {
            baseText(key: titleKey)
                .font(boldFont)
                + baseText(key: "  ")
                + baseText(key: descKey)
                .font(defaultFont)
        }
    }

    @ViewBuilder func baseText(key: LocalizedStringKey) -> Text {
        Text(key)
            .foregroundColor(
                CThemeColors(colorScheme: colorScheme).textDefault
            )
    }
}
