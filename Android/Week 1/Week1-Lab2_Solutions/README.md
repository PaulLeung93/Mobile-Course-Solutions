# Week 1 Session 2 — Monster Slayer (Complete Solution)

**⚠️ This is instructor-only reference material. Do not share with students.**

## Overview

A battle screen where the player attacks a dragon boss. The dragon starts with 20 HP, reacts as its health drops through different phases, and is defeated when HP reaches zero. Everything on screen is driven by **state** — the core concept of this session.

## What This Solution Covers

### Core Lab Steps (0–7)
- **State variables**: `monsterHp` and `attackCount` using `remember { mutableIntStateOf() }`
- **Phase system**: `getMonsterPhase()` returns emoji + taunt based on HP thresholds using `when`
- **Attack button**: Decrements HP by 1, increments attack count, guarded against negative HP
- **HP display**: Numeric display + animated progress bar with color transitions (green → yellow → red)
- **Attack counter**: Independent state variable displayed below HP
- **Conditional reset**: "New Monster" button only appears when dragon is defeated (HP = 0)

### Stretch Feature
- **Swipe combo**: ↑ ↑ ↓ gesture sequence triggers instant kill
- Visual combo progress indicator shows which inputs have been registered
- Uses `detectDragGestures` for swipe detection with direction thresholds

## Key Concepts Demonstrated

| Concept | Implementation |
|---|---|
| State declaration | `var monsterHp by remember { mutableIntStateOf(20) }` |
| Conditional UI | `if (monsterHp == 0) { ... }` to show/hide reset button |
| Derived state | `val phase = getMonsterPhase(monsterHp)` — recomputed on every recomposition |
| Animated state | `animateColorAsState` for HP bar color, `animateFloatAsState` for HP bar progress |
| Data class | `MonsterPhase(emoji, taunt)` to bundle related phase data |
| When expression | Pattern matching on HP ranges for phase transitions |
| Gesture detection | `pointerInput` + `detectDragGestures` for swipe combo |

## Starter Code

The corresponding starter code is located at:
`../../Week1-Lab2/` (when available)
