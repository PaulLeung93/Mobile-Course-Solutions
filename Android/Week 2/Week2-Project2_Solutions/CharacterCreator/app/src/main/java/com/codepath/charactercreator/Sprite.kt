package com.codepath.charactercreator

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ── SpriteLayer ───────────────────────────────────────────────────────────────
// Stateless: renders one frame from a standard LPC sprite sheet.
// The caller owns the frame counter so multiple layers share one animation tick.
@Composable
fun SpriteLayer(
    drawableId: Int,
    frame: Int,
    rowIndex: Int,
    modifier: Modifier = Modifier
) {
    val imageBitmap = ImageBitmap.imageResource(id = drawableId)
    Canvas(modifier = modifier) {
        val frameW = 64 // LPC standard: 64px wide, 64px tall per cell
        val frameH = frameW
        val srcY   = rowIndex * frameH

        // Safety: skip if row or column is out of bounds (e.g. 12-row weapon sheets at SLASH_ROW)
        if (srcY + frameH > imageBitmap.height || frame * frameW >= imageBitmap.width) return@Canvas

        // All layers share a 64×128 logical space so the scale stays at 1.25 for a 160dp canvas
        // (rather than 2.5 if we used a 64×64 space), keeping characters at a comfortable size.
        // The 64px frame occupies the bottom half of that logical space (y=64..128), which
        // centers the character head ~25% from the top of the canvas.
        val logicalW = 64f
        val logicalH = 128f
        val canvasW  = size.width
        val canvasH  = size.height
        val scale    = minOf(canvasW / logicalW, canvasH / logicalH)

        val dstW = (frameW * scale).toInt()
        val dstH = (frameH * scale).toInt()
        val dstX = ((canvasW - logicalW * scale) / 2f).toInt()
        // Anchor logical y=96 (center of the bottom 64px band) at canvasH/2:
        val dstY = ((canvasH / 2f) - (96f * scale) + (logicalH - frameH) * scale).toInt()

        drawImage(
            image         = imageBitmap,
            srcOffset     = IntOffset(frame * frameW, srcY),
            srcSize       = IntSize(frameW, frameH),
            dstOffset     = IntOffset(dstX, dstY),
            dstSize       = IntSize(dstW, dstH),
            filterQuality = FilterQuality.None
        )
    }
}

// ── Sprite ────────────────────────────────────────────────────────────────────
// Animated single-layer renderer for single-row (non-LPC) sprite sheets.
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

    val imageBitmap = ImageBitmap.imageResource(id = anim.drawableId)
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
// Composites body + class armor + weapon layers from LPC sprite sheets.
// All layers share one frame counter so they animate in perfect sync.
// Falls back to an emoji placeholder if the body sheet is not yet in res/drawable-nodpi/.
//
// Expected drawables in res/drawable-nodpi/:
//   body_sheet.png
//   {class}_armor_sheet.png          e.g. warrior_armor_sheet.png
//   {weapon_name}_sheet.png          e.g. longsword_sheet.png, battle_axe_sheet.png
@Composable
fun CharacterSprite(
    characterClass: String,
    weapon: String,
    modifier: Modifier = Modifier,
    isAttacking: Boolean = false,
    onAttackComplete: () -> Unit = {}
) {
    val context = LocalContext.current

    fun drawableId(name: String) =
        context.resources.getIdentifier(name, "drawable", context.packageName)

    val cls      = characterClass.lowercase()
    val bodyId   = drawableId("body_sheet")
    val headId   = drawableId("head_sheet")
    val pantsId  = drawableId("${cls}_pants_sheet")
    val feetId   = drawableId("${cls}_feet_sheet")
    val armsId   = drawableId("${cls}_arms_sheet")
    val armorId  = drawableId("${cls}_armor_sheet")
    val weaponId = drawableId(
        weapon.lowercase().replace(" ", "_").replace("'", "") + "_sheet"
    )

    if (bodyId == 0) {
        Box(
            modifier = modifier.background(
                color = classColors[characterClass]?.copy(alpha = 0.25f) ?: PurpleAccent.copy(alpha = 0.25f),
                shape = RoundedCornerShape(12.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = classEmojis[characterClass] ?: "⚔️", fontSize = 48.sp)
        }
        return
    }

    val row        = if (isAttacking) SpriteFrames.SLASH_ROW    else SpriteFrames.WALK_ROW
    val frameCount = if (isAttacking) SpriteFrames.SLASH_FRAMES else SpriteFrames.WALK_FRAMES

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
        SpriteLayer(drawableId = bodyId,   frame = currentFrame, rowIndex = row, modifier = Modifier.fillMaxSize())
        if (pantsId  != 0) SpriteLayer(drawableId = pantsId,  frame = currentFrame, rowIndex = row, modifier = Modifier.fillMaxSize())
        if (feetId   != 0) SpriteLayer(drawableId = feetId,   frame = currentFrame, rowIndex = row, modifier = Modifier.fillMaxSize())
        if (armsId   != 0) SpriteLayer(drawableId = armsId,   frame = currentFrame, rowIndex = row, modifier = Modifier.fillMaxSize())
        if (headId   != 0) SpriteLayer(drawableId = headId,   frame = currentFrame, rowIndex = row, modifier = Modifier.fillMaxSize())
        if (armorId  != 0) SpriteLayer(drawableId = armorId,  frame = currentFrame, rowIndex = row, modifier = Modifier.fillMaxSize())
        if (weaponId != 0) SpriteLayer(drawableId = weaponId, frame = currentFrame, rowIndex = row, modifier = Modifier.fillMaxSize())
    }
}
