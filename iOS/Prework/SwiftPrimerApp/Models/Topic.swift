import SwiftUI

// MARK: - Topic Model

/// Represents one of the 12 Swift language topics in the primer.
struct Topic: Identifiable {
    let id: Int
    let title: String
    let subtitle: String
    let icon: String          // SF Symbol name
    let lesson: Lesson
    let quiz: QuizQuestion
}

// MARK: - Lesson Model

/// The educational content for a single topic.
struct Lesson {
    let explanation: String          // Multi-paragraph explanation
    let codeExamples: [CodeExample]  // Annotated code snippets
    let keyTakeaway: String          // One-liner summary
}

// MARK: - Code Example

/// A single code snippet with a title and optional explanation.
struct CodeExample: Identifiable {
    let id = UUID()
    let title: String
    let code: String
    let explanation: String
}

// MARK: - Quiz Question

/// A multiple-choice question for a topic.
struct QuizQuestion {
    let question: String
    let scenario: String          // Contextual setup for the question
    let options: [String]         // 4 options (A–D)
    let correctIndex: Int         // 0-based index of the correct answer
    let explanation: String       // Why the correct answer is correct
}
