//
//  CButton.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 20/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CButton: View {
    let color: Color
    let width: CGFloat?
    let svg: CSVGType
    let key: LocalizedStringKey
    let onTap: () -> Void
    let disabled: Bool

    let iconSize = 12.0
    let offsetBackBox = 5.0
    let heightBox = 44.0

    init(
        color: Color = CColors.green,
        width: CGFloat? = nil,
        svg: CSVGType = .question,
        key: LocalizedStringKey = "",
        disabled: Bool = false,
        onTap: @escaping () -> Void
    ) {
        self.color = color
        self.width = width
        self.svg = svg
        self.key = key
        self.disabled = disabled
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
                if !disabled {
                    content()
                        .offset(y: -2)
                } else {
                    content()
                }
            }
            .frame(
                width: width,
                height: heightBox + offsetBackBox
            )
        }
    }

    @ViewBuilder func content() -> some View {
        HStack {
            CIcon(
                type: svg,
                width: iconSize,
                height: iconSize,
                color: CColors.black
            )
            CText(
                key: key,
                color: CColors.black,
                fixedFont: true
            )
        }
    }

    @ViewBuilder func backRect() -> some View {
        if !disabled {
            RoundedRectangle(
                cornerRadius: cBorderRadius4
            )
            .fill(CColors.black)
            .frame(
                width: width,
                height: heightBox + offsetBackBox
            )
        }
    }

    @ViewBuilder func frontRect(width: Double) -> some View {
        if !disabled {
            frontRectWithoutBorder(width: width, height: heightBox)
                .background(
                    RoundedRectangle(
                        cornerRadius: cBorderRadius4,
                        style: .continuous
                    )
                    .stroke(CColors.black, lineWidth: 2)
                )
        } else {
            frontRectWithoutBorder(width: width, height: heightBox + offsetBackBox)
        }
    }

    @ViewBuilder func frontRectWithoutBorder(width: Double, height: Double) -> some View {
        RoundedRectangle(
            cornerRadius: cBorderRadius4
        )
        .fill(color)
        .frame(
            width: width,
            height: height
        )
    }
}

#Preview {
    VStack {
        CButton(
            width: 100,
            svg: .broom,
            key: "Enabled",
            onTap: {}
        )
        CButton(
            width: 100,
            svg: .broom,
            key: "Disabled",
            disabled: true,
            onTap: {}
        )
    }
}
