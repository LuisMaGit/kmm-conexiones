//
//  TutorialIntroBullets.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 31/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TutorialIntroBullets: View {
    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        let defaultFont = Font.custom(
            CFont.ceraRegular.rawValue,
            size: cFontDefaultSize
        )
        let boldFont = Font.custom(
            CFont.ceraBold.rawValue,
            size: cFontDefaultSize
        )
        let space = baseText(key: " ")
        TutorialBullet {
            baseText(
                key: "kh_bullet_1"
            )
            .font(defaultFont)
            +
            space
            +
            baseText(key: "kh_bullet_1_substring_1")
            .font(boldFont)
            +
            space
            +
            baseText(key: "kh_bullet_1_substring_2")
            .font(defaultFont)
        }
        .padding([.bottom], cSpace8)
        TutorialBullet {
            baseText(
                key: "kh_bullet_2"
            )
            .font(defaultFont)
        }
        .padding([.bottom], cSpace8)
        TutorialBullet {
            baseText(
                key: "kh_bullet_3"
            )
            .font(defaultFont)
            +
            space
            +
            Text(
                Image(
                    CSVGType.heartFilled16px.rawValue
                )
            )
            +
            baseText(key: " .")
        }
        .padding([.bottom], cSpace8)
        TutorialBullet {
            baseText(
                key: "kh_bullet_4"
            )
            .font(defaultFont)
        }
    }

    @ViewBuilder func baseText(key: LocalizedStringKey) -> Text {
        Text(key)
            .foregroundColor(CThemeColors(colorScheme: colorScheme).textDefault)
    }
}
