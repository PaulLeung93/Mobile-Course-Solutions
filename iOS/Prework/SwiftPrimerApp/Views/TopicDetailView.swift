import SwiftUI

struct TopicDetailView: View {
    @EnvironmentObject var viewModel: PrimerViewModel
    @Binding var isPresented: Bool
    
    var body: some View {
        let topic = viewModel.currentTopic
        
        VStack(spacing: 0) {
            // Custom Header
            HStack {
                Button(action: {
                    isPresented = false
                }) {
                    Image(systemName: "xmark")
                        .font(.system(size: 20, weight: .bold))
                        .foregroundColor(Theme.Colors.textSecondary)
                }
                
                Spacer()
                
                // Progress Indicator
                Text("\\(viewModel.currentTopicIndex + 1) of \\(viewModel.topics.count)")
                    .font(.subheadline)
                    .fontWeight(.semibold)
                    .foregroundColor(Theme.Colors.textSecondary)
                
                Spacer()
                
                // Invisible placeholder to balance the X button
                Image(systemName: "xmark")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(.clear)
            }
            .padding()
            .background(Theme.Colors.surface)
            
            // Progress Bar
            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    Rectangle()
                        .fill(Theme.Colors.surfaceHighlight)
                        .frame(height: 4)
                    
                    Rectangle()
                        .fill(Theme.Colors.primary)
                        .frame(width: geometry.size.width * CGFloat(viewModel.progressPercentage), height: 4)
                        .animation(.spring(), value: viewModel.progressPercentage)
                }
            }
            .frame(height: 4)
            
            ScrollView {
                VStack(alignment: .leading, spacing: Theme.Spacing.large) {
                    
                    // Topic Header
                    VStack(alignment: .leading, spacing: Theme.Spacing.small) {
                        HStack {
                            Image(systemName: topic.icon)
                                .foregroundColor(Theme.Colors.primary)
                                .font(.title2)
                            
                            Text(topic.title)
                                .font(.title)
                                .fontWeight(.bold)
                                .foregroundColor(Theme.Colors.textPrimary)
                        }
                        
                        Text(topic.subtitle)
                            .font(.title3)
                            .foregroundColor(Theme.Colors.textSecondary)
                    }
                    
                    // Explanation
                    Text(topic.lesson.explanation)
                        .font(.body)
                        .foregroundColor(Theme.Colors.textPrimary)
                        .lineSpacing(4)
                    
                    // Code Examples
                    ForEach(topic.lesson.codeExamples, id: \\.title) { example in
                        VStack(alignment: .leading, spacing: Theme.Spacing.small) {
                            Text(example.title)
                                .font(.headline)
                                .foregroundColor(Theme.Colors.textPrimary)
                            
                            // Code Box
                            VStack(alignment: .leading) {
                                Text(example.code)
                                    .font(.system(.body, design: .monospaced))
                                    .foregroundColor(.white)
                                    .padding()
                            }
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .background(Color.black.opacity(0.8))
                            .cornerRadius(Theme.Radius.small)
                            
                            // Code Explanation
                            Text(example.explanation)
                                .font(.subheadline)
                                .foregroundColor(Theme.Colors.textSecondary)
                                .padding(.top, 4)
                        }
                    }
                    
                    // Key Takeaway
                    HStack(alignment: .top) {
                        Image(systemName: "lightbulb.fill")
                            .foregroundColor(.yellow)
                            .font(.title3)
                            .padding(.top, 2)
                        
                        VStack(alignment: .leading, spacing: 4) {
                            Text("Key Takeaway")
                                .font(.headline)
                                .foregroundColor(Theme.Colors.textPrimary)
                            
                            Text(topic.lesson.keyTakeaway)
                                .font(.body)
                                .foregroundColor(Theme.Colors.textSecondary)
                        }
                    }
                    .padding()
                    .background(Theme.Colors.surfaceHighlight)
                    .cornerRadius(Theme.Radius.small)
                    .overlay(
                        RoundedRectangle(cornerRadius: Theme.Radius.small)
                            .stroke(Theme.Colors.primary.opacity(0.3), lineWidth: 1)
                    )
                    
                    Divider()
                        .background(Theme.Colors.surfaceHighlight)
                        .padding(.vertical, Theme.Spacing.small)
                    
                    // Quiz Section
                    QuizView()
                        .environmentObject(viewModel)
                    
                }
                .padding(Theme.Spacing.medium)
            }
        }
        .background(Theme.Colors.background.edgesIgnoringSafeArea(.bottom))
    }
}
