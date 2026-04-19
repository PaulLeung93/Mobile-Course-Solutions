package com.codepath.monsterslayer

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.Canvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import kotlin.math.abs

// ── Colors ──
private val DarkBg = Color(0xFF1A1A2E)
private val Purple = Color(0xFF534AB7)
private val PurpleLight = Color(0xFF7F52FF)
private val HpGreen = Color(0xFF4CAF50)
private val HpYellow = Color(0xFFFFC107)
private val HpRed = Color(0xFFF44336)

// ── Animation data ──
data class SpriteAnim(val drawableId: Int, val frameCount: Int, val isLooping: Boolean)

val AnimMonsterIdle = SpriteAnim(R.drawable.monster_idle_sheet, 6, true)
val AnimMonsterHurt = SpriteAnim(R.drawable.monster_hurt_sheet, 4, false)
val AnimMonsterDeath = SpriteAnim(R.drawable.monster_death_sheet, 10, false)

val AnimHeroIdle = SpriteAnim(R.drawable.hero_idle_sheet, 6, true)
val AnimHeroAttack = SpriteAnim(R.drawable.hero_attack_sheet, 8, false)

// ── Monster taunt logic ──
fun getMonsterTaunt(hp: Int): String {
    // TODO: Step 3a - Update getMonsterTaunt to return different strings based on HP using a when statement
    return "I will crush you, tiny human!"
}

// ── Maximum HP constant ──
private const val MAX_HP = 20

// ── Swipe combo ──
fun generateRandomCombo(length: Int = 5): List<String> {
    val ops = listOf("up", "down", "left", "right")
    return List(length) { ops.random() }
}

@Composable
fun MonsterSlayerScreen() {
    // ── State ──
    var monsterHp = MAX_HP
    var attackCount = 0
    var comboProgress = listOf<String>()
    
    // TODO: Step 2 - Lifting State - Replace the variables above with remember { mutableStateOf(...) }
    // (Hint: use mutableIntStateOf for numbers and mutableStateOf for lists)

    var secretCombo by remember { mutableStateOf(generateRandomCombo(5)) }
    var isHeroAttacking by remember { mutableStateOf(false) }
    var isMonsterHurt by remember { mutableStateOf(false) }

    // ── Derived values ──
    val taunt = getMonsterTaunt(monsterHp)
    val heroAnim = if (isHeroAttacking) AnimHeroAttack else AnimHeroIdle
    val monsterAnim = when {
        monsterHp == 0 -> AnimMonsterDeath
        isMonsterHurt -> AnimMonsterHurt
        else -> AnimMonsterIdle
    }
    val hpFraction = monsterHp.toFloat() / MAX_HP

    // ── HP bar color ──
    // TODO: Step 6 - Dynamic Styling - Calculate the HP bar color based on monsterHp 
    // (Hint: Green > 10, Yellow > 5, Red <= 5)
    val hpBarColor = HpGreen

    // ── Animated HP bar progress ──
    val animatedHpFraction by animateFloatAsState(
        targetValue = hpFraction,
        animationSpec = tween(300),
        label = "hpProgress"
    )

    // ── Hero Attack Offset ──
    val heroOffsetX by animateDpAsState(
        targetValue = if (isHeroAttacking) 90.dp else 0.dp,
        animationSpec = tween(150),
        label = "heroOffsetX"
    )

    // ── Swipe detection helper ──
    fun handleSwipe(direction: String) {
        if (monsterHp <= 0) return

        // TODO: Stretch goal - Check if direction matches the next swipe in secretCombo
        // If it does, add to comboProgress list
        // If comboProgress size equals secretCombo size, instantly defeat the monster!
    }

    // ── UI ──
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .pointerInput(Unit) {
                var totalDrag = androidx.compose.ui.geometry.Offset.Zero
                detectDragGestures(
                    onDragStart = { totalDrag = androidx.compose.ui.geometry.Offset.Zero },
                    onDragEnd = {
                        val (dx, dy) = totalDrag
                        val threshold = 100f
                        if (kotlin.math.abs(dx) > kotlin.math.abs(dy)) {
                            if (dx > threshold) handleSwipe("right")
                            else if (dx < -threshold) handleSwipe("left")
                        } else {
                            if (dy > threshold) handleSwipe("down")
                            else if (dy < -threshold) handleSwipe( "up")
                        }
                    },
                    onDrag = { _, dragAmount ->
                        totalDrag += dragAmount
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── Top Bar (HP and Taunt) ──
            Spacer(modifier = Modifier.height(100.dp))
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "HP: $monsterHp / $MAX_HP",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { animatedHpFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = hpBarColor,
                    trackColor = Color.White.copy(alpha = 0.15f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "\"$taunt\"",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }

            // ── Middle Section (Battle Scene) ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ── Hero Image (Left) ──
                Sprite(
                    anim = heroAnim,
                    modifier = Modifier
                        .offset(x = heroOffsetX)
                        .size(160.dp)
                        .scale(3.2f),
                    onAnimComplete = {
                        if (isHeroAttacking) isHeroAttacking = false
                    }
                )

                // ── Monster Image (Right) ──
                Sprite(
                    anim = monsterAnim,
                    modifier = Modifier.size(160.dp).scale(scaleX = -3.2f, scaleY = 3.2f),
                    onAnimComplete = {
                        if (isMonsterHurt) isMonsterHurt = false
                    }
                )
            }

            // ── Bottom Section (Controls) ──
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ── Attack count ──
                // TODO: Step 4 - Display the attackCount variable here
                Text(
                    text = "Attacks landed: $attackCount",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ── Attack button ──
                // TODO: Step 5 - Conditional UI - If monsterHp is 0, hide this button and show a "Battle Again" 
                // button that resets HP to MAX_HP and attackCount to 0!
                Button(
                    onClick = {
                        // TODO: Step 1 - Wire up the attack button (logic: subtract 1 from monsterHp)
                        
                        // TODO: Step 3b - Trigger the Hero attack and Monster hurt animations
                        // (Hint: set isHeroAttacking and isMonsterHurt to true)
                    },
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    enabled = monsterHp > 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                    )
                ) {
                    Text(
                        text = if (monsterHp > 0) "⚔️" else "💀",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ── Attack label ──
                Text(
                    text = if (monsterHp > 0) "TAP TO ATTACK" else "VICTORY!",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (monsterHp > 0) PurpleLight else Color(0xFF4CAF50),
                    letterSpacing = 2.sp
                )

                // ── Combo hint ──
                if (monsterHp > 0) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val labels = secretCombo.map { 
                            when (it) {
                                "up" -> "↑"
                                "down" -> "↓"
                                "left" -> "←"
                                "right" -> "→"
                                else -> "?"
                            }
                        }
                        labels.forEachIndexed { index, arrow ->
                            val filled = index < comboProgress.size
                            Text(
                                text = arrow,
                                fontSize = 16.sp,
                                color = if (filled) PurpleLight else Color.White.copy(alpha = 0.2f),
                                modifier = Modifier
                                    .background(
                                        color = if (filled) PurpleLight.copy(alpha = 0.15f)
                                        else Color.White.copy(alpha = 0.05f),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "secret combo",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.2f),
                        letterSpacing = 1.sp
                    )
                }

                // TODO: Step 8 - Make One Change: Add your own creative modification
                // (e.g., critical hits, battles won, player HP, or a randomized back face)
            }
        }
    }
}

// TODO: Step 9 - Reflection: Answer the curriculum questions in your own words here:
// 1. In your own words: what is state, and why does declarative UI need it?
// 2. What is one thing the monster phase system and a milestone counter have in common?
// 3. What surprised you most about how the UI reacted to state changes?

@Composable
fun Sprite(
    anim: SpriteAnim,
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

