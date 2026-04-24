package com.codepath.charactercreator

import androidx.compose.ui.graphics.Color

// ── Shared color palette ──────────────────────────────────────────────────────
val DarkBg       = Color(0xFF1A1A2E)
val CardBg       = Color(0xFF16213E)
val CardBgDim    = Color(0xFF0F3460)
val PurpleAccent = Color(0xFF534AB7)
val PurpleLight  = Color(0xFF7F52FF)
val GoldAccent   = Color(0xFFFFD700)
val TextPrimary  = Color.White
val TextMuted    = Color.White.copy(alpha = 0.55f)

// Each class has its own accent color used for selection highlights and card borders
val classColors = mapOf(
    "Warrior" to Color(0xFFE53935),
    "Mage"    to Color(0xFF5C6BC0),
    "Rogue"   to Color(0xFF00897B),
    "Ranger"  to Color(0xFF558B2F),
)

// ── Class data ────────────────────────────────────────────────────────────────
val classes = listOf("Warrior", "Mage", "Rogue", "Ranger")

val classEmojis = mapOf(
    "Warrior" to "⚔️",
    "Mage"    to "🔮",
    "Rogue"   to "🗡️",
    "Ranger"  to "🏹",
)

val classDescriptions = mapOf(
    "Warrior" to "Excels in direct combat",
    "Mage"    to "Commands arcane power",
    "Rogue"   to "Strikes from the shadows",
    "Ranger"  to "Commands the wilderness",
)

// ── Stat data ─────────────────────────────────────────────────────────────────
val stats = listOf("Strength", "Intelligence", "Agility", "Luck")

val statEmojis = mapOf(
    "Strength"     to "💪",
    "Intelligence" to "🧠",
    "Agility"      to "⚡",
    "Luck"         to "🍀",
)

val statDescriptions = mapOf(
    "Strength"     to "Raw physical power",
    "Intelligence" to "Arcane insight",
    "Agility"      to "Speed and reflexes",
    "Luck"         to "Fortune's favor",
)

// ── Weapon data ───────────────────────────────────────────────────────────────
// Weapons are class-specific — WeaponScreen uses this map to build the correct list
val weaponsByClass = mapOf(
    "Warrior" to listOf("Longsword", "Battle Axe", "War Hammer", "Tower Shield"),
    "Mage"    to listOf("Arcane Staff", "Spell Tome", "Crystal Orb", "Runic Dagger"),
    "Rogue"   to listOf("Twin Daggers", "Smoke Bomb", "Garrote Wire", "Poison Vial"),
    "Ranger"  to listOf("Longbow", "Crossbow", "Throwing Axes", "Hunter's Trap"),
)

val weaponEmojis = mapOf(
    "Longsword"     to "🗡️", "Battle Axe"    to "🪓",
    "War Hammer"    to "🔨", "Tower Shield"  to "🛡️",
    "Arcane Staff"  to "🪄", "Spell Tome"    to "📖",
    "Crystal Orb"   to "🔮", "Runic Dagger"  to "✨",
    "Twin Daggers"  to "🗡️", "Smoke Bomb"    to "💨",
    "Garrote Wire"  to "🪢", "Poison Vial"   to "🧪",
    "Longbow"       to "🏹", "Crossbow"      to "🎯",
    "Throwing Axes" to "🪓", "Hunter's Trap" to "⛓️",
)

// ── Ability data ──────────────────────────────────────────────────────────────
// Abilities are also class-specific — each class has 4 unique abilities
val abilitiesByClass = mapOf(
    "Warrior" to listOf("Battle Cry", "Shield Bash", "Berserker Rage", "Iron Will"),
    "Mage"    to listOf("Fireball", "Frost Nova", "Arcane Surge", "Mana Shield"),
    "Rogue"   to listOf("Shadowstep", "Backstab", "Vanish", "Poison Strike"),
    "Ranger"  to listOf("Eagle Eye", "Rain of Arrows", "Track Prey", "Nature's Call"),
)

val abilityEmojis = mapOf(
    "Battle Cry"     to "📣", "Shield Bash"    to "🛡️",
    "Berserker Rage" to "😤", "Iron Will"      to "⚙️",
    "Fireball"       to "🔥", "Frost Nova"     to "❄️",
    "Arcane Surge"   to "⚡", "Mana Shield"    to "🌀",
    "Shadowstep"     to "👤", "Backstab"       to "🗡️",
    "Vanish"         to "💨", "Poison Strike"  to "🧪",
    "Eagle Eye"      to "🦅", "Rain of Arrows" to "🏹",
    "Track Prey"     to "🐾", "Nature's Call"  to "🌿",
)

// ── Character card description ────────────────────────────────────────────────
fun buildDescription(
    characterClass: String,
    stat: String,
    weapon: String,
    ability: String
): String = "A $stat-enhanced $characterClass, wielding a $weapon and mastering $ability."

// ── Sprite animation data class ───────────────────────────────────────────────
// Same structure as MonsterSlayer's SpriteAnim — horizontal sprite sheet, single row
data class CharacterAnim(
    val drawableId: Int,
    val frameCount: Int,
    val isLooping: Boolean
)

// ── Sprite frame counts ───────────────────────────────────────────────────────
// UPDATE these values once real sprite sheets are confirmed.
// Add your sheets to res/drawable-nodpi/ with these exact filenames:
//   warrior_idle_sheet.png, mage_idle_sheet.png, rogue_idle_sheet.png, ranger_idle_sheet.png
//   warrior_attack_sheet.png, mage_attack_sheet.png, rogue_attack_sheet.png, ranger_attack_sheet.png
object SpriteFrames {
    val idle   = mapOf("Warrior" to 6, "Mage" to 6, "Rogue" to 6, "Ranger" to 6)
    val attack = mapOf("Warrior" to 8, "Mage" to 8, "Rogue" to 8, "Ranger" to 8)
}
