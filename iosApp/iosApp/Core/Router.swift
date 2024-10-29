//
//  Router.swift
//  iosApp
//
//  Created by Luis Manuel García Fernández on 29/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum Routes : Hashable {
    case Levels
    case Game(gameId: Int)
    case MockAdd
}

class Router: ObservableObject {
    @Published public private(set) var route: Routes
    public static let instance: Router = .init()
    
    private var routes : Array<Routes> = [.Levels]
    private let firstRoute: Routes

    init(
        route: Routes = .Levels
    ) {
        self.route = route
        self.firstRoute = route
    }

    func goTo(page: Routes) async {
        if page == route {
            return
        }
        await MainActor.run {
            route = page
            routes.append(route)
        }
    }
    
    func pop() async {
        if routes.count == 1 || routes.isEmpty {
            return
        }
        await MainActor.run {
            routes.removeLast()
            route = routes.last ?? firstRoute
        }
    }
}
