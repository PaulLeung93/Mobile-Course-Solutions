import SwiftUI

struct ResultsView: View {
    @EnvironmentObject var viewModel: PrimerViewModel
    @Binding var isPresented: Bool
    
    var body: some View {
        let isPerfect = viewModel.score == viewModel.topics.count
        
        VStack(spacing: Theme.Spacing.large) {
            Spacer()
            
            // Icon
            ZStack {
                Circle()
                    .fill(isPerfect ? Color.green.opacity(0.2) : Theme.Colors.primary.opacity(0.2))
                    .frame(width: 120, height: 120)
                
                Image(systemName: isPerfect ? "trophy.fill" : "star.fill")
                    .font(.system(size: 60))
                    .foregroundColor(isPerfect ? .green : Theme.Colors.primary)
            }
            
            // Title
            Text(isPerfect ? "Passed!" : "Great Job!")
                .font(.largeTitle)
                .fontWeight(.bold)
                .foregroundColor(Theme.Colors.textPrimary)
            
            // Score
            Text("You scored \\(viewModel.score) out of \\(viewModel.topics.count)")
                .font(.title2)
                .foregroundColor(Theme.Colors.textSecondary)
            
            // Missed Topics
            if !viewModel.missedTopics.isEmpty {
                VStack(alignment: .leading, spacing: Theme.Spacing.small) {
                    Text("Topics to review:")
                        .font(.headline)
                        .foregroundColor(Theme.Colors.textPrimary)
                    
                    ForEach(viewModel.missedTopics, id: \\.self) { topic in
                        HStack {
                            Image(systemName: "arrow.right.circle.fill")
                                .foregroundColor(Theme.Colors.primary)
                            Text(topic)
                                .font(.body)
                                .foregroundColor(Theme.Colors.textSecondary)
                        }
                    }
                }
                .padding()
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Theme.Colors.surface)
                .cornerRadius(Theme.Radius.medium)
                .padding(.horizontal, Theme.Spacing.medium)
            }
            
            Spacer()
            
            // Done Button
            Button(action: {
                isPresented = false
            }) {
                Text("Return to Home")
                    .font(.headline)
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Theme.Colors.primary)
                    .cornerRadius(Theme.Radius.medium)
                    .shadow(color: Theme.Colors.primary.opacity(0.3), radius: 5, x: 0, y: 3)
            }
            .padding(Theme.Spacing.medium)
        }
        .background(Theme.Colors.background.edgesIgnoringSafeArea(.all))
    }
}
