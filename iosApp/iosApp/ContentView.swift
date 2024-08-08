import shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            CText(text: "HELLO")
            CIcon(type: .arrowLeft)
            Button(
                action: {
//                    GameDBService().selectAllGames(completionHandler)
                }
            ) {
                CText(text: "Query db")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
