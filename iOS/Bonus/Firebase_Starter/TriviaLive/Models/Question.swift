import Foundation

struct Question {
    let text: String
    let options: [String]
    let correctIndex: Int
}

let triviaQuestions: [Question] = [
    Question(
        text: "What is the capital of Australia?",
        options: ["Sydney", "Melbourne", "Canberra", "Perth"],
        correctIndex: 2
    ),
    Question(
        text: "Which planet is closest to the Sun?",
        options: ["Venus", "Mercury", "Mars", "Earth"],
        correctIndex: 1
    ),
    Question(
        text: "How many sides does a heptagon have?",
        options: ["5", "6", "7", "8"],
        correctIndex: 2
    ),
    Question(
        text: "Who painted the Mona Lisa?",
        options: ["Van Gogh", "Michelangelo", "Leonardo da Vinci", "Raphael"],
        correctIndex: 2
    ),
    Question(
        text: "What is the chemical symbol for gold?",
        options: ["Au", "Ag", "Fe", "Gd"],
        correctIndex: 0
    ),
    Question(
        text: "In what year did the first iPhone launch?",
        options: ["2005", "2006", "2007", "2008"],
        correctIndex: 2
    ),
    Question(
        text: "How many bones are in the adult human body?",
        options: ["186", "206", "226", "246"],
        correctIndex: 1
    ),
    Question(
        text: "What is the largest ocean on Earth?",
        options: ["Atlantic", "Indian", "Arctic", "Pacific"],
        correctIndex: 3
    ),
    Question(
        text: "Who created the Python programming language?",
        options: ["James Gosling", "Guido van Rossum", "Bjarne Stroustrup", "Brendan Eich"],
        correctIndex: 1
    ),
    Question(
        text: "What does CPU stand for?",
        options: ["Central Processing Unit", "Computer Power Unit", "Control Processing Unit", "Core Processing Utility"],
        correctIndex: 0
    )
]
