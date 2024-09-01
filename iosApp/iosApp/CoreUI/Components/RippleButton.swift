//
//  RippleButton.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 19/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct RippleButton<Content: View>: View {
    let action: () -> Void
    let longPressAction: (() -> Void)?
    let content: () -> Content
    let transparent: Bool
    let transparentPadding: Double
    let fixedFeedback: Bool

    public init(
        transparent: Bool = true,
        transparentPadding: Double = 0.0,
        fixedFeedback: Bool = false,
        action: @escaping () -> Void,
        longPressAction: (() -> Void)? = nil,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.action = action
        self.transparent = transparent
        self.transparentPadding = transparentPadding
        self.fixedFeedback = fixedFeedback
        self.content = content
        self.longPressAction = longPressAction
    }

    @Environment(\.colorScheme) var colorScheme

    public var body: some View {
        Button(
            action: {},
            label: {
                content()
                    .onTapGesture {
                        action()
                    }
            }
        ).buttonStyle(
            RippleButtonStyle(
                transparent: transparent,
                transparentPadding: transparentPadding,
                fixedFeedback: fixedFeedback,
                rippleColor: CThemeColors(colorScheme: colorScheme).screenBackground
            )
        )
    }
}

public struct RippleButtonStyle: ButtonStyle {
    let transparent: Bool
    let transparentPadding: Double
    let fixedFeedback: Bool
    let rippleColor: Color
    let opacityRippleColor = 0.7

    init(
        transparent: Bool,
        transparentPadding: Double,
        fixedFeedback: Bool,
        rippleColor: Color
    ) {
        self.transparent = transparent
        self.transparentPadding = transparentPadding
        self.fixedFeedback = fixedFeedback
        self.rippleColor = rippleColor
    }

    public func makeBody(
        configuration: Configuration
    ) -> some View {
        let label = configuration.label
        let isPressed = configuration.isPressed
        if transparent {
            let color = rippleColor.opacity(opacityRippleColor)
            label
                .padding(.all, transparentPadding)
                .background(
                    fixedFeedback ? color :
                        isPressed ? color : color.opacity(0.0)
                )
        } else {
            label.opacity(
                fixedFeedback ? opacityRippleColor :
                    isPressed ? opacityRippleColor : 1
            )
        }
    }
}

struct RippleButton_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            RippleButton(
                transparent: true,
                action: {},
                content: {
                    Text("transparent: true")
                }
            )
            RippleButton(
                transparent: false,
                action: {},
                content: {
                    Rectangle()
                        .fill(.red)
                        .frame(width: 200, height: 100)
                        .overlay {
                            Text("transparent: false")
                        }
                }
            )
            RippleButton(
                transparent: true,
                fixedFeedback: true,
                action: {},
                content: {
                    Text("transparent: true\nfixedFeedback: true")
                }
            )
            RippleButton(
                transparent: false,
                fixedFeedback: true,
                action: {},
                content: {
                    Rectangle()
                        .fill(.red)
                        .frame(width: 200, height: 100)
                        .overlay {
                            Text("transparent: false\nfixedFeedback: true")
                        }
                }
            )
        }
        .background(Color.blue)
    }
}
