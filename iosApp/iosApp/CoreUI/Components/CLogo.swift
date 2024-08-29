//
//  CLogo.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 23/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CLogo: View {
    let size: Double
    
    init(
        size: Double = 40.0
    ) {
        self.size = size
    }
    
    var body: some View {
        Rectangle()
            .fill(CColors.green)
            .frame(width: size, height: size)
    }
}

#Preview {
    CLogo()
}
