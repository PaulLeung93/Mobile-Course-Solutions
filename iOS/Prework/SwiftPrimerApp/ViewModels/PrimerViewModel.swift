import Foundation

// MARK: - View Model for the Primer

class PrimerViewModel: ObservableObject {
    @Published var topics: [Topic] = TopicData.allTopics
    
    // Quiz state
    @Published var currentTopicIndex: Int = 0
    @Published var score: Int = 0
    @Published var missedTopics: [String] = []
    
    // UI state
    @Published var isQuizActive: Bool = false
    @Published var showResults: Bool = false
    
    // Selected option in the current quiz
    @Published var selectedOptionIndex: Int? = nil
    @Published var hasSubmittedCurrentQuestion: Bool = false
    
    var currentTopic: Topic {
        topics[currentTopicIndex]
    }
    
    var progressPercentage: Double {
        Double(currentTopicIndex) / Double(topics.count)
    }
    
    func startQuiz() {
        currentTopicIndex = 0
        score = 0
        missedTopics = []
        isQuizActive = true
        showResults = false
        selectedOptionIndex = nil
        hasSubmittedCurrentQuestion = false
    }
    
    func submitAnswer() {
        guard let selected = selectedOptionIndex, !hasSubmittedCurrentQuestion else { return }
        
        hasSubmittedCurrentQuestion = true
        
        if selected == currentTopic.quiz.correctIndex {
            score += 1
        } else {
            missedTopics.append("\(currentTopicIndex + 1). \(currentTopic.title)")
        }
    }
    
    func nextTopic() {
        if currentTopicIndex < topics.count - 1 {
            currentTopicIndex += 1
            selectedOptionIndex = nil
            hasSubmittedCurrentQuestion = false
        } else {
            isQuizActive = false
            showResults = true
        }
    }
    
    func reset() {
        isQuizActive = false
        showResults = false
        currentTopicIndex = 0
        score = 0
        missedTopics = []
        selectedOptionIndex = nil
        hasSubmittedCurrentQuestion = false
    }
}
