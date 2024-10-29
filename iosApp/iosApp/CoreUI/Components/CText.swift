//
//  CText.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI



public struct CText: View {
    let key: LocalizedStringKey?
    let text: String?
    let color: Color?
    let font: CFont?
    let fontSize: CGFloat?
    let lineLimit: Int?
    let fixedFont: Bool

    init(
        key: LocalizedStringKey? = nil,
        text: String? = nil,
        color: Color? = nil,
        font: CFont? = nil,
        fontSize: CGFloat? = nil,
        lineLimit: Int? = nil,
        fixedFont: Bool = false
    ) {
        self.key = key
        self.text = text
        self.color = color
        self.font = font
        self.fontSize = fontSize
        self.lineLimit = lineLimit
        self.fixedFont = fixedFont
    }

    @Environment(\.colorScheme) var colorScheme

    var getColor: Color {
        if let colorNotNill = color {
            return colorNotNill
        }

        return CThemeColors(colorScheme: colorScheme).textDefault
    }

    var getFont: CFont {
        if let fontNotNill = font {
            return fontNotNill
        }

        return .ceraBold
    }

    var getText: Text {
        if let key = key {
            return Text(key)
        }

        if let text = text {
            return Text(text)
        }

        return Text("")
    }

    public var body: some View {
        getText
            .foregroundColor(getColor)
            .font(
                fixedFont
                    ? Font.custom(
                        getFont.rawValue,
                        fixedSize: fontSize ?? cFontDefaultSize
                    )
                    : Font.custom(
                        getFont.rawValue,
                        size: fontSize ?? cFontDefaultSize
                    )
            )
            .lineLimit(lineLimit)
    }
}

struct CText_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            CText(text: "Hello")
            CText(text: "Hello", font: .ceraMedium, fixedFont: true)
            CText(text: "Hello", font: .ceraRegular)
        }
    }
}
