import SwiftUI

struct LobbyView: View {
    @ObservedObject var viewModel: GameViewModel
    @EnvironmentObject private var router: NavigationRouter

    var body: some View {
        ZStack {
            Color(hex: "0F0E1A").ignoresSafeArea()

            ScrollView {
                VStack(spacing: 24) {
                    if viewModel.roomCode == nil {
                        if viewModel.isLoading {
                            ProgressView().tint(Color(hex: "7C4DFF")).padding(.top, 40)
                        } else {
                            Text("Create a new game to get a room code")
                                .font(.subheadline)
                                .foregroundStyle(.white.opacity(0.6))
                                .multilineTextAlignment(.center)
                                .padding(.top, 40)

                            Button {
                                viewModel.createGame()
                            } label: {
                                Label("New Game", systemImage: "plus")
                                    .font(.headline)
                                    .frame(maxWidth: .infinity).frame(height: 52)
                                    .background(Color(hex: "7C4DFF"))
                                    .foregroundStyle(.white)
                                    .clipShape(RoundedRectangle(cornerRadius: 12))
                            }
                        }
                    } else {
                        VStack(spacing: 4) {
                            Text("Room Code")
                                .font(.caption.weight(.semibold))
                                .foregroundStyle(.white.opacity(0.5))
                                .textCase(.uppercase)
                            Text(viewModel.roomCode ?? "")
                                .font(.system(size: 52, weight: .black, design: .monospaced))
                                .foregroundStyle(Color(hex: "7C4DFF"))
                                .tracking(8)
                            Text("Share this with your players")
                                .font(.caption)
                                .foregroundStyle(.white.opacity(0.5))
                        }

                        Divider().overlay(Color.white.opacity(0.1))

                        VStack(alignment: .leading, spacing: 10) {
                            HStack {
                                Text("Players")
                                    .font(.headline).foregroundStyle(.white)
                                Spacer()
                                Text("\(viewModel.players.count) joined")
                                    .font(.caption).foregroundStyle(.white.opacity(0.5))
                            }

                            if viewModel.players.isEmpty {
                                Text("Waiting for players to join...")
                                    .font(.subheadline)
                                    .foregroundStyle(.white.opacity(0.5))
                                    .frame(maxWidth: .infinity)
                                    .padding()
                                    .background(Color(hex: "1C1A2E"))
                                    .clipShape(RoundedRectangle(cornerRadius: 12))
                            } else {
                                ForEach(viewModel.players) { player in
                                    HStack(spacing: 12) {
                                        Circle()
                                            .fill(Color(hex: "7C4DFF").opacity(0.2))
                                            .frame(width: 36, height: 36)
                                            .overlay(
                                                Text(String(player.name.prefix(1)).uppercased())
                                                    .font(.headline)
                                                    .foregroundStyle(Color(hex: "7C4DFF"))
                                            )
                                        Text(player.name)
                                            .font(.headline).foregroundStyle(.white)
                                    }
                                    .padding()
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .background(Color(hex: "1C1A2E"))
                                    .clipShape(RoundedRectangle(cornerRadius: 12))
                                }
                            }
                        }

                        Button {
                            viewModel.startGame()
                        } label: {
                            Text("Start Game  ▶")
                                .font(.headline)
                                .frame(maxWidth: .infinity).frame(height: 56)
                                .background(viewModel.players.isEmpty ? Color.gray : Color(hex: "7C4DFF"))
                                .foregroundStyle(.white)
                                .clipShape(RoundedRectangle(cornerRadius: 14))
                        }
                        .disabled(viewModel.players.isEmpty)
                    }
                }
                .padding(24)
            }
        }
        .navigationTitle("Game Lobby")
        .navigationBarTitleDisplayMode(.inline)
        .onChange(of: viewModel.gameStatus) { _, status in
            if status == "active" { router.path.append(.question) }
        }
    }
}
