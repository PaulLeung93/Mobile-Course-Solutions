# Monster Slayer - 2D Battle Edition

An immersive 2D RPG battle experience built with **Jetpack Compose**. This project demonstrates advanced UI techniques, custom frame-by-frame animations using `Canvas`, and dynamic state-driven gameplay mechanics.

## 🎮 Features

### ⚔️ Dynamic Combat System
- **Animated Sprites**: Custom pixel-art characters (Hero and Slime) with Idle, Attack, Hurt, and Death states.
- **Physical Feedback**: The hero physically leaps across the screen to make contact during attacks using `animateDpAsState`.
- **Hurt Reactions**: Monsters respond to strikes with a dedicated "Hurt" flash animation, providing immediate visual weight to every tap.

### 🧩 Secret Combo Mechanic
- **Randomized 5-Gesture Sequences**: Victory isn't just about tapping! Discover a randomized sequence of 5 directional swipes (↑, ↓, ←, →) to instantly defeat the monster.
- **Dynamic Hints**: A real-time UI tracker updates as you input each gesture of the secret combo.

### 🎨 RPG-Style Layout
- **Thematic Positioning**: A structured layout featuring a top-pinnned HP bar, a central battle row where entities face off, and a bottom-aligned command center.
- **Responsive Animations**: Smooth transitions for HP bar colors (Green → Yellow → Red) and progress tracking.

## 🛠️ Technical Highlights
- **Jetpack Compose Canvas**: High-performance sprite rendering using frame-by-frame sheets.
- **State Management**: Robust use of `mutableStateOf` and `LaunchedEffect` to sync game logic with animations.
- **Asset Filtering**: Utilizes `FilterQuality.None` to maintain crisp, "pixel-perfect" art rendering.

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or newer.
- Android SDK 34+.
- A physical device or emulator (Pixel 9 Pro recommended).

### Usage
1. Open the project in Android Studio.
2. Run the `app` module on your device.
3. Tap the **⚔️** button to attack the monster.
4. Try swiping in different directions to trigger the **Secret Combo**.
5. Once defeated, tap **Battle Again** to face a new challenge with a randomized combo!

## 📜 Credits
- **Pixel Art**: OboroPixel (Characters Animations Asset Pack).
- **Core Implementation**: Part of the Pathfinders Mobile Development Course.
