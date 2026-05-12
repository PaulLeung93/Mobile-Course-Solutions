import SwiftUI

struct ReferenceView: View {
    @EnvironmentObject var viewModel: PrimerViewModel
    
    var body: some View {
        NavigationView {
            ScrollView {
                LazyVStack(spacing: Theme.Spacing.medium) {
                    ForEach(viewModel.topics) { topic in
                        VStack(alignment: .leading, spacing: Theme.Spacing.small) {
                            HStack {
                                Image(systemName: topic.icon)
                                    .foregroundColor(Theme.Colors.primary)
                                
                                Text(topic.title)
                                    .font(.headline)
                                    .foregroundColor(Theme.Colors.textPrimary)
                            }
                            
                            Text(topic.lesson.keyTakeaway)
                                .font(.subheadline)
                                .foregroundColor(Theme.Colors.textSecondary)
                                .fixedSize(horizontal: false, vertical: true)
                        }
                        .padding()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(Theme.Colors.surface)
                        .cornerRadius(Theme.Radius.medium)
                        .shadow(color: Color.black.opacity(0.1), radius: 5, x: 0, y: 2)
                    }
                }
                .padding(Theme.Spacing.medium)
            }
            .background(Theme.Colors.background.edgesIgnoringSafeArea(.all))
            .navigationTitle("Cheat Sheet")
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

struct ReferenceView_Previews: PreviewProvider {
    static var previews: some View {
        ReferenceView()
            .environmentObject(PrimerViewModel())
            .preferredColorScheme(.dark)
    }
}
