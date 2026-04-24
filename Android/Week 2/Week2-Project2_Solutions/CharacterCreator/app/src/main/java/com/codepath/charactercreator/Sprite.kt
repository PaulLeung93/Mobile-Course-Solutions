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

// ── Low-level sprite renderer ─────────────────────────────────────────────────
// Draws a single frame from a horizontal sprite sheet using pixel-perfect scaling
// (FilterQuality.None preserves the crisp pixel art look — no interpolation)
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
        val frameWidth = imageBitmap.width / anim.frameCount
        val frameHeight = imageBitmap.height
        drawImage(
            image = imageBitmap,
            srcOffset = IntOffset(currentFrame * frameWidth, 0),
            srcSize = IntSize(frameWidth, frameHeight),
            dstOffset = IntOffset.Zero,
            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
            filterQuality = FilterQuality.None
        )
    }
}

// ── High-level character sprite composable ────────────────────────────────────
// Looks up the sprite sheet at runtime using the class name.
// If the drawable doesn't exist yet, it shows an emoji placeholder instead.
// This means the app builds and runs with no assets — just drop in the PNGs
// to activate the real sprites automatically.
//
// Expected drawable filenames (place in res/drawable-nodpi/):
//   warrior_idle_sheet.png    mage_idle_sheet.png
//   rogue_idle_sheet.png      ranger_idle_sheet.png
//   warrior_attack_sheet.png  mage_attack_sheet.png
//   rogue_attack_sheet.png    ranger_attack_sheet.png
@Composable
fun CharacterSprite(
    characterClass: String,
    modifier: Modifier = Modifier,
    isAttacking: Boolean = false,
    onAttackComplete: () -> Unit = {}
) {
    val context = LocalContext.current
    val animType = if (isAttacking) "attack" else "idle"
    val drawableName = "${characterClass.lowercase()}_${animType}_sheet"
    val drawableId = context.resources.getIdentifier(
        drawableName, "drawable", context.packageName
    )

    if (drawableId != 0) {
        val frameCount = if (isAttacking)
            SpriteFrames.attack[characterClass] ?: 8
        else
            SpriteFrames.idle[characterClass] ?: 6

        Sprite(
            anim = CharacterAnim(drawableId, frameCount, !isAttacking),
            modifier = modifier,
            onAnimComplete = onAttackComplete
        )
    } else {
        // Placeholder until sprite sheets are added to res/drawable-nodpi/
        Box(
            modifier = modifier.background(
                color = classColors[characterClass]?.copy(alpha = 0.25f) ?: PurpleAccent.copy(alpha = 0.25f),
                shape = RoundedCornerShape(12.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = classEmojis[characterClass] ?: "⚔️",
                fontSize = 48.sp
            )
        }
    }
}
