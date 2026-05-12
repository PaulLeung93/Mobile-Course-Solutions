import Foundation

enum TopicData {
    static let allTopics: [Topic] = [
        Topic(
            id: 1,
            title: "let vs var",
            subtitle: "Constants and variables",
            icon: "lock.fill",
            lesson: Lesson(
                explanation: "Swift provides two keywords to declare values: `let` and `var`. Use `let` to declare a constant (a value that never changes) and `var` to declare a variable (a value that can be updated). Swift encourages using `let` by default to make your code safer and easier to understand.",
                codeExamples: [
                    CodeExample(
                        title: "Declaring constants and variables",
                        code: """
let appName = "CodePath"   // fixed — never changes
var lives = 3              // changes as game progresses

lives = 2                  // allowed
appName = "Other"          // ERROR: Cannot assign to value: 'appName' is a 'let' constant
""",
                        explanation: "When you try to reassign a `let` constant, the Swift compiler will throw an error and stop you from making a mistake."
                    )
                ],
                keyTakeaway: "Use let for constants and var for variables. Default to let."
            ),
            quiz: QuizQuestion(
                question: "Which keyword should you use to declare a player's score that increases each correct answer?",
                scenario: "You're building a quiz app.",
                options: [
                    "let",
                    "val",
                    "var",
                    "const"
                ],
                correctIndex: 2,
                explanation: "Because the score changes, you must use `var`. `let` is for constants."
            )
        ),
        
        Topic(
            id: 2,
            title: "Type inference",
            subtitle: "Letting Swift figure out types",
            icon: "magnifyingglass",
            lesson: Lesson(
                explanation: "Swift is strongly typed, but it's smart enough to figure out what type of data you're storing without you explicitly writing it. This is called Type Inference.",
                codeExamples: [
                    CodeExample(
                        title: "Implicit vs Explicit Types",
                        code: """
// Swift infers this is a String
let username = "jappleseed"

// Swift infers this is an Int
var score = 100

// You can explicitly state the type using a colon
let email: String = "john@apple.com"
var balance: Double = 0.00
""",
                        explanation: "Usually, you don't need to write the type (`: String`). Let Swift infer it from the value on the right."
                    )
                ],
                keyTakeaway: "Swift automatically figures out the type of your variables based on their initial value."
            ),
            quiz: QuizQuestion(
                question: "What is the inferred type of the following variable: `var price = 19.99`?",
                scenario: "You are setting the price of an item in a store app.",
                options: [
                    "Int",
                    "String",
                    "Double",
                    "Float"
                ],
                correctIndex: 2,
                explanation: "Swift infers decimal numbers as `Double` by default. If you specifically need a `Float`, you must explicitly annotate it: `var price: Float = 19.99`."
            )
        ),
        
        Topic(
            id: 3,
            title: "String interpolation",
            subtitle: "Injecting variables into strings",
            icon: "text.quote",
            lesson: Lesson(
                explanation: "In Swift, you can easily insert variables or expressions directly into a String using string interpolation. Just wrap the variable inside `\\()`.",
                codeExamples: [
                    CodeExample(
                        title: "Using string interpolation",
                        code: """
let name = "Alice"
let age = 25

// Inject variables directly into the string using \\()
let message = "Hello, my name is \\(name) and I am \\(age) years old."

// You can also perform math inside the parentheses
let nextYear = "Next year I will be \\(age + 1)."
""",
                        explanation: "This is much cleaner than using the `+` operator to concatenate strings and numbers together."
                    )
                ],
                keyTakeaway: "Use \\(variableName) to easily embed values inside string literals."
            ),
            quiz: QuizQuestion(
                question: "How do you correctly inject the variable `score` into this string?",
                scenario: "var score = 10",
                options: [
                    "\"Your score is ${score}\"",
                    "\"Your score is \\(score)\"",
                    "\"Your score is \" + score",
                    "\"Your score is {score}\""
                ],
                correctIndex: 1,
                explanation: "Swift uses `\\()` for string interpolation. `${}` is used in Kotlin, and adding a number to a string with `+` requires explicit casting in Swift."
            )
        ),
        
        Topic(
            id: 4,
            title: "Functions",
            subtitle: "Reusable blocks of code",
            icon: "f.cursive.circle.fill",
            lesson: Lesson(
                explanation: "Functions in Swift use the `func` keyword. They take parameters and optionally return a value using the `->` syntax. By default, function arguments require labels when calling the function.",
                codeExamples: [
                    CodeExample(
                        title: "A basic Swift function",
                        code: """
// func [name]([param]: [Type]) -> [ReturnType]
func greet(name: String) -> String {
    return "Hello, \\(name)!"
}

// Calling the function (note the argument label)
let greeting = greet(name: "Taylor")
""",
                        explanation: "Notice how `name: \"Taylor\"` includes the parameter name. This makes Swift code highly readable at the call site."
                    ),
                    CodeExample(
                        title: "Omitting argument labels",
                        code: """
// Use '_' to omit the argument label
func add(_ a: Int, _ b: Int) -> Int {
    return a + b
}

let sum = add(5, 3) // No labels needed
""",
                        explanation: "You can use an underscore `_` if you want the function call to look like a standard math function or C-style function."
                    )
                ],
                keyTakeaway: "Use `func` to define functions, `->` for the return type, and remember to use argument labels when calling them."
            ),
            quiz: QuizQuestion(
                question: "Which of the following is the correct syntax for a function that takes an integer and returns a string?",
                scenario: "You are writing a function to format a price.",
                options: [
                    "func formatPrice(price: Int) : String { ... }",
                    "def formatPrice(price: Int) -> String { ... }",
                    "func formatPrice(price: Int) -> String { ... }",
                    "function formatPrice(price: Int) returns String { ... }"
                ],
                correctIndex: 2,
                explanation: "Swift uses the `func` keyword, followed by parameters in parentheses, and an arrow `->` for the return type."
            )
        ),
        
        Topic(
            id: 5,
            title: "if / else",
            subtitle: "Making decisions",
            icon: "arrow.triangle.branch",
            lesson: Lesson(
                explanation: "Conditional statements in Swift look similar to other languages, but you don't need parentheses around the condition. However, the curly braces `{}` are required, even for single-line statements.",
                codeExamples: [
                    CodeExample(
                        title: "Basic if/else",
                        code: """
let temp = 75

if temp > 80 {
    print("It's hot outside.")
} else if temp < 50 {
    print("It's cold outside.")
} else {
    print("It's nice outside.")
}
""",
                        explanation: "No parentheses around `temp > 80`, but the curly braces are strictly required."
                    )
                ],
                keyTakeaway: "Parentheses around conditions are optional, but curly braces are mandatory."
            ),
            quiz: QuizQuestion(
                question: "Which of the following is valid Swift code?",
                scenario: "Checking if a user is logged in.",
                options: [
                    "if isLoggedIn print(\"Welcome\")",
                    "if (isLoggedIn) print(\"Welcome\")",
                    "if isLoggedIn { print(\"Welcome\") }",
                    "if isLoggedIn then print(\"Welcome\")"
                ],
                correctIndex: 2,
                explanation: "Swift requires curly braces `{}` around the body of an `if` statement, even if it's only one line. Parentheses around the condition are optional."
            )
        ),
        
        Topic(
            id: 6,
            title: "switch",
            subtitle: "Pattern matching",
            icon: "switch.2",
            lesson: Lesson(
                explanation: "Swift's `switch` statement is incredibly powerful. Unlike other languages, it doesn't fall through to the next case automatically (no `break` needed), and it must be exhaustive (handle every possible value).",
                codeExamples: [
                    CodeExample(
                        title: "Switching on strings and ranges",
                        code: """
let status = "success"

switch status {
case "success":
    print("It worked!")
case "error":
    print("Something went wrong.")
default:
    print("Unknown status.")
}

let score = 85
switch score {
case 90...100: print("A")
case 80..<90:  print("B")
case 70..<80:  print("C")
default:       print("F")
}
""",
                        explanation: "Notice the `...` (closed range) and `..<` (half-open range) operators, and the mandatory `default` case."
                    )
                ],
                keyTakeaway: "Switch statements must be exhaustive and don't require `break` statements."
            ),
            quiz: QuizQuestion(
                question: "What happens if you omit the `default` case in a Swift `switch` statement when switching on an integer?",
                scenario: "var rating = 5; switch rating { case 1: ... case 5: ... }",
                options: [
                    "It defaults to doing nothing.",
                    "The compiler throws an error because the switch is not exhaustive.",
                    "It crashes at runtime if the value isn't 1 or 5.",
                    "It automatically returns nil."
                ],
                correctIndex: 1,
                explanation: "Swift requires `switch` statements to be exhaustive. Since an `Int` can have millions of values, you must provide a `default` case if you don't explicitly list every possible integer."
            )
        ),
        
        Topic(
            id: 7,
            title: "Optionals",
            subtitle: "Handling missing values",
            icon: "questionmark.circle.fill",
            lesson: Lesson(
                explanation: "In Swift, regular variables cannot be `nil` (null). If a variable might be missing a value, you must declare it as an Optional by adding a `?` to its type. To safely use an Optional, you must \"unwrap\" it.",
                codeExamples: [
                    CodeExample(
                        title: "Declaring and using Optionals",
                        code: """
// This might contain a String, or it might be nil
var nickname: String? = nil

// 1. Nil-coalescing operator (??) - provide a default
let display = nickname ?? "Guest"

// 2. if let (Optional Binding) - do something if it exists
if let name = nickname {
    print("Hello, \\(name)!")
} else {
    print("Hello, Stranger!")
}
""",
                        explanation: "Optionals force you to handle `nil` cases explicitly, completely eliminating entire classes of runtime crashes."
                    )
                ],
                keyTakeaway: "Add `?` to a type to make it Optional. Use `??` to provide a default, or `if let` to unwrap safely."
            ),
            quiz: QuizQuestion(
                question: "What does the `??` (nil-coalescing) operator do?",
                scenario: "let name = currentUser?.name ?? \"Anonymous\"",
                options: [
                    "It checks if two variables are both nil.",
                    "It forcibly unwraps the optional, crashing if it's nil.",
                    "It provides a default value to use if the optional is nil.",
                    "It safely unwraps the optional and binds it to a new variable."
                ],
                correctIndex: 2,
                explanation: "The `??` operator unwraps the optional if it contains a value, or falls back to the default value on the right if it is `nil`."
            )
        ),
        
        Topic(
            id: 8,
            title: "Arrays",
            subtitle: "Ordered collections",
            icon: "list.bullet.rectangle.fill",
            lesson: Lesson(
                explanation: "Arrays in Swift are created using square brackets `[]`. Whether an array can be modified depends entirely on whether you declared it with `let` (immutable) or `var` (mutable).",
                codeExamples: [
                    CodeExample(
                        title: "Creating and modifying arrays",
                        code: """
// Immutable array (cannot be changed)
let vowels = ["A", "E", "I", "O", "U"]

// Mutable array (can add/remove items)
var scores = [10, 20, 30]

// Adding an item
scores.append(40)

// Accessing an item (0-indexed)
let firstScore = scores[0]

// Empty array
var emptyArr: [String] = []
""",
                        explanation: "You don't need a special `MutableList` type like in Kotlin; you just use `var` instead of `let`."
                    )
                ],
                keyTakeaway: "Use square brackets `[]` for arrays. Use `var` to make them mutable."
            ),
            quiz: QuizQuestion(
                question: "How do you add \"Apple\" to this array: `var fruits = [\"Banana\", \"Orange\"]`?",
                scenario: "You are building a shopping list app.",
                options: [
                    "fruits.add(\"Apple\")",
                    "fruits.push(\"Apple\")",
                    "fruits.insert(\"Apple\")",
                    "fruits.append(\"Apple\")"
                ],
                correctIndex: 3,
                explanation: "In Swift, the method to add an element to the end of an array is `append()`."
            )
        ),
        
        Topic(
            id: 9,
            title: "for loops & ranges",
            subtitle: "Iterating over collections",
            icon: "repeat",
            lesson: Lesson(
                explanation: "Swift uses the `for-in` loop to iterate over arrays, dictionaries, or ranges of numbers. Ranges are created using `...` (inclusive) or `..<` (exclusive).",
                codeExamples: [
                    CodeExample(
                        title: "Looping over arrays and ranges",
                        code: """
let names = ["Anna", "Brian", "Craig"]

// Loop over an array
for name in names {
    print("Hello, \\(name)!")
}

// Loop over a closed range (1, 2, 3, 4, 5)
for i in 1...5 {
    print(i)
}

// Loop over a half-open range (0, 1, 2)
for i in 0..<3 {
    print(i)
}
""",
                        explanation: "If you don't need the loop variable `i`, you can replace it with an underscore `_`."
                    )
                ],
                keyTakeaway: "Use `for item in collection` to loop. Use `...` for inclusive ranges and `..<` for exclusive ranges."
            ),
            quiz: QuizQuestion(
                question: "Which loop prints exactly the numbers 1, 2, and 3?",
                scenario: "You want a loop to execute 3 times, printing 1 to 3.",
                options: [
                    "for i in 1..<3 { print(i) }",
                    "for i in 1...3 { print(i) }",
                    "for i = 1 to 3 { print(i) }",
                    "for (i = 1; i <= 3; i++) { print(i) }"
                ],
                correctIndex: 1,
                explanation: "`1...3` is a closed range that includes both 1 and 3."
            )
        ),
        
        Topic(
            id: 10,
            title: "while loops",
            subtitle: "Conditional iteration",
            icon: "arrow.3.trianglepath",
            lesson: Lesson(
                explanation: "A `while` loop evaluates a condition before each iteration, and continues running as long as the condition is true. It looks very similar to `if` statements.",
                codeExamples: [
                    CodeExample(
                        title: "Basic while loop",
                        code: """
var count = 5

while count > 0 {
    print("\\(count)...")
    count -= 1
}
print("Liftoff!")
""",
                        explanation: "Just like `if` statements, you don't need parentheses around the condition, but the curly braces `{}` are required."
                    )
                ],
                keyTakeaway: "Use `while` loops when the number of iterations isn't known ahead of time."
            ),
            quiz: QuizQuestion(
                question: "What does `count -= 1` do in the while loop example?",
                scenario: "while count > 0 { count -= 1 }",
                options: [
                    "Sets count to exactly -1.",
                    "Decreases count by 1.",
                    "Checks if count is equal to -1.",
                    "It is a syntax error in Swift."
                ],
                correctIndex: 1,
                explanation: "`count -= 1` is shorthand for `count = count - 1`. Swift does not have the `--` or `++` operators."
            )
        ),
        
        Topic(
            id: 11,
            title: "Structs",
            subtitle: "Custom data types",
            icon: "cube.fill",
            lesson: Lesson(
                explanation: "In Swift, you typically use `struct` instead of `class` to model data. Structs are \"value types\", meaning they are copied when passed around, which prevents unintended side effects. Swift also gives them a free initializer!",
                codeExamples: [
                    CodeExample(
                        title: "Defining and using a struct",
                        code: """
struct User {
    let username: String
    var age: Int
}

// Swift automatically creates this initializer for you!
var myUser = User(username: "p_leung", age: 30)

// Accessing properties
print(myUser.username)

// Updating properties (requires the instance to be a 'var')
myUser.age = 31
""",
                        explanation: "Unlike classes, you rarely need to write an `init` method for a struct."
                    )
                ],
                keyTakeaway: "Use `struct` for modeling data. They are safer and more efficient than classes."
            ),
            quiz: QuizQuestion(
                question: "Why does `myUser.age = 31` require `myUser` to be declared with `var`?",
                scenario: "var myUser = User(username: \"p_leung\", age: 30)",
                options: [
                    "Because all structs must be declared with var.",
                    "Because age was declared as an Int.",
                    "Because structs are value types; to mutate a property, the struct instance itself must be mutable.",
                    "It doesn't; you can mutate properties of a struct even if it's assigned to a let constant."
                ],
                correctIndex: 2,
                explanation: "If you assign a struct to a `let` constant, all of its properties become immutable, even if they were declared as `var` inside the struct."
            )
        ),
        
        Topic(
            id: 12,
            title: "Closures",
            subtitle: "Functions without names",
            icon: "curlybraces",
            lesson: Lesson(
                explanation: "Closures are anonymous functions that you can pass around like variables. They are heavily used in SwiftUI for things like button actions or list rendering.",
                codeExamples: [
                    CodeExample(
                        title: "Trailing closure syntax",
                        code: """
let numbers = [1, 2, 3, 4]

// Standard closure syntax
let doubled = numbers.map({ (number: Int) -> Int in
    return number * 2
})

// Trailing closure syntax (shorthand, idiomatic Swift)
let tripled = numbers.map { number in
    number * 3
}

// Ultra-shorthand (using $0 for the first argument)
let quadrupled = numbers.map { $0 * 4 }
""",
                        explanation: "If a closure is the last argument to a function, you can close the parentheses early and put the closure in curly braces `{}`. This is called Trailing Closure Syntax."
                    )
                ],
                keyTakeaway: "Closures are blocks of code. Use trailing closure syntax for cleaner, more idiomatic Swift code."
            ),
            quiz: QuizQuestion(
                question: "In the shorthand closure `numbers.map { $0 * 4 }`, what does `$0` represent?",
                scenario: "You are mapping an array of numbers.",
                options: [
                    "The index of the current element.",
                    "The first argument passed to the closure (the current element).",
                    "A variable named '$0'.",
                    "A macro that means 'return'."
                ],
                correctIndex: 1,
                explanation: "Swift provides shorthand argument names (`$0`, `$1`, `$2`, etc.) so you don't have to explicitly name the arguments in simple closures."
            )
        )
    ]
}
