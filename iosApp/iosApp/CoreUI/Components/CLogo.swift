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
        Image(
            "svg_logo"
        )
        .resizable()
        .aspectRatio(contentMode: .fit)
        .clipShape(RoundedRectangle(cornerRadius: cBorderRadius4))
        .frame(
            width: size,
            height: size
        )
    }
}

#Preview {
    CLogo()
}
