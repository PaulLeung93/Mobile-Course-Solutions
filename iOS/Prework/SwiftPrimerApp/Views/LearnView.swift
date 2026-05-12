import SwiftUI

struct LearnView: View {
    @EnvironmentObject var viewModel: PrimerViewModel
    @State private var showingTopicDetail = false
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: Theme.Spacing.medium) {
                    
                    // Start/Resume Button
                    Button(action: {
                        viewModel.startQuiz()
                        showingTopicDetail = true
                    }) {
                        Text(viewModel.score > 0 || viewModel.showResults ? "Restart Primer" : "Start Primer")
                            .font(.headline)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Theme.Colors.primary)
                            .cornerRadius(Theme.Radius.medium)
                            .shadow(color: Theme.Colors.primary.opacity(0.3), radius: 5, x: 0, y: 3)
                    }
                    .padding(.horizontal, Theme.Spacing.medium)
                    .padding(.top, Theme.Spacing.medium)
                    
                    // Topic List
                    LazyVStack(spacing: Theme.Spacing.medium) {
                        ForEach(viewModel.topics) { topic in
                            TopicCard(topic: topic)
                        }
                    }
                    .padding(Theme.Spacing.medium)
                }
            }
            .background(Theme.Colors.background.edgesIgnoringSafeArea(.all))
            .navigationTitle("Learn Swift")
            .fullScreenCover(isPresented: $showingTopicDetail) {
                if viewModel.showResults {
                    ResultsView(isPresented: $showingTopicDetail)
                        .environmentObject(viewModel)
                } else {
                    TopicDetailView(isPresented: $showingTopicDetail)
                        .environmentObject(viewModel)
                }
            }
        }
        // Force stack navigation view style for iPad compatibility
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

struct TopicCard: View {
    let topic: Topic
    
    var body: some View {
        HStack(spacing: Theme.Spacing.medium) {
            // Icon
            ZStack {
                Circle()
                    .fill(Theme.Colors.primary.opacity(0.1))
                    .frame(width: 50, height: 50)
                
                Image(systemName: topic.icon)
                    .font(.system(size: 24))
                    .foregroundColor(Theme.Colors.primary)
            }
            
            // Text
            VStack(alignment: .leading, spacing: 4) {
                Text("\(topic.id). \(topic.title)")
                    .font(.headline)
                    .foregroundColor(Theme.Colors.textPrimary)
                
                Text(topic.subtitle)
                    .font(.subheadline)
                    .foregroundColor(Theme.Colors.textSecondary)
            }
            
            Spacer()
        }
        .padding()
        .background(Theme.Colors.surface)
        .cornerRadius(Theme.Radius.medium)
        .shadow(color: Color.black.opacity(0.1), radius: 5, x: 0, y: 2)
    }
}

struct LearnView_Previews: PreviewProvider {
    static var previews: some View {
        LearnView()
            .environmentObject(PrimerViewModel())
            .preferredColorScheme(.dark)
    }
}
