import SwiftUI

struct HomeView: View {
    @EnvironmentObject var viewModel: PrimerViewModel
    
    var body: some View {
        ScrollView {
            VStack(spacing: Theme.Spacing.large) {
                // Header
                VStack(spacing: Theme.Spacing.small) {
                    Image(systemName: "swift")
                        .font(.system(size: 60))
                        .foregroundColor(Theme.Colors.primary)
                        .padding(.bottom, Theme.Spacing.small)
                    
                    Text("Swift Primer")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .foregroundColor(Theme.Colors.textPrimary)
                    
                    Text("Welcome to iOS Development")
                        .font(.title3)
                        .foregroundColor(Theme.Colors.textSecondary)
                }
                .padding(.top, Theme.Spacing.xlarge)
                
                // Welcome Card
                VStack(alignment: .leading, spacing: Theme.Spacing.medium) {
                    Text("Your Journey Begins Here")
                        .font(.headline)
                        .foregroundColor(Theme.Colors.textPrimary)
                    
                    Text("Before diving into building iOS apps, you need to understand the basics of Swift. This app contains 12 bite-sized lessons to get you up to speed.")
                        .font(.body)
                        .foregroundColor(Theme.Colors.textSecondary)
                        .fixedSize(horizontal: false, vertical: true)
                }
                .padding()
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Theme.Colors.surface)
                .cornerRadius(Theme.Radius.medium)
                .shadow(color: Color.black.opacity(0.1), radius: 5, x: 0, y: 2)
                
                // Stats Card
                VStack(alignment: .leading, spacing: Theme.Spacing.medium) {
                    Text("Your Progress")
                        .font(.headline)
                        .foregroundColor(Theme.Colors.textPrimary)
                    
                    HStack {
                        StatBox(
                            title: "Lessons",
                            value: "\\(viewModel.topics.count)",
                            icon: "book.fill",
                            color: .blue
                        )
                        
                        StatBox(
                            title: "Score",
                            value: "\\(viewModel.score)/\\(viewModel.topics.count)",
                            icon: "star.fill",
                            color: .orange
                        )
                    }
                }
                .padding()
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Theme.Colors.surface)
                .cornerRadius(Theme.Radius.medium)
                .shadow(color: Color.black.opacity(0.1), radius: 5, x: 0, y: 2)
                
                Spacer()
            }
            .padding(Theme.Spacing.medium)
        }
        .background(Theme.Colors.background.edgesIgnoringSafeArea(.all))
    }
}

struct StatBox: View {
    let title: String
    let value: String
    let icon: String
    let color: Color
    
    var body: some View {
        VStack(spacing: Theme.Spacing.small) {
            HStack {
                Image(systemName: icon)
                    .foregroundColor(color)
                Text(title)
                    .font(.subheadline)
                    .foregroundColor(Theme.Colors.textSecondary)
            }
            
            Text(value)
                .font(.title2)
                .fontWeight(.bold)
                .foregroundColor(Theme.Colors.textPrimary)
        }
        .frame(maxWidth: .infinity)
        .padding()
        .background(Theme.Colors.background)
        .cornerRadius(Theme.Radius.small)
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
            .environmentObject(PrimerViewModel())
            .preferredColorScheme(.dark)
    }
}
