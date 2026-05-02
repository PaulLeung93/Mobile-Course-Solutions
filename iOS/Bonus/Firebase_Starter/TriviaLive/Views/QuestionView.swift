import SwiftUI

private let optionLabels  = ["A", "B", "C", "D"]
private let optionColors  = [Color(hex: "7C4DFF"), Color(hex: "00C8D7"),
                              Color(hex: "FF6B6B"), Color(hex: "FFD93D")]

struct QuestionView: View {
    @ObservedObject var viewModel: GameViewModel
    @EnvironmentObject private var router: NavigationRouter

    var body: some View {
        ZStack {
            Color(hex: "0F0E1A").ignoresSafeArea()

            let questions = viewModel.questions
            let idx       = viewModel.currentQuestion

            if idx < questions.count {
                let currentQ = questions[idx]

                VStack(spacing: 0) {
                    // ── Progress header ──────────────────────────────────────
                    HStack {
                        Text("Q \(idx + 1) / \(questions.count)")
                            .font(.caption.weight(.semibold))
                            .foregroundStyle(.white.opacity(0.6))
                        Spacer()
                        if viewModel.isHost {
                            Text("\(viewModel.players.count) players")
                                .font(.caption.weight(.semibold))
                                .foregroundStyle(.white.opacity(0.6))
                        }
                    }
                    .padding(.bottom, 6)

                    ProgressView(value: Double(idx + 1), total: Double(questions.count))
                        .tint(Color(hex: "7C4DFF"))
                        .background(Color(hex: "1C1A2E"))
                        .clipShape(Capsule())

                    Spacer().frame(height: 20)

                    // ── Question card ────────────────────────────────────────
                    Text(currentQ.text)
                        .font(.title3.weight(.semibold))
                        .foregroundStyle(.white)
                        .multilineTextAlignment(.center)
                        .frame(maxWidth: .infinity)
                        .padding(24)
                        .background(Color(hex: "1C1A2E"))
                        .clipShape(RoundedRectangle(cornerRadius: 16))

                    Spacer().frame(height: 20)

                    // ── Host or Player controls ──────────────────────────────
                    if viewModel.isHost {
                        HostControls(viewModel: viewModel)
                    } else {
                        PlayerAnswers(question: currentQ, viewModel: viewModel)
                    }
                }
                .padding(.horizontal, 20)
                .padding(.vertical, 24)
            }
        }
        .navigationTitle("Question \(viewModel.currentQuestion + 1)")
        .navigationBarTitleDisplayMode(.inline)
        .onChange(of: viewModel.gameStatus) { _, status in
            if status == "finished" {
                viewModel.loadLeaderboard()
                router.path.append(.leaderboard)
            }
        }
    }
}

// MARK: - Player answer buttons

private struct PlayerAnswers: View {
    let question: Question
    @ObservedObject var viewModel: GameViewModel

    var body: some View {
        VStack(spacing: 10) {
            ForEach(Array(question.options.enumerated()), id: \.offset) { index, option in
                let bgColor: Color = {
                    guard viewModel.hasAnswered else { return optionColors[index] }
                    return index == question.correctIndex
                        ? Color(hex: "4CAF50")
                        : Color(hex: "E53935").opacity(0.5)
                }()

                Button {
                    viewModel.submitAnswer(index: index)
                } label: {
                    HStack(spacing: 12) {
                        Text(optionLabels[index])
                            .font(.headline.weight(.black))
                            .frame(width: 28)
                        Text(option)
                            .font(.headline)
                            .frame(maxWidth: .infinity, alignment: .leading)
                    }
                    .foregroundStyle(.white)
                    .padding(.horizontal, 16)
                    .frame(maxWidth: .infinity)
                    .frame(height: 58)
                    .background(bgColor)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .disabled(viewModel.hasAnswered)
            }

            if viewModel.hasAnswered {
                Spacer().frame(height: 4)
                let correct = viewModel.lastAnswerCorrect == true
                Text(correct ? "✅  Correct! +100 points" : "❌  Wrong answer")
                    .font(.headline)
                    .foregroundStyle(correct ? Color(hex: "4CAF50") : Color(hex: "E53935"))
                    .frame(maxWidth: .infinity, alignment: .center)
                Text("Waiting for the host to continue...")
                    .font(.subheadline)
                    .foregroundStyle(.white.opacity(0.5))
                    .frame(maxWidth: .infinity, alignment: .center)
            }
        }
    }
}

// MARK: - Host scoreboard + next button

private struct HostControls: View {
    @ObservedObject var viewModel: GameViewModel

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("Scoreboard")
                .font(.headline)
                .foregroundStyle(.white)

            if viewModel.players.isEmpty {
                Text("No players yet")
                    .font(.subheadline)
                    .foregroundStyle(.white.opacity(0.5))
            } else {
                let sorted = viewModel.players.sorted { $0.score > $1.score }
                ScrollView {
                    VStack(spacing: 6) {
                        ForEach(sorted) { player in
                            HStack {
                                Text(player.name)
                                    .font(.headline)
                                    .foregroundStyle(.white)
                                Spacer()
                                Text("\(player.score) pts")
                                    .font(.headline)
                                    .foregroundStyle(Color(hex: "7C4DFF"))
                            }
                            .padding(.horizontal, 16)
                            .padding(.vertical, 10)
                            .background(Color(hex: "1C1A2E"))
                            .clipShape(RoundedRectangle(cornerRadius: 10))
                        }
                    }
                }
            }

            Spacer().frame(height: 6)

            Button {
                viewModel.advanceQuestion()
            } label: {
                Text("Next Question  →")
                    .font(.headline)
                    .frame(maxWidth: .infinity).frame(height: 56)
                    .background(Color(hex: "7C4DFF"))
                    .foregroundStyle(.white)
                    .clipShape(RoundedRectangle(cornerRadius: 14))
            }
        }
    }
}
