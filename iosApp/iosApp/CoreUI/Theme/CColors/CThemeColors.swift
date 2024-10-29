//
//  CColorsTheme.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct CThemeColors {
    let colorScheme: ColorScheme

    public init(colorScheme: ColorScheme) {
        self.colorScheme = colorScheme
    }

    public var screenBackground: Color {
        return CColorsHelper().getColorByTheme(
            lightOrAny: CColors.white2,
            dark: CColors.grey,
            colorScheme: colorScheme
        )
    }

    public var textDefault: Color {
        return CColorsHelper().getColorByTheme(
            lightOrAny: CColors.black,
            dark: CColors.white2,
            colorScheme: colorScheme
        )
    }
    
    public var softContrastScreenBackground: Color {
        return CColorsHelper().getColorByTheme(
            lightOrAny: CColors.white,
            dark: CColors.black,
            colorScheme: colorScheme
        )
    }

    public var iconDefault: Color {
        return CColorsHelper().getColorByTheme(
            lightOrAny: CColors.black,
            dark: CColors.white2,
            colorScheme: colorScheme
        )
    }
}
