// ✅ Complete — game data. Read as reference; do not modify.
// The top half (colors, classes, stats, weapons, abilities, face options) is relevant to your tasks.
// The bottom half (WeaponConfig, weaponConfigs, SpriteFrames) is sprite engine internals — skip it.
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
    "Warrior" to listOf("Longsword", "Battle Axe", "Light Saber", "Halberd"),
    "Mage"    to listOf("Arcane Staff", "Spell Tome", "Crystal Orb", "Elder Wand"),
    "Rogue"   to listOf("Dagger", "Shuriken", "Whip", "Scythe"),
    "Ranger"  to listOf("Longbow", "Crossbow", "Boomerang", "Slingshot"),
)

val weaponEmojis = mapOf(
    "Longsword"     to "🗡️", "Battle Axe"    to "🪓",
    "Light Saber"   to "🗡️", "Halberd"       to "🔱",
    "Arcane Staff"  to "🪄", "Spell Tome"    to "📖",
    "Crystal Orb"   to "🔮", "Elder Wand"    to "🪵",
    "Dagger"        to "🗡️", "Shuriken"      to "🌟",
    "Whip"          to "➰", "Scythe"        to "⚔️",
    "Longbow"       to "🏹", "Crossbow"      to "🎯",
    "Boomerang"     to "🪃", "Slingshot"     to "☄️",
)

// ── Ability data ──────────────────────────────────────────────────────────────
// Abilities are also class-specific — each class has 4 unique abilities
val abilitiesByClass = mapOf(
    "Warrior" to listOf("Juggernaut Charge", "Ground Smash", "Berserker Rage", "Iron Will"),
    "Mage"    to listOf("Fireball", "Frost Nova", "Arcane Surge", "Mana Shield"),
    "Rogue"   to listOf("Shadow Clones", "Shadowstep", "Vanish", "Poison Strike"),
    "Ranger"  to listOf("Eagle Eye", "Rain of Arrows", "Track Prey", "Nature's Call"),
)

val abilityEmojis = mapOf(
    "Juggernaut Charge" to "🦏", "Ground Smash"   to "💥",
    "Berserker Rage" to "😤", "Iron Will"      to "⚙️",
    "Fireball"       to "🔥", "Frost Nova"     to "❄️",
    "Arcane Surge"   to "⚡", "Mana Shield"    to "🌀",
    "Shadow Clones"  to "👥", "Shadowstep"     to "👤",
    "Vanish"         to "💨", "Poison Strike"  to "🧪",
    "Eagle Eye"      to "🦅", "Rain of Arrows" to "🏹",
    "Track Prey"     to "🐾", "Nature's Call"  to "🌿",
)

// ── Face customization data ───────────────────────────────────────────────────

data class SkinTone(val key: String, val label: String, val previewColor: Color)

val skinTones = listOf(
    SkinTone("light",  "Light",  Color(0xFFF5D0A9)),
    SkinTone("amber",  "Amber",  Color(0xFFD4A574)),
    SkinTone("bronze", "Bronze", Color(0xFFC18040)),
    SkinTone("brown",  "Brown",  Color(0xFF8B5E3C)),
    SkinTone("olive",  "Olive",  Color(0xFF9C8455)),
    SkinTone("taupe",  "Taupe",  Color(0xFF8B7355)),
)

data class EyeOption(val key: String, val label: String, val previewColor: Color)

val eyeOptions = listOf(
    EyeOption("blue",   "Blue",   Color(0xFF4A90D9)),
    EyeOption("brown",  "Brown",  Color(0xFF8B5E3C)),
    EyeOption("gray",   "Gray",   Color(0xFF9B9B9B)),
    EyeOption("green",  "Green",  Color(0xFF5B9E5B)),
    EyeOption("orange", "Orange", Color(0xFFE67E22)),
    EyeOption("purple", "Purple", Color(0xFF9B59B6)),
    EyeOption("red",    "Red",    Color(0xFFE74C3C)),
    EyeOption("yellow", "Yellow", Color(0xFFE8C840)),
)

data class HairStyle(val key: String, val label: String, val folder: String)

val hairStyles = listOf(
    HairStyle("bangs",          "Bangs",      "hair/bangs/male"),
    HairStyle("bangsshort",     "Short Bangs","hair/bangsshort/male"),
    HairStyle("bangslong",      "Long Bangs", "hair/bangslong/male"),
    HairStyle("buzzcut",        "Buzzcut",    "hair/buzzcut/adult"),
    HairStyle("bob",            "Bob",        "hair/bob/adult"),
    HairStyle("bob_side_part",  "Side Part",  "hair/bob_side_part/adult"),
    HairStyle("braid",          "Braid",      "hair/braid/male"),
    HairStyle("braid2",         "Double Braid","hair/braid2/male"),
    HairStyle("afro",           "Afro",       "hair/afro/male"),
    HairStyle("curly_short",    "Curly Short","hair/curly_short/adult"),
    HairStyle("curly_long",     "Curly Long", "hair/curly_long/male"),
    HairStyle("bedhead",        "Bedhead",    "hair/bedhead/male"),
    HairStyle("cowlick",        "Cowlick",    "hair/cowlick/adult"),
    HairStyle("cornrows",       "Cornrows",   "hair/cornrows/adult"),
    HairStyle("dreadlocks_short","Dreads Short","hair/dreadlocks_short/adult"),
    HairStyle("dreadlocks_long", "Dreads Long", "hair/dreadlocks_long/male"),
)

data class HairColor(val key: String, val label: String, val previewColor: Color)

val hairColors = listOf(
    HairColor("black",      "Black",    Color(0xFF1A1A1A)),
    HairColor("raven",      "Raven",    Color(0xFF2C2C3A)),
    HairColor("dark_brown", "Dk Brown", Color(0xFF4A2E1A)),
    HairColor("chestnut",   "Chestnut", Color(0xFF7B4513)),
    HairColor("ginger",     "Ginger",   Color(0xFFB25219)),
    HairColor("red",        "Red",      Color(0xFFCC3300)),
    HairColor("blonde",     "Blonde",   Color(0xFFE8C84A)),
    HairColor("ash",        "Ash",      Color(0xFFB0B0A8)),
)

data class EarOption(val key: String, val label: String, val emoji: String)

val earOptions = listOf(
    EarOption("elven",  "Human", "👂"),
    EarOption("medium", "Elven", "🧝"),
)

// asset path helpers used by FaceScreen and CharacterSprite
fun bodyAssetPath(skin: String)                 = "sprites/face/body_$skin.png"
fun headAssetPath(skin: String)                 = "sprites/face/head_$skin.png"
fun eyesAssetPath(color: String)                = "sprites/face/eyes_$color.png"
fun hairAssetPath(style: String, color: String) = "sprites/face/hair_${style}_$color.png"
fun earsAssetPath(type: String, skin: String)   = "sprites/face/ears_${type}_$skin.png"
const val DEFAULT_SHIRT_PATH = "sprites/face/default_shirt.png"
const val DEFAULT_PANTS_PATH = "sprites/face/default_pants.png"

// ── Character card description ────────────────────────────────────────────────
fun buildDescription(
    characterClass: String,
    stat: String,
    weapon: String,
    ability: String
): String = "A $stat-enhanced $characterClass, wielding a $weapon and mastering $ability."

// ═════════════════════════════════════════════════════════════════════════════
// Everything below this line is sprite animation internals used by engine/Sprite.kt.
// You don't need to read or modify anything from here down.
// ═════════════════════════════════════════════════════════════════════════════

// ── Sprite animation data class ───────────────────────────────────────────────
// Used by Sprite for single-row (non-LPC) sprite sheets loaded from assets/.
// assetPath is relative to the assets/ root, e.g. "sprites/monsters/dragon_sheet.png".
data class CharacterAnim(
    val assetPath: String,
    val frameCount: Int,
    val isLooping: Boolean
)

// ── Weapon config ─────────────────────────────────────────────────────────────
// Most weapons are a single standard LPC sheet (13×21) — those use the defaults.
// Weapons with a separate attack sheet or a behind-body layer override here.
//
// attackRow    – south-facing row within the attack sheet (0-indexed)
// attackFrames – number of animation frames in that row
data class WeaponConfig(
    val walkPath: String,
    val attackPath: String        = walkPath,
    val attackRow: Int            = SpriteFrames.SLASH_ROW,
    val attackFrames: Int         = SpriteFrames.SLASH_FRAMES,
    val walkBehindPath: String?   = null,
    val attackBehindPath: String? = null,
    val attackCellSize: Int       = 64,
    val bodyAttackRow: Int?       = null,
    val bodyAttackFrames: Int?    = null,
    // Override the weapon's walk row/cell-size for non-standard compact sheets
    val walkRow: Int?             = null,
    val walkCellSize: Int         = 64,
)

private fun defaultWeaponPath(weapon: String) =
    "sprites/weapons/${weapon.lowercase().replace(" ", "_").replace("'", "")}_sheet.png"

/** Returns the [WeaponConfig] for [weapon], falling back to a single-sheet default. */
fun weaponConfig(weapon: String): WeaponConfig =
    weaponConfigs[weapon] ?: WeaponConfig(walkPath = defaultWeaponPath(weapon))

val weaponConfigs: Map<String, WeaponConfig> = mapOf(
    "Arcane Staff" to WeaponConfig(
        walkPath         = "sprites/weapons/arcane_staff_sheet.png",
        walkBehindPath   = "sprites/weapons/arcane_staff_behind_sheet.png",
        attackBehindPath = "sprites/weapons/arcane_staff_behind_sheet.png",
        attackRow        = SpriteFrames.WALK_ROW,
        attackFrames     = SpriteFrames.SPELLCAST_FRAMES,
        bodyAttackRow    = SpriteFrames.SPELLCAST_ROW
    ),
    "Crystal Orb" to WeaponConfig(
        walkPath         = "sprites/weapons/crystal_orb_sheet.png",
        walkBehindPath   = "sprites/weapons/crystal_orb_behind_sheet.png",
        attackBehindPath = "sprites/weapons/crystal_orb_behind_sheet.png",
        attackRow        = SpriteFrames.WALK_ROW,
        attackFrames     = SpriteFrames.SPELLCAST_FRAMES,
        bodyAttackRow    = SpriteFrames.SPELLCAST_ROW
    ),
    "Spell Tome" to WeaponConfig(
        walkPath         = "sprites/weapons/spell_tome_sheet.png",
        attackPath       = "sprites/weapons/spell_tome_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 7,
        attackCellSize   = 192,
        bodyAttackRow    = SpriteFrames.SPELLCAST_ROW
    ),
    "Elder Wand" to WeaponConfig(
        walkPath         = "sprites/weapons/elder_wand_sheet.png",
        walkBehindPath   = "sprites/weapons/elder_wand_behind_sheet.png",
        attackBehindPath = "sprites/weapons/elder_wand_behind_sheet.png",
        attackRow        = SpriteFrames.WALK_ROW,
        attackFrames     = SpriteFrames.SPELLCAST_FRAMES,
        bodyAttackRow    = SpriteFrames.SPELLCAST_ROW
    ),
    "Battle Axe" to WeaponConfig(
        walkPath         = "sprites/weapons/battle_axe_sheet.png",
        attackPath       = "sprites/weapons/battle_axe_attack_sheet.png",
        // South-facing slash = row 2 in the 4-row attack_slash sheet (192x192 cells)
        attackRow        = 2,
        attackFrames     = 6,
        walkBehindPath   = "sprites/weapons/battle_axe_behind_sheet.png",
        attackBehindPath = "sprites/weapons/battle_axe_attack_behind_sheet.png",
        attackCellSize   = 192,
    ),
    "Longsword" to WeaponConfig(
        walkPath         = "sprites/weapons/longsword_sheet.png",
        attackPath       = "sprites/weapons/longsword_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 6,
        walkBehindPath   = "sprites/weapons/longsword_behind_sheet.png",
        attackBehindPath = "sprites/weapons/longsword_attack_behind_sheet.png",
        attackCellSize   = 192,
    ),
    "Light Saber" to WeaponConfig(
        walkPath         = "sprites/weapons/lightsaber_sheet.png",
        attackPath       = "sprites/weapons/lightsaber_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 6,
        walkBehindPath   = "sprites/weapons/lightsaber_behind_sheet.png",
        attackBehindPath = "sprites/weapons/lightsaber_attack_behind_sheet.png",
        attackCellSize   = 192,
    ),
    "Halberd" to WeaponConfig(
        walkPath         = "sprites/weapons/halberd_sheet.png",
        attackPath       = "sprites/weapons/halberd_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 6,
        walkBehindPath   = "sprites/weapons/halberd_behind_sheet.png",
        attackBehindPath = "sprites/weapons/halberd_attack_behind_sheet.png",
        attackCellSize   = 192,
    ),
    "Scythe" to WeaponConfig(
        walkPath         = "sprites/weapons/scythe_sheet.png",
        attackPath       = "sprites/weapons/scythe_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 6,
        walkBehindPath   = "sprites/weapons/scythe_behind_sheet.png",
        attackBehindPath = "sprites/weapons/scythe_attack_behind_sheet.png",
        attackCellSize   = 192,
    ),
    "Shuriken" to WeaponConfig(
        walkPath         = "sprites/weapons/shuriken_sheet.png",
        attackPath       = "sprites/weapons/shuriken_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 7,
        attackCellSize   = 192,
        bodyAttackRow    = SpriteFrames.SPELLCAST_ROW,
        bodyAttackFrames = SpriteFrames.SPELLCAST_FRAMES
    ),
    "Whip" to WeaponConfig(
        walkPath         = "sprites/weapons/whip_sheet.png",
        attackPath       = "sprites/weapons/whip_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 8,
        walkBehindPath   = "sprites/weapons/whip_behind_sheet.png",
        attackBehindPath = "sprites/weapons/whip_attack_behind_sheet.png",
        attackCellSize   = 192
    ),
    "Longbow" to WeaponConfig(
        walkPath         = "sprites/weapons/longbow_sheet.png",
        walkBehindPath   = "sprites/weapons/longbow_behind_sheet.png",
        attackBehindPath = "sprites/weapons/longbow_behind_sheet.png",
        attackRow        = SpriteFrames.SHOOT_ROW,
        attackFrames     = SpriteFrames.SHOOT_FRAMES,
        bodyAttackRow    = SpriteFrames.SHOOT_ROW,
        bodyAttackFrames = SpriteFrames.SHOOT_FRAMES
    ),
    "Crossbow" to WeaponConfig(
        walkPath         = "sprites/weapons/crossbow_sheet.png",
        walkBehindPath   = "sprites/weapons/crossbow_behind_sheet.png",
        attackBehindPath = "sprites/weapons/crossbow_behind_sheet.png",
        attackRow        = SpriteFrames.THRUST_ROW,
        attackFrames     = SpriteFrames.THRUST_FRAMES,
        bodyAttackRow    = SpriteFrames.THRUST_ROW,
        bodyAttackFrames = SpriteFrames.THRUST_FRAMES
    ),
    "Slingshot" to WeaponConfig(
        walkPath         = "sprites/weapons/slingshot_sheet.png",
        walkBehindPath   = "sprites/weapons/slingshot_behind_sheet.png",
        attackBehindPath = "sprites/weapons/slingshot_behind_sheet.png",
        attackRow        = SpriteFrames.SHOOT_ROW,
        attackFrames     = SpriteFrames.SHOOT_FRAMES,
        bodyAttackRow    = SpriteFrames.SHOOT_ROW,
        bodyAttackFrames = SpriteFrames.SHOOT_FRAMES
    ),
    "Boomerang" to WeaponConfig(
        walkPath         = "sprites/weapons/boomerang_sheet.png",
        attackPath       = "sprites/weapons/boomerang_attack_sheet.png",
        attackRow        = 2,
        attackFrames     = 6,
        attackCellSize   = 192
    ),
)

// ── LPC sprite sheet constants ────────────────────────────────────────────────
// Standard Universal LPC Spritesheet layout: 13 columns × 21 rows of 64×64 frames.
// All layer PNGs (body, armor, weapon) share the same grid so they composite in sync.
object SpriteFrames {
    const val TOTAL_COLUMNS = 13
    const val TOTAL_ROWS    = 21
    const val WALK_ROW      = 10  // walk south (facing viewer) — used for idle
    const val WALK_FRAMES   = 9
    const val SLASH_ROW     = 14  // slash south — used for attack
    const val SLASH_FRAMES  = 6
    const val SPELLCAST_ROW = 2   // spellcast south — used for throws/magic
    const val SPELLCAST_FRAMES = 7
    const val SHOOT_ROW     = 18  // shoot south — used for bows
    const val SHOOT_FRAMES  = 13
    const val THRUST_ROW    = 6   // thrust south — used for spears/crossbows
    const val THRUST_FRAMES = 8
}
