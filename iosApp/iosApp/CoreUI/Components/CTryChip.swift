//
//  CTryChip.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 9/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CTryChip: View {
    let visible: Bool

    init(
        visible: Bool
    ) {
        self.visible = visible
    }

    @Environment(\.colorScheme) var colorScheme
    @State var scaleOut = false

    func handleAnimation(visibility: Bool)  {
        withAnimation(.easeOut(duration: 0.1)) {
            scaleOut = !visibility
        }
    }

    let sizeCircle = 16.0
    let lineW = 2.0
    var body: some View {
        ZStack(
            alignment: .center
        ) {
            backCircle()
                .offset(y: 1)
            frontCircle()
        }
        .scaleEffect(
            scaleOut ? 0 : 1
        )
        .onAppear {
            handleAnimation(visibility: visible)
        }
        .onChange(of: visible) { vis in
            handleAnimation(visibility: vis)
        }
    }

    @ViewBuilder func backCircle() -> some View {
        Circle()
            .fill(CColors.black)
            .frame(
                width: sizeCircle + lineW,
                height: sizeCircle + lineW
            )
    }

    @ViewBuilder func frontCircle() -> some View {
        Circle()
            .fill(CColors.white)
            .background(
                Circle()
                    .stroke(CColors.black, lineWidth: lineW)
            )
            .frame(
                width: sizeCircle,
                height: sizeCircle
            )
    }
}
