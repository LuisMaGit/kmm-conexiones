//
//  TutorialBullet.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 31/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TutorialBullet<Content: View>: View {
    let content: () -> Content

    init(
        content: @escaping () -> Content
    ) {
        self.content = content
    }

    var body: some View {
        HStack {
            CText(
                text: "•"
            )
            .padding([.trailing], cSpace4)
            content()
        }
    }
}
