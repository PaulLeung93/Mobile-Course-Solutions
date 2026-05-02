package com.codepath.trivialive.model

data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)

val triviaQuestions = listOf(
    Question(
        text = "What is the capital of Australia?",
        options = listOf("Sydney", "Melbourne", "Canberra", "Perth"),
        correctIndex = 2
    ),
    Question(
        text = "Which planet is closest to the Sun?",
        options = listOf("Venus", "Mercury", "Mars", "Earth"),
        correctIndex = 1
    ),
    Question(
        text = "How many sides does a heptagon have?",
        options = listOf("5", "6", "7", "8"),
        correctIndex = 2
    ),
    Question(
        text = "Who painted the Mona Lisa?",
        options = listOf("Van Gogh", "Michelangelo", "Leonardo da Vinci", "Raphael"),
        correctIndex = 2
    ),
    Question(
        text = "What is the chemical symbol for gold?",
        options = listOf("Au", "Ag", "Fe", "Gd"),
        correctIndex = 0
    ),
    Question(
        text = "In what year did the first iPhone launch?",
        options = listOf("2005", "2006", "2007", "2008"),
        correctIndex = 2
    ),
    Question(
        text = "How many bones are in the adult human body?",
        options = listOf("186", "206", "226", "246"),
        correctIndex = 1
    ),
    Question(
        text = "What is the largest ocean on Earth?",
        options = listOf("Atlantic", "Indian", "Arctic", "Pacific"),
        correctIndex = 3
    ),
    Question(
        text = "Who created the Python programming language?",
        options = listOf("James Gosling", "Guido van Rossum", "Bjarne Stroustrup", "Brendan Eich"),
        correctIndex = 1
    ),
    Question(
        text = "What does CPU stand for?",
        options = listOf("Central Processing Unit", "Computer Power Unit", "Control Processing Unit", "Core Processing Utility"),
        correctIndex = 0
    )
)
