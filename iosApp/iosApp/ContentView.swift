import shared
import SwiftUI

struct ContentView: View {
    @State var value: Bool = false

    var body: some View {
        Levels()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
