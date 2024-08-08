//
//  Colors.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct CColors {
    private static func getColor(_ named: String) -> Color {
        Color(named)
    }
    
    public static let white = getColor( "white")
    public static let white2 = getColor("white2")
    public static let black = getColor("black")
    public static let grey = getColor("grey")
    public static let green = getColor("green")
    public static let red = getColor("red")
    public static let orange = getColor("orange")
    public static let yellow = getColor("yellow")
}
