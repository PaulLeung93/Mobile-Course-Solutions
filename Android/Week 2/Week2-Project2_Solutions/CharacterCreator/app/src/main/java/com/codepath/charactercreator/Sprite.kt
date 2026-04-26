package com.codepath.charactercreator

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ── Asset caches ──────────────────────────────────────────────────────────────
// Both caches are process-scoped so each asset is decoded / stat-checked once.
private val spriteCache      = mutableMapOf<String, ImageBitmap?>()
private val assetExistsCache = mutableMapOf<String, Boolean>()

/** Decode a PNG from assets/sprites/… into an [ImageBitmap], or null if missing. */
private fun loadSpriteAsset(context: android.content.Context, path: String): ImageBitmap? =
    spriteCache.getOrPut(path) {
        try {
            context.assets.open(path).use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        } catch (e: Exception) { null }
    }

/** Returns true if [path] exists in assets/; result is cached after the first check. */
private fun assetExists(context: android.content.Context, path: String): Boolean =
    assetExistsCache.getOrPut(path) {
        try { context.assets.open(path).close(); true } catch (e: Exception) { false }
    }

// ── SpriteLayer ───────────────────────────────────────────────────────────────
// Stateless: renders one frame from a standard LPC sprite sheet loaded from assets.
// The caller owns the frame counter so multiple layers animate in perfect sync.
//
// Asset path format: "sprites/{folder}/{name}_sheet.png"
@Composable
fun SpriteLayer(
    assetPath: String,
    frame: Int,
    rowIndex: Int,
    cellSize: Int = 64,
    modifier: Modifier = Modifier
) {
    val context     = LocalContext.current
    val imageBitmap = remember(assetPath) { loadSpriteAsset(context, assetPath) } ?: return

    Canvas(modifier = modifier) {
        val srcY   = rowIndex * cellSize

        // Safety: skip if row or column is out of bounds
        if (srcY + cellSize > imageBitmap.height || frame * cellSize >= imageBitmap.width) return@Canvas

        // All layers share a 64×128 logical space so scale stays at 1.25 for a 160 dp canvas,
        // keeping characters at a comfortable size with the head visible above centre.
        val logicalW = 64f
        val logicalH = 128f
        val canvasW  = size.width
        val canvasH  = size.height
        val scale    = minOf(canvasW / logicalW, canvasH / logicalH)

        val dstW = (cellSize * scale).toInt()
        val dstH = (cellSize * scale).toInt()
        
        // Base coordinate for a standard 64x64 body cell
        val baseDstX = (canvasW - logicalW * scale) / 2f
        val baseDstY = (canvasH / 2f) - (96f * scale) + (logicalH - 64f) * scale
        
        // If the cell is larger (e.g. 192x192 attack slash), it needs to be centered 
        // around the 64x64 body. Offset by half the difference.
        val offset = (cellSize - 64) / 2f
        val dstX = (baseDstX - offset * scale).toInt()
        val dstY = (baseDstY - offset * scale).toInt()

        drawImage(
            image         = imageBitmap,
            srcOffset     = IntOffset(frame * cellSize, srcY),
            srcSize       = IntSize(cellSize, cellSize),
            dstOffset     = IntOffset(dstX, dstY),
            dstSize       = IntSize(dstW, dstH),
            filterQuality = FilterQuality.None
        )
    }
}

// ── Sprite ────────────────────────────────────────────────────────────────────
// Animated single-layer renderer for single-row (non-LPC) sprite sheets.
// Used for standalone animations (e.g. monster sprites) loaded from assets.
@Composable
fun Sprite(
    anim: CharacterAnim,
    modifier: Modifier = Modifier,
    onAnimComplete: () -> Unit = {}
) {
    var currentFrame by remember(anim) { mutableIntStateOf(0) }

    LaunchedEffect(anim) {
        if (anim.frameCount <= 1) return@LaunchedEffect
        while (true) {
            delay(100)
            if (currentFrame < anim.frameCount - 1) {
                currentFrame++
            } else {
                if (anim.isLooping) {
                    currentFrame = 0
                } else {
                    onAnimComplete()
                    break
                }
            }
        }
    }

    val context     = LocalContext.current
    val imageBitmap = remember(anim.assetPath) { loadSpriteAsset(context, anim.assetPath) } ?: return

    Canvas(modifier = modifier) {
        val frameWidth  = imageBitmap.width / anim.frameCount
        val frameHeight = imageBitmap.height
        drawImage(
            image         = imageBitmap,
            srcOffset     = IntOffset(currentFrame * frameWidth, 0),
            srcSize       = IntSize(frameWidth, frameHeight),
            dstOffset     = IntOffset.Zero,
            dstSize       = IntSize(size.width.toInt(), size.height.toInt()),
            filterQuality = FilterQuality.None
        )
    }
}

// ── CharacterSprite ───────────────────────────────────────────────────────────
// Composites body + class layers + weapon from LPC sprite sheets stored in assets/.
// All layers share one frame counter so they animate in perfect sync.
// Falls back to an emoji placeholder if the shared body sheet is missing.
//
// Weapons with separate attack sheets or behind-body layers are declared in
// CharacterData.weaponConfigs. All other weapons fall back to a single-sheet default.
//
// Render order (back to front):
//   weapon_behind → body → pants → feet → arms → head → armor → weapon_front
@Composable
fun CharacterSprite(
    characterClass: String,
    weapon: String,
    ability: String? = null,
    skinTone: String? = null,
    eyeColor: String? = null,
    hairStyle: String? = null,
    hairColor: String? = null,
    earType: String? = null,
    modifier: Modifier = Modifier,
    isAttacking: Boolean = false,
    isBodyHidden: Boolean = false,
    onAttackComplete: () -> Unit = {}
) {
    val context = LocalContext.current

    val cls       = characterClass.lowercase()
    val bodyPath  = if (skinTone != null) bodyAssetPath(skinTone) else "sprites/shared/body_sheet.png"
    val headPath  = if (skinTone != null) headAssetPath(skinTone) else "sprites/shared/head_sheet.png"
    val earsPath  = if (skinTone != null && earType != null) earsAssetPath(earType, skinTone) else null
    val eyesPath  = if (eyeColor != null) eyesAssetPath(eyeColor) else null
    val hairPath  = if (hairStyle != null && hairColor != null) hairAssetPath(hairStyle, hairColor) else null
    val pantsPath = "sprites/$cls/pants_sheet.png"
    val feetPath  = "sprites/$cls/feet_sheet.png"
    val armsPath  = "sprites/$cls/arms_sheet.png"
    val armorPath = "sprites/$cls/armor_sheet.png"

    // Resolve weapon paths and attack-animation parameters from WeaponConfig
    val config          = weaponConfig(weapon)
    val isCasting        = isAttacking && ability != null && characterClass != "Mage" && ability != "Juggernaut Charge" && ability != "Shadowstep"
    val isJuggernaut     = isAttacking && ability == "Juggernaut Charge"
    val isShadowstep     = isAttacking && ability == "Shadowstep"
    val isWeaponAttack   = isAttacking && !isCasting && !isJuggernaut && !isShadowstep
    val isShadowstepping = isAttacking && ability == "Shadow Clones"

    val weaponFrontPath = if (isWeaponAttack) config.attackPath       else config.walkPath
    val weaponBehindPath= if (isWeaponAttack) config.attackBehindPath else config.walkBehindPath
    val weaponRow       = if (isWeaponAttack) config.attackRow        else if (isCasting) SpriteFrames.SPELLCAST_ROW else SpriteFrames.WALK_ROW
    val weaponCellSize  = if (isWeaponAttack) config.attackCellSize   else 64

    // Body/armor layers use standard LPC rows; weapon may override its own row during attacks
    val bodyRow       = if (isWeaponAttack) (config.bodyAttackRow ?: SpriteFrames.SLASH_ROW) else if (isCasting) SpriteFrames.SPELLCAST_ROW else SpriteFrames.WALK_ROW
    val bodyFrames    = if (isWeaponAttack) (config.bodyAttackFrames ?: SpriteFrames.SLASH_FRAMES) else if (isCasting) SpriteFrames.SPELLCAST_FRAMES else SpriteFrames.WALK_FRAMES
    val frameCount    = if (isWeaponAttack) config.attackFrames else if (isCasting) SpriteFrames.SPELLCAST_FRAMES else SpriteFrames.WALK_FRAMES

    // Emoji fallback if body sheet is missing
    if (!assetExists(context, bodyPath)) {
        Box(
            modifier = modifier.background(
                color = classColors[characterClass]?.copy(alpha = 0.25f)
                    ?: PurpleAccent.copy(alpha = 0.25f),
                shape = RoundedCornerShape(12.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = classEmojis[characterClass] ?: "⚔️", fontSize = 48.sp)
        }
        return
    }

    var currentFrame by remember(isAttacking) { mutableIntStateOf(0) }
    val dashOffsetY = remember(isAttacking) { Animatable(0f) }

    LaunchedEffect(isAttacking) {
        if (isJuggernaut || isShadowstep) {
            launch {
                dashOffsetY.animateTo(300f, animationSpec = tween(400, easing = FastOutSlowInEasing))
                dashOffsetY.snapTo(-300f)
                dashOffsetY.animateTo(0f, animationSpec = tween(400, easing = LinearOutSlowInEasing))
            }
        }
        while (true) {
            delay(100)
            if (currentFrame < frameCount - 1) {
                currentFrame++
            } else {
                if (!isAttacking) {
                    currentFrame = 0
                } else {
                    onAttackComplete()
                    break
                }
            }
        }
    }

    Box(modifier = modifier) {
        // Shadow clone ghosts — rendered first so they sit behind the real character
        if (isShadowstepping) {
            val currentBodyFrame = minOf(currentFrame, bodyFrames - 1)
            listOf(-44f to 0.20f, -22f to 0.36f).forEach { (xOff, alphaVal) ->
                Box(modifier = Modifier.fillMaxSize().offset(x = xOff.dp).alpha(alphaVal)) {
                    SpriteLayer(assetPath = bodyPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (earsPath != null && assetExists(context, earsPath)) SpriteLayer(assetPath = earsPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (assetExists(context, pantsPath)) SpriteLayer(assetPath = pantsPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (assetExists(context, feetPath))  SpriteLayer(assetPath = feetPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (assetExists(context, armsPath))  SpriteLayer(assetPath = armsPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (assetExists(context, headPath))  SpriteLayer(assetPath = headPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (eyesPath != null && assetExists(context, eyesPath)) SpriteLayer(assetPath = eyesPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (hairPath != null && assetExists(context, hairPath)) SpriteLayer(assetPath = hairPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                    if (assetExists(context, armorPath)) SpriteLayer(assetPath = armorPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize().offset(y = dashOffsetY.value.dp)) {
            // Behind-weapon layer renders first (beneath body)
            if (weaponBehindPath != null && assetExists(context, weaponBehindPath))
                SpriteLayer(assetPath = weaponBehindPath, frame = currentFrame, rowIndex = weaponRow, cellSize = weaponCellSize, modifier = Modifier.fillMaxSize())

            val currentBodyFrame = minOf(currentFrame, bodyFrames - 1)

            if (!isBodyHidden) {
                SpriteLayer(assetPath = bodyPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (earsPath != null && assetExists(context, earsPath))
                    SpriteLayer(assetPath = earsPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (assetExists(context, pantsPath))  SpriteLayer(assetPath = pantsPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (assetExists(context, feetPath))   SpriteLayer(assetPath = feetPath,   frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (assetExists(context, armsPath))   SpriteLayer(assetPath = armsPath,   frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (assetExists(context, headPath))   SpriteLayer(assetPath = headPath,   frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (eyesPath != null && assetExists(context, eyesPath))
                    SpriteLayer(assetPath = eyesPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (hairPath != null && assetExists(context, hairPath))
                    SpriteLayer(assetPath = hairPath, frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
                if (assetExists(context, armorPath))  SpriteLayer(assetPath = armorPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
            }

            // Front weapon layer renders last (on top)
            if (assetExists(context, weaponFrontPath)) SpriteLayer(assetPath = weaponFrontPath, frame = currentFrame, rowIndex = weaponRow, cellSize = weaponCellSize, modifier = Modifier.fillMaxSize())
        }

        // Shadowstep shadow aura — dark teal vignette that fades in/out with the ability
        if (isShadowstep) {
            val envelope = when {
                currentFrame < 2 -> currentFrame / 2f
                currentFrame < 6 -> 1f
                else             -> (8 - currentFrame) / 3f
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0f, 0.537f, 0.482f, (envelope * 0.25f).coerceIn(0f, 1f)),
                                Color(0f, 0.12f,  0.11f,  (envelope * 0.75f).coerceIn(0f, 1f))
                            )
                        )
                    )
            )
        }

        // Ability overlay renders on very top, only during attacks
        if (isAttacking && ability != null) {
            val abilityPath = "sprites/abilities/${ability.lowercase().replace(" ", "_").replace("'", "")}_sheet.png"
            if (assetExists(context, abilityPath)) {
                val abilityFrame = minOf(currentFrame, 6)
                SpriteLayer(assetPath = abilityPath, frame = abilityFrame, rowIndex = 2, cellSize = 192, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
