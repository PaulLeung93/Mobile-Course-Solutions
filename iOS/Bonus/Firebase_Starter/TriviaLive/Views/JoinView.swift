import SwiftUI

struct JoinView: View {
    @ObservedObject var viewModel: GameViewModel
    @State private var roomCode   = ""
    @State private var playerName = ""
    @EnvironmentObject private var router: NavigationRouter

    var body: some View {
        ZStack {
            Color(hex: "0F0E1A").ignoresSafeArea()

            if viewModel.hasJoined {
                VStack(spacing: 16) {
                    Text("⏳").font(.system(size: 64))
                    Text("You're in!")
                        .font(.title.bold())
                        .foregroundStyle(Color(hex: "00E5FF"))
                    Text("Waiting for the host to start the game...")
                        .font(.subheadline)
                        .foregroundStyle(.white.opacity(0.6))
                        .multilineTextAlignment(.center)
                }
                .padding(.horizontal, 32)
            } else {
                VStack(spacing: 20) {
                    VStack(spacing: 6) {
                        Text("Join a Game")
                            .font(.title.bold()).foregroundStyle(.white)
                        Text("Enter the room code from your host")
                            .font(.subheadline)
                            .foregroundStyle(.white.opacity(0.6))
                    }
                    .padding(.bottom, 8)

                    TextField("Room Code", text: $roomCode)
                        .textCase(.uppercase)
                        .autocapitalization(.allCharacters)
                        .disableAutocorrection(true)
                        .font(.system(size: 28, weight: .black, design: .monospaced))
                        .multilineTextAlignment(.center)
                        .tracking(6)
                        .onChange(of: roomCode) { _, v in roomCode = String(v.uppercased().prefix(6)) }
                        .padding()
                        .background(Color(hex: "1C1A2E"))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .foregroundStyle(.white)

                    TextField("Your Name", text: $playerName)
                        .disableAutocorrection(true)
                        .onChange(of: playerName) { _, v in playerName = String(v.prefix(20)) }
                        .textFieldStyle(.plain)
                        .padding()
                        .background(Color(hex: "1C1A2E"))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .foregroundStyle(.white)

                    if let error = viewModel.errorMessage {
                        Text(error).font(.caption).foregroundStyle(.red)
                    }

                    if viewModel.isLoading {
                        ProgressView().tint(Color(hex: "7C4DFF"))
                    } else {
                        Button {
                            viewModel.joinGame(roomCode: roomCode, playerName: playerName)
                        } label: {
                            Text("Join Game")
                                .font(.headline)
                                .frame(maxWidth: .infinity).frame(height: 56)
                                .background(roomCode.count == 6 && !playerName.isEmpty
                                    ? Color(hex: "7C4DFF") : Color.gray)
                                .foregroundStyle(.white)
                                .clipShape(RoundedRectangle(cornerRadius: 14))
                        }
                        .disabled(roomCode.count != 6 || playerName.isEmpty)
                    }
                }
                .padding(.horizontal, 28)
            }
        }
        .navigationTitle("Join")
        .navigationBarTitleDisplayMode(.inline)
        .onChange(of: viewModel.gameStatus) { _, status in
            if status == "active" { router.path.append(.question) }
        }
    }
}
