//
//  CLoader.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 23/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CLoader: View {
    @State var animate: Bool = false

    var body: some View {
        CLogo()
            .rotation3DEffect(
                .degrees(animate ? 0 : 360),
                axis: (x: animate ? 1 : 0, y: 0, z: 0)
            )
            .onAppear {
                withAnimation(.easeInOut(duration: 1).repeatForever(autoreverses: true)) {
                    animate = true
                }
            }
    }
}

#Preview {
    CLoader()
}
