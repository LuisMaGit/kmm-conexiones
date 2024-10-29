//
//  ScreenSizeHelper.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 10/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


struct ScreenSizeHelper<Content: View>: View {
    let content: (_ breakPoints: ScreenSizeBreakPoints, _ width: Double) -> Content
    let width = UIScreen.main.bounds.size.width
    var body: some View {
        if width > 0 && width < 700 {
            content(.mobile, width)
        } else {
            content(.tablet, width)
        }
    }
}

enum ScreenSizeBreakPoints {
    case mobile
    case tablet
}

