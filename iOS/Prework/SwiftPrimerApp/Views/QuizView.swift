import SwiftUI

struct QuizView: View {
    @EnvironmentObject var viewModel: PrimerViewModel
    
    var body: some View {
        let quiz = viewModel.currentTopic.quiz
        
        VStack(alignment: .leading, spacing: Theme.Spacing.medium) {
            Text("Knowledge Check")
                .font(.title2)
                .fontWeight(.bold)
                .foregroundColor(Theme.Colors.textPrimary)
            
            Text(quiz.scenario)
                .font(.subheadline)
                .foregroundColor(Theme.Colors.textSecondary)
                .italic()
            
            Text(quiz.question)
                .font(.body)
                .foregroundColor(Theme.Colors.textPrimary)
                .padding(.bottom, Theme.Spacing.small)
            
            // Options
            VStack(spacing: Theme.Spacing.small) {
                ForEach(0..<quiz.options.count, id: \\.self) { index in
                    QuizOptionRow(
                        text: quiz.options[index],
                        isSelected: viewModel.selectedOptionIndex == index,
                        isSubmitted: viewModel.hasSubmittedCurrentQuestion,
                        isCorrect: index == quiz.correctIndex,
                        action: {
                            if !viewModel.hasSubmittedCurrentQuestion {
                                viewModel.selectedOptionIndex = index
                            }
                        }
                    )
                }
            }
            
            // Feedback & Action Button
            if viewModel.hasSubmittedCurrentQuestion {
                let isCorrect = viewModel.selectedOptionIndex == quiz.correctIndex
                
                VStack(alignment: .leading, spacing: Theme.Spacing.medium) {
                    HStack {
                        Image(systemName: isCorrect ? "checkmark.circle.fill" : "xmark.circle.fill")
                            .foregroundColor(isCorrect ? .green : .red)
                        Text(isCorrect ? "Correct!" : "Incorrect")
                            .font(.headline)
                            .foregroundColor(isCorrect ? .green : .red)
                    }
                    
                    Text(quiz.explanation)
                        .font(.body)
                        .foregroundColor(Theme.Colors.textSecondary)
                    
                    Button(action: {
                        viewModel.nextTopic()
                    }) {
                        Text(viewModel.currentTopicIndex == viewModel.topics.count - 1 ? "Finish" : "Next Topic")
                            .font(.headline)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Theme.Colors.primary)
                            .cornerRadius(Theme.Radius.medium)
                    }
                    .padding(.top, Theme.Spacing.small)
                }
                .padding()
                .background(isCorrect ? Color.green.opacity(0.1) : Color.red.opacity(0.1))
                .cornerRadius(Theme.Radius.medium)
                .transition(.move(edge: .bottom).combined(with: .opacity))
                
            } else {
                Button(action: {
                    withAnimation {
                        viewModel.submitAnswer()
                    }
                }) {
                    Text("Submit Answer")
                        .font(.headline)
                        .foregroundColor(viewModel.selectedOptionIndex != nil ? .white : Theme.Colors.textSecondary)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(viewModel.selectedOptionIndex != nil ? Theme.Colors.primary : Theme.Colors.surfaceHighlight)
                        .cornerRadius(Theme.Radius.medium)
                }
                .disabled(viewModel.selectedOptionIndex == nil)
                .padding(.top, Theme.Spacing.small)
            }
        }
    }
}

struct QuizOptionRow: View {
    let text: String
    let isSelected: Bool
    let isSubmitted: Bool
    let isCorrect: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack {
                Text(text)
                    .font(.body)
                    .foregroundColor(Theme.Colors.textPrimary)
                    .multilineTextAlignment(.leading)
                
                Spacer()
                
                if isSubmitted {
                    if isCorrect {
                        Image(systemName: "checkmark")
                            .foregroundColor(.green)
                            .font(.system(size: 16, weight: .bold))
                    } else if isSelected {
                        Image(systemName: "xmark")
                            .foregroundColor(.red)
                            .font(.system(size: 16, weight: .bold))
                    }
                } else {
                    Circle()
                        .strokeBorder(isSelected ? Theme.Colors.primary : Theme.Colors.textSecondary, lineWidth: 2)
                        .background(Circle().fill(isSelected ? Theme.Colors.primary : Color.clear))
                        .frame(width: 20, height: 20)
                }
            }
            .padding()
            .background(getBackgroundColor())
            .cornerRadius(Theme.Radius.small)
            .overlay(
                RoundedRectangle(cornerRadius: Theme.Radius.small)
                    .stroke(getBorderColor(), lineWidth: isSelected || (isSubmitted && isCorrect) ? 2 : 1)
            )
        }
        .buttonStyle(PlainButtonStyle())
    }
    
    private func getBackgroundColor() -> Color {
        if isSubmitted {
            if isCorrect {
                return Color.green.opacity(0.1)
            } else if isSelected {
                return Color.red.opacity(0.1)
            }
        } else if isSelected {
            return Theme.Colors.primary.opacity(0.1)
        }
        return Theme.Colors.surface
    }
    
    private func getBorderColor() -> Color {
        if isSubmitted {
            if isCorrect {
                return .green
            } else if isSelected {
                return .red
            }
        } else if isSelected {
            return Theme.Colors.primary
        }
        return Theme.Colors.surfaceHighlight
    }
}
