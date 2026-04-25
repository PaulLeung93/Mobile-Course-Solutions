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
    modifier: Modifier = Modifier,
    isAttacking: Boolean = false,
    isBodyHidden: Boolean = false,
    onAttackComplete: () -> Unit = {}
) {
    val context = LocalContext.current

    val cls       = characterClass.lowercase()
    val bodyPath  = "sprites/shared/body_sheet.png"
    val headPath  = "sprites/shared/head_sheet.png"
    val pantsPath = "sprites/$cls/pants_sheet.png"
    val feetPath  = "sprites/$cls/feet_sheet.png"
    val armsPath  = "sprites/$cls/arms_sheet.png"
    val armorPath = "sprites/$cls/armor_sheet.png"

    // Resolve weapon paths and attack-animation parameters from WeaponConfig
    val config          = weaponConfig(weapon)
    val isCasting       = isAttacking && ability != null && characterClass != "Mage"
    val isWeaponAttack  = isAttacking && !isCasting

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

    LaunchedEffect(isAttacking) {
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
        // Behind-weapon layer renders first (beneath body)
        if (weaponBehindPath != null && assetExists(context, weaponBehindPath))
            SpriteLayer(assetPath = weaponBehindPath, frame = currentFrame, rowIndex = weaponRow, cellSize = weaponCellSize, modifier = Modifier.fillMaxSize())

        val currentBodyFrame = minOf(currentFrame, bodyFrames - 1)

        if (!isBodyHidden) {
            SpriteLayer(assetPath = bodyPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
            if (assetExists(context, pantsPath))  SpriteLayer(assetPath = pantsPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
            if (assetExists(context, feetPath))   SpriteLayer(assetPath = feetPath,   frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
            if (assetExists(context, armsPath))   SpriteLayer(assetPath = armsPath,   frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
            if (assetExists(context, headPath))   SpriteLayer(assetPath = headPath,   frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
            if (assetExists(context, armorPath))  SpriteLayer(assetPath = armorPath,  frame = currentBodyFrame, rowIndex = bodyRow, modifier = Modifier.fillMaxSize())
        }

        // Front weapon layer renders last (on top)
        if (assetExists(context, weaponFrontPath)) SpriteLayer(assetPath = weaponFrontPath, frame = currentFrame, rowIndex = weaponRow, cellSize = weaponCellSize, modifier = Modifier.fillMaxSize())

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
