//
//  CColorsHelpers.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct CColorsHelper {
    
    public func getColorByTheme(
        lightOrAny: Color,
        dark: Color? = nil,
        colorScheme : ColorScheme
    ) -> Color {
        if let darkNotNil = dark, colorScheme == .dark {
            return darkNotNil
        }
        
        return lightOrAny
    }
}
