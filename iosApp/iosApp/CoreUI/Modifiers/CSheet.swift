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
    let body: () -> SheetBody
    let usePresentationDetents: Bool

    init(
        isPresented: Binding<Bool>,
        onDismiss: @escaping () -> Void,
        usePresentationDetents: Bool = false,
        body: @escaping () -> SheetBody
    ) {
        self.isPresented = isPresented
        self.onDismiss = onDismiss
        self.body = body
        self.usePresentationDetents = usePresentationDetents
    }

    @Environment(\.colorScheme) var colorScheme

    func body(content: Content) -> some View {
        content
            .sheet(
                isPresented: isPresented,
                onDismiss: onDismiss
            ) {
                if #available(iOS 16.0, *), usePresentationDetents {
                    sheetBody()
                        .presentationDetents([.medium, .large])
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
