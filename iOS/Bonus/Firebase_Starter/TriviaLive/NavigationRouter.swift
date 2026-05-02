import Foundation

class NavigationRouter: ObservableObject {
    @Published var path: [AppRoute] = []
    func popToRoot() { path = [] }
}
