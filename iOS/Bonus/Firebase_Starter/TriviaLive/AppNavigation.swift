import SwiftUI

struct AppNavigation: View {
    @StateObject private var viewModel = GameViewModel()
    @StateObject private var router   = NavigationRouter()

    var body: some View {
        NavigationStack(path: $router.path) {
            HomeView()
                .navigationDestination(for: AppRoute.self) { route in
                    switch route {
                    case .auth:        HostLoginView(viewModel: viewModel)
                    case .lobby:       LobbyView(viewModel: viewModel)
                    case .join:        JoinView(viewModel: viewModel)
                    case .question:    QuestionView(viewModel: viewModel)
                    case .leaderboard: LeaderboardView(viewModel: viewModel)
                    }
                }
        }
        .environmentObject(viewModel)
        .environmentObject(router)
        .preferredColorScheme(.dark)
    }
}

enum AppRoute: Hashable {
    case auth, lobby, join, question, leaderboard
}
