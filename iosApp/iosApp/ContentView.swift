import shared
import SwiftUI

struct ContentView: View {
    @StateObject var router = Router.instance
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        Group {
            switch router.route {
            case .Levels:
                Levels()
            case .Game(let gameId):
                GameView(gameId: gameId)
            case .MockAdd:
                MockAddView()
            }
        }.background {
            CThemeColors(colorScheme: colorScheme).screenBackground.ignoresSafeArea()
        }
    }
}
