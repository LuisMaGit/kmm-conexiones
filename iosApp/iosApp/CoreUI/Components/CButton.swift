//
//  CButton.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 20/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum CButtonContracts {
    static let BORDER_R_BOX = 4.0
    static let HEIGHT_BOX = 44.0
    static let OFFSET_BACK_BOX = 5.0
    static let SIZE_ICON = 12.0
}

struct CButton: View {
    let color: Color
    let width: Double
    let svg: CSVGType
    let key: LocalizedStringKey
    let onTap: () -> Void

    init(
        color: Color = CColors.green,
        width: Double = .infinity,
        svg: CSVGType = .question,
        key: LocalizedStringKey = "",
        onTap: @escaping () -> Void
    ) {
        self.color = color
        self.width = width
        self.svg = svg
        self.key = key
        self.onTap = onTap
    }

    var body: some View {
        RippleButton(
            transparent: false,
            action: onTap
        ) {
            ZStack {
                backRect()
                GeometryReader { geo in
                    frontRect(width: geo.size.width - 2)
                        .offset(x: 1)
                }
                content()
                    .offset(y: -2)
            }
            .frame(
                width: width,
                height: CButtonContracts.HEIGHT_BOX + CButtonContracts.OFFSET_BACK_BOX
            )
        }
    }

    @ViewBuilder func content() -> some View {
        HStack {
            CIcon(
                type: svg,
                width: CButtonContracts.SIZE_ICON,
                height: CButtonContracts.SIZE_ICON
            )
            CText(key: key, fixedFont: true)
        }
    }

    @ViewBuilder func backRect() -> some View {
        RoundedRectangle(
            cornerRadius: CButtonContracts.BORDER_R_BOX
        )
        .fill(CColors.black)
        .frame(
            width: width,
            height: CButtonContracts.HEIGHT_BOX + CButtonContracts.OFFSET_BACK_BOX
        )
    }

    @ViewBuilder func frontRect(width: Double) -> some View {
        RoundedRectangle(
            cornerRadius: CButtonContracts.BORDER_R_BOX
        )
        .fill(color)
        .background(
            RoundedRectangle(
                cornerRadius: CButtonContracts.BORDER_R_BOX,
                style: .continuous
            )
            .stroke(CColors.black, lineWidth: 2)
        )
        .frame(
            width: width,
            height: CButtonContracts.HEIGHT_BOX
        )
    }
}

#Preview {
    CButton(
        width: 300,
        svg: .broom,
        key: "Clear",
        onTap: {}
    )
}
