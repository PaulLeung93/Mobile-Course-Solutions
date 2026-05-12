import SwiftUI

struct ContentView: View {
    @StateObject private var viewModel = PrimerViewModel()
    
    // Tab selection state
    @State private var selectedTab = 0
    
    // Apply custom appearance to the tab bar
    init() {
        let appearance = UITabBarAppearance()
        appearance.configureWithOpaqueBackground()
        appearance.backgroundColor = UIColor(Theme.Colors.surface)
        
        // Active item
        appearance.stackedLayoutAppearance.selected.iconColor = UIColor(Theme.Colors.primary)
        appearance.stackedLayoutAppearance.selected.titleTextAttributes = [.foregroundColor: UIColor(Theme.Colors.primary)]
        
        // Inactive item
        appearance.stackedLayoutAppearance.normal.iconColor = UIColor.systemGray
        appearance.stackedLayoutAppearance.normal.titleTextAttributes = [.foregroundColor: UIColor.systemGray]
        
        UITabBar.appearance().standardAppearance = appearance
        if #available(iOS 15.0, *) {
            UITabBar.appearance().scrollEdgeAppearance = appearance
        }
        
        // Navigation Bar appearance
        let navAppearance = UINavigationBarAppearance()
        navAppearance.configureWithOpaqueBackground()
        navAppearance.backgroundColor = UIColor(Theme.Colors.background)
        navAppearance.titleTextAttributes = [.foregroundColor: UIColor(Theme.Colors.textPrimary)]
        navAppearance.largeTitleTextAttributes = [.foregroundColor: UIColor(Theme.Colors.textPrimary)]
        
        UINavigationBar.appearance().standardAppearance = navAppearance
        UINavigationBar.appearance().compactAppearance = navAppearance
        UINavigationBar.appearance().scrollEdgeAppearance = navAppearance
    }
    
    var body: some View {
        TabView(selection: $selectedTab) {
            
            HomeView()
                .environmentObject(viewModel)
                .tabItem {
                    Label("Home", systemImage: "house.fill")
                }
                .tag(0)
            
            LearnView()
                .environmentObject(viewModel)
                .tabItem {
                    Label("Learn", systemImage: "book.fill")
                }
                .tag(1)
            
            ReferenceView()
                .environmentObject(viewModel)
                .tabItem {
                    Label("Reference", systemImage: "doc.text.magnifyingglass")
                }
                .tag(2)
            
            CodeView()
                .tabItem {
                    Label("Code", systemImage: "chevron.left.slash.chevron.right")
                }
                .tag(3)
        }
        .accentColor(Theme.Colors.primary)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .preferredColorScheme(.dark)
    }
}
