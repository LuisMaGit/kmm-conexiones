import shared
import SwiftUI

struct ContentView: View {
    @StateObject var router = Router.instance

    var body: some View {
        switch router.route {
        case .Levels:
            Levels()
        case .Game(let gameId):
            GameView(gameId: gameId)
        case .MockAdd:
            MockAddView()
        }
    }
}
