import SwiftUI

// Part 1 — Add this import after adding the Firebase SDK:
// import FirebaseCore

@main
struct TriviaLiveApp: App {

    init() {
        // Part 1 — Uncomment after adding GoogleService-Info.plist:
        // FirebaseApp.configure()
    }

    var body: some Scene {
        WindowGroup {
            AppNavigation()
        }
    }
}
