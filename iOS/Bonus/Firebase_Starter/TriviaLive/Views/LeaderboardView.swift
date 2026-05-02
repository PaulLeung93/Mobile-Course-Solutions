import SwiftUI

private let medals = ["🥇", "🥈", "🥉"]

struct LeaderboardView: View {
    @ObservedObject var viewModel: GameViewModel
    @EnvironmentObject private var router: NavigationRouter

    var body: some View {
        ZStack {
            Color(hex: "0F0E1A").ignoresSafeArea()

            VStack(spacing: 0) {
                Text("🏆")
                    .font(.system(size: 52))
                    .padding(.top, 32)
                Text("Final Scores")
                    .font(.title.bold())
                    .foregroundStyle(.white)
                    .padding(.top, 8)
                    .padding(.bottom, 24)

                if viewModel.leaderboard.isEmpty {
                    Text("No scores to show.\nMake sure you completed loadLeaderboard() in Part 5.")
                        .font(.subheadline)
                        .foregroundStyle(.white.opacity(0.5))
                        .multilineTextAlignment(.center)
                        .padding(.horizontal, 32)
                } else {
                    ScrollView {
                        VStack(spacing: 8) {
                            ForEach(viewModel.leaderboard) { entry in
                                let medal = medals.indices.contains(entry.rank - 1)
                                    ? medals[entry.rank - 1]
                                    : "  \(entry.rank)."

                                HStack(spacing: 12) {
                                    Text(medal)
                                        .font(.system(size: 22))
                                        .frame(width: 32)
                                    Text(entry.name)
                                        .font(.headline)
                                        .foregroundStyle(.white)
                                    Spacer()
                                    Text("\(entry.score) pts")
                                        .font(.headline)
                                        .foregroundStyle(Color(hex: "7C4DFF"))
                                }
                                .padding(.horizontal, 20)
                                .padding(.vertical, 14)
                                .background(
                                    entry.rank == 1
                                        ? Color(hex: "7C4DFF").opacity(0.2)
                                        : Color(hex: "1C1A2E")
                                )
                                .clipShape(RoundedRectangle(cornerRadius: 12))
                                .padding(.horizontal, 24)
                            }
                        }
                    }
                }

                Spacer()

                Button {
                    viewModel.signOut()
                    router.popToRoot()
                } label: {
                    Text("Play Again")
                        .font(.headline)
                        .frame(maxWidth: .infinity).frame(height: 56)
                        .background(Color(hex: "7C4DFF"))
                        .foregroundStyle(.white)
                        .clipShape(RoundedRectangle(cornerRadius: 14))
                }
                .padding(.horizontal, 24)
                .padding(.bottom, 32)
            }
        }
        .navigationTitle("Leaderboard")
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarBackButtonHidden(true)
    }
}
