//
//  CSheetModifier.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 30/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CSheet<SheetBody: View>: ViewModifier {
    let isPresented: Binding<Bool>
    let onDismiss: () -> Void
    let useCustomHeight: Bool
    let customHeightFraction: CGFloat
    let body: () -> SheetBody

    init(
        isPresented: Binding<Bool>,
        onDismiss: @escaping () -> Void,
        useCustomHeight: Bool = false,
        customHeightFraction: CGFloat = 0.5,
        body: @escaping () -> SheetBody
    ) {
        self.isPresented = isPresented
        self.onDismiss = onDismiss
        self.body = body
        self.useCustomHeight = useCustomHeight
        self.customHeightFraction = customHeightFraction
    }

    @Environment(\.colorScheme) var colorScheme

    func body(content: Content) -> some View {
        content
            .sheet(
                isPresented: isPresented,
                onDismiss: onDismiss
            ) {
               if #available(iOS 16.0, *), useCustomHeight {
                    sheetBody()
                        .presentationDetents([.fraction(customHeightFraction)])

                } else {
                    sheetBody()
                }
            }
    }

    @ViewBuilder func sheetBody() -> some View {
        ZStack {
            CThemeColors(colorScheme: colorScheme).screenBackground
            body()
                .padding(
                    EdgeInsets(
                        top: cSpace32,
                        leading: cSpace16,
                        bottom: cSpace16,
                        trailing: cSpace16
                    )
                )
        }
    }
}

struct CSheetTitle: View {
    let title: LocalizedStringKey
    init(title: LocalizedStringKey) {
        self.title = title
    }

    var body: some View {
        CText(
            key: title,
            fontSize: cFontSize32
        )
        .padding([.bottom], cSpace32)
    }
}
