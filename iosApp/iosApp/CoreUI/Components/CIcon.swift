//
//  CIcon.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 27/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public enum CSVGType: String {
    case arrowLeft = "svg_arrow_left"
    case arrowRight = "svg_arrow_right"
    case arrowUp = "svg_arrow_up"
    case broom = "svg_broom"
    case chart = "svg_chart"
    case heartFilled = "svg_heart_filled"
    case heart = "svg_heart"
    case lock = "svg_lock"
    case unlock = "svg_unlock"
    case question = "svg_question"
    case rocket = "svg_rocket"
    case shuffle = "svg_shuffle"
}

public struct CIcon: View {
    let type: CSVGType
    let width: Double
    let height: Double
    let color: Color?

    static let defaultSize = 24.0

    init(
        type: CSVGType,
        width: Double = defaultSize,
        height: Double = defaultSize,
        color: Color? = nil
    ) {
        self.type = type
        self.width = width
        self.height = height
        self.color = color
    }

    @Environment(\.colorScheme) var colorScheme
    
    public var body: some View {
        var getColor: Color {
            if let color = color {
                return color
            }

            return CThemeColors(colorScheme: colorScheme).iconDefault
        }

        Image(
            type.rawValue
        )
        .resizable()
        .aspectRatio(contentMode: .fit)
        .foregroundColor(getColor)
        .frame(
            width: width,
            height: height
        )
    }
}

struct CIcon_Previews: PreviewProvider {
    static var previews: some View {
        CIcon(type: .rocket)
    }
}
