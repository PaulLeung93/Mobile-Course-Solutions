import SwiftUI

struct HomeView: View {
    var onHostTap: () -> Void = {}
    var onJoinTap:  () -> Void = {}

    @EnvironmentObject private var viewModel: GameViewModel

    var body: some View {
        ZStack {
            LinearGradient(
                colors: [Color(hex: "0F0E1A"), Color(hex: "7C4DFF").opacity(0.25), Color(hex: "0F0E1A")],
                startPoint: .top, endPoint: .bottom
            )
            .ignoresSafeArea()

            VStack(spacing: 16) {
                Text("⚡")
                    .font(.system(size: 64))
                Text("TRIVIA\nLIVE")
                    .font(.system(size: 52, weight: .black))
                    .multilineTextAlignment(.center)
                    .foregroundStyle(.white)
                Text("Multiplayer trivia with your crew")
                    .font(.subheadline)
                    .foregroundStyle(.white.opacity(0.6))

                Spacer().frame(height: 24)

                NavigationLink(value: AppRoute.auth) {
                    Label("Host a Game", systemImage: "gamecontroller.fill")
                        .font(.headline)
                        .frame(maxWidth: .infinity)
                        .frame(height: 56)
                        .background(Color(hex: "7C4DFF"))
                        .foregroundStyle(.white)
                        .clipShape(RoundedRectangle(cornerRadius: 14))
                }

                NavigationLink(value: AppRoute.join) {
                    Label("Join a Game", systemImage: "person.wave.2.fill")
                        .font(.headline)
                        .frame(maxWidth: .infinity)
                        .frame(height: 56)
                        .overlay(
                            RoundedRectangle(cornerRadius: 14)
                                .strokeBorder(Color(hex: "00E5FF"), lineWidth: 1.5)
                        )
                        .foregroundStyle(Color(hex: "00E5FF"))
                }
            }
            .padding(.horizontal, 32)
        }
        .navigationBarHidden(true)
    }
}

// Convenience hex color initializer
extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: .alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let r = Double((int >> 16) & 0xFF) / 255
        let g = Double((int >> 8)  & 0xFF) / 255
        let b = Double(int         & 0xFF) / 255
        self.init(red: r, green: g, blue: b)
    }
}
