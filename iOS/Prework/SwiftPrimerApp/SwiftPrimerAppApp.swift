import SwiftUI

@main
struct SwiftPrimerAppApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                // Force dark mode for a consistent aesthetic as defined in the Theme
                .preferredColorScheme(.dark)
        }
    }
}
