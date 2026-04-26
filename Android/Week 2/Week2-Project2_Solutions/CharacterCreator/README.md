# ⚔️ Character Creator

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)](https://www.android.com)
[![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Character Creator** is a premium, multi-step character generation tool built with Jetpack Compose. Designed as a solution for Week 2 of the Codepath Mobile Course, it allows users to craft unique RPG characters by customizing physical attributes, selecting classes, and equipping class-specific weaponry.

The application features a bespoke rendering engine that composites multiple sprite layers in real-time, delivering a seamless and animated preview of your legendary hero.

---

## 🎬 App Walkthrough

<div align="center">
  <img src="walkthroughs/walkthrough.gif" width="350" style="border-radius: 15px; box-shadow: 0 4px 8px rgba(0,0,0,0.2);" />
  <p><i>Real-time character customization and animation preview.</i></p>
</div>

---

## ✨ Core Features

- **🎨 Deep Customization**: Express your creativity with a wide range of physical options:
  - **Skin Tones**: 6 diverse tones (Light, Amber, Bronze, Brown, Olive, Taupe).
  - **Hair Styles**: 15+ styles including Bangs, Buzzcuts, Afros, and Braids.
  - **Hair Colors**: 8 classic and unique shades.
  - **Eye Colors**: 8 vibrant options.
  - **Ear Types**: Choose between Human and Elven traits.
- **🛡️ Class-Based Gear**: Select from four distinct RPG archetypes:
  - **Warrior**: Masters of direct combat, wielding Longswords and Battle Axes.
  - **Mage**: Commanders of arcane power with Staffs and Spell Tomes.
  - **Rogue**: Masters of stealth equipped with Shurikens and Whips.
  - **Ranger**: Survivalists proficient with Longbows and Boomerangs.
- **🏃 Dynamic Rendering Engine**:
  - **Layered Composition**: Characters are rendered by stacking layers (Body → Clothes → Hair → Armor → Weapon) with frame-perfect synchronization.
  - **Interactive Previews**: Watch your character come to life with idle and class-specific combat animations.
  - **Visual Effects**: Includes special effects like shadow clones for the Rogue and dash offsets for the Warrior.

---

## 🛠️ Technical Implementation

- **Language**: Kotlin
- **UI Architecture**: Jetpack Compose (Declarative UI)
- **State Management**: Navigation-based state passing and Compose `remember` states.
- **Graphics Pipeline**: Custom `Canvas` rendering for efficient sprite manipulation.
- **Animation**: Coroutine-driven frame updates (100ms intervals) for smooth pixel-art motion.

### 📂 Project Structure

```text
com.codepath.charactercreator/
├── MainActivity.kt        # Navigation Host & Step-by-Step Flow
├── CharacterData.kt      # Design tokens, color palettes, and asset mappings
├── Sprite.kt             # The core of the rendering engine
└── screens/              # Individual Compose screens for the creation journey
```

---

## 🚀 Setup & Installation

1. **Clone** the repository to your local machine.
2. Open the project in **Android Studio** (Hedgehog or later recommended).
3. Ensure you have **JDK 17+** and **Android SDK 34** configured.
4. Sync project with Gradle files.
5. Run on an emulator or physical device with **API 24+**.

---

## 📜 Credits & Acknowledgments

- **Assets**: Character sprites are based on the **Liberated Pixel Cup (LPC)** open-source standard.
- **Course**: Developed as a solution project for the **Codepath Mobile Development Course**.

---
<div align="center">
  <sub>Built with ❤️ by Paul Leung</sub>
</div>
