import SwiftUI

struct HostLoginView: View {
    @ObservedObject var viewModel: GameViewModel
    @State private var email    = ""
    @State private var password = ""
    @EnvironmentObject private var router: NavigationRouter

    var body: some View {
        ZStack {
            Color(hex: "0F0E1A").ignoresSafeArea()

            VStack(spacing: 20) {
                VStack(spacing: 6) {
                    Text("Host Sign In")
                        .font(.title.bold())
                        .foregroundStyle(.white)
                    Text("Sign in or create an account to host a game")
                        .font(.subheadline)
                        .foregroundStyle(.white.opacity(0.6))
                        .multilineTextAlignment(.center)
                }
                .padding(.bottom, 8)

                VStack(spacing: 12) {
                    TextField("Email", text: $email)
                        .textContentType(.emailAddress)
                        .keyboardType(.emailAddress)
                        .autocapitalization(.none)
                        .textFieldStyle(.plain)
                        .padding()
                        .background(Color(hex: "1C1A2E"))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .foregroundStyle(.white)

                    SecureField("Password", text: $password)
                        .textContentType(.password)
                        .textFieldStyle(.plain)
                        .padding()
                        .background(Color(hex: "1C1A2E"))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .foregroundStyle(.white)
                }

                if let error = viewModel.errorMessage {
                    Text(error)
                        .font(.caption)
                        .foregroundStyle(.red)
                        .multilineTextAlignment(.center)
                }

                if viewModel.isLoading {
                    ProgressView().tint(Color(hex: "7C4DFF"))
                } else {
                    VStack(spacing: 10) {
                        Button {
                            viewModel.signIn(email: email, password: password)
                        } label: {
                            Text("Sign In")
                                .font(.headline)
                                .frame(maxWidth: .infinity).frame(height: 52)
                                .background(Color(hex: "7C4DFF"))
                                .foregroundStyle(.white)
                                .clipShape(RoundedRectangle(cornerRadius: 12))
                        }
                        .disabled(email.isEmpty || password.isEmpty)

                        Button {
                            viewModel.createAccount(email: email, password: password)
                        } label: {
                            Text("Create Account")
                                .font(.headline)
                                .frame(maxWidth: .infinity).frame(height: 52)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 12)
                                        .strokeBorder(Color(hex: "7C4DFF"), lineWidth: 1.5)
                                )
                                .foregroundStyle(Color(hex: "7C4DFF"))
                        }
                        .disabled(email.isEmpty || password.count < 6)
                    }
                }
            }
            .padding(.horizontal, 28)
        }
        .navigationTitle("")
        .navigationBarTitleDisplayMode(.inline)
        .onChange(of: viewModel.isSignedIn) { _, signedIn in
            if signedIn { router.path.append(.lobby) }
        }
    }
}
