//
//  Tutorial.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 30/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct Tutorial: View {
    var body: some View {
        ScrollView {
            VStack(
                alignment: .leading,
                spacing: 0
            ) {
                CSheetTitle(
                    title: "kh_title"
                )
                CText(
                    key: "kh_subtitle",
                    font: CFont.ceraMedium,
                    fontSize: cFontSize16
                )
                .padding([.bottom], cSpace16)
                TutorialIntroBullets()
                TutorialErrorRows()
                CText(
                    key: "kh_ex_title",
                    fontSize: cFontSize20
                )
                .padding([.bottom], cSpace16)
                TutorialBulletsExample()
                TutorialExampleDescrip()
                    .padding([.bottom], cSpace16)
                CText(
                    key: "kh_ex_text_2",
                    font: .ceraRegular
                )
                .padding([.bottom], cSpace32)
                TutorialDifficultyGraph()
            }
        }
    }
}

#Preview {
    Tutorial()
}
