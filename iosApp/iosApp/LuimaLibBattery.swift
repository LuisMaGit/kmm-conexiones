//
//  LuimaLibBattery.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 24/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

public struct LBatteryLevelView: View {
    let lUtilsAccess = LUtilsAccess()
    public var body: some View {
        Text("Battery level ios: \(lUtilsAccess.androidOrIOSBatteryLevel())")
    }
    
}
