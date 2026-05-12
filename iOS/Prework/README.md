# Prework — CodePath Mobile Development

## Overview
A self-guided iOS Swift language primer covering 12 fundamental syntax topics, each followed by a quiz question. Submitted as part of the enrollment process for CodePath's **Mobile Development in the Age of AI** course.

## Language
- Swift (iOS)

## Score
- Result: __ / 12

## Reflections
<!-- Any thoughts on the prework — what clicked, what was challenging, what you're curious about heading into Week 1 -->


## App Screens

### Home — Welcome Screen
The entry point of the app. Displays a summary of the primer (12 topics, 12 questions, pass rate, and estimated time), along with a description of the curriculum. Tap **Start Primer** to begin the guided lesson flow.

### Learn — Swift Fundamentals Hub
A scrollable list of all 12 topics. Tap any topic card to open a deep-dive lesson for that topic, complete with key concepts, code examples, and a topic quiz — so you can study at your own pace outside the main flow.

Topics covered:
1. `let` vs `var`
2. Type inference
3. String interpolation
4. Functions
5. `if`/`else`
6. `switch`
7. Optionals & nil coalescing
8. Arrays
9. `for` loops & ranges
10. `while` loops
11. Structs
12. Closures

### Reference — Swift Cheat Sheet
A quick-reference guide with concise, copy-ready code snippets for the most common Swift syntax. Organized by topic and designed to be a fast lookup tool while you code.

### Code — Swift Playground
An embedded browser window that loads [swiftfiddle.com](https://swiftfiddle.com) — an online Swift playground. Use it to experiment with code snippets directly from the app without leaving Xcode.

---

## How to Run

Follow these steps carefully — this may be your first iOS project, so each step is explained in detail.

### Prerequisites

#### 1. Install Git
Git is the tool used to download (clone) this repository to your computer.
- **Mac**: Git is typically pre-installed. Open Terminal and run `git --version` to verify. If prompted to install Xcode Command Line Tools, click **Install**.

Once installed, verify it works by running:
```
git --version
```

#### 2. Install Xcode
Xcode is Apple's official IDE for iOS development. It includes everything you need — the code editor, iOS simulator, and build tools.
1. Open the **App Store** on your Mac and search for **Xcode**
2. Click **Get** then **Install** — it's free. You may be asked to sign in with your Apple ID
3. Wait for the download to complete — this can take 30–60 minutes depending on your connection
4. **Open Xcode** once installed. The first launch will show "Installing additional components" with a progress bar — this is normal. Let it finish (another 5–10 min)
5. When Xcode finishes loading you'll see the Welcome screen — you're ready to go

> **Note:** Xcode requires macOS and several gigabytes of disk space. Make sure you have at least 15 GB free.

### Cloning the Repository

#### Option A: Using the Terminal (recommended)
1. Open **Terminal**
2. Navigate to the folder where you want to save the project:
   ```
   cd ~/Desktop
   ```
3. Clone the repository:
   ```
   git clone https://github.com/<your-username>/<repo-name>.git
   ```
4. Once cloning finishes, a new folder will appear with the project files inside.

#### Option B: Using GitHub Desktop
1. Download GitHub Desktop from [desktop.github.com](https://desktop.github.com) and sign in with your GitHub account
2. Click **File → Clone Repository**
3. Paste the repository URL and choose a local folder to save it
4. Click **Clone**

### Opening the Project in Xcode
1. Launch **Xcode**
2. On the Welcome screen, click **Open Existing Project** (not "Create New Project")
3. In the file browser, navigate to the folder where you cloned the repository
4. Select the `SwiftPrimerApp` folder (the one that contains the Swift source files) and click **Open**

### Running the App

You can run the app on either a simulator (a virtual iPhone on your computer) or a physical iOS device.

#### Option A: iOS Simulator (recommended for beginners)
1. In Xcode, click the device selector dropdown near the top-left of the toolbar
2. Choose an iPhone model (e.g., **iPhone 16**) from the list
3. Click the **Run** button (▶) in the toolbar — the app will build and launch in the simulator

#### Option B: Physical iOS Device
1. Connect your iPhone to your Mac with a USB cable
2. When prompted, tap **Trust** on your iPhone
3. In Xcode, select your phone from the device dropdown
4. You may need to set up a signing team under **Signing & Capabilities** — use your Apple ID (free)
5. Click the **Run** button (▶) to build and install the app on your phone

---

## Submitting Your Work

Once you have completed all 12 lessons and quiz questions:
1. Note your final score from the Results screen
2. Fill in the **Score** section at the top of this README
3. Add your **Reflections** — a few sentences about what you learned or found challenging
4. Push your changes to GitHub and submit the repository link as instructed

---

## Resources
- [Swift Documentation](https://docs.swift.org/swift-book/)
- [SwiftUI Tutorials](https://developer.apple.com/tutorials/swiftui)
- [Swift Fiddle — Online Playground](https://swiftfiddle.com)
