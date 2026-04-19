package com.codepath.monsterslayer

import androidx.compose.animation.animateColorAsState
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
import kotlin.math.abs

// ── Colors ──
private val DarkBg = Color(0xFF1A1A2E)
private val Purple = Color(0xFF534AB7)
private val PurpleLight = Color(0xFF7F52FF)
private val HpGreen = Color(0xFF4CAF50)
private val HpYellow = Color(0xFFFFC107)
private val HpRed = Color(0xFFF44336)

// ── Monster phase data ──
data class MonsterPhase(val emoji: String, val taunt: String)

fun getMonsterPhase(hp: Int): MonsterPhase = when {
    hp > 14 -> MonsterPhase("🐉", "I will crush you, tiny human!")
    hp > 9  -> MonsterPhase("😠", "You dare wound me?!")
    hp > 4  -> MonsterPhase("😤", "N-not so fast...")
    hp > 0  -> MonsterPhase("😰", "Please... have mercy...")
    else    -> MonsterPhase("💀", "You... you beat me...")
}

// ── Maximum HP constant ──
private const val MAX_HP = 20

// ── Swipe combo ──
private val COMBO = listOf("up", "up", "down")

@Composable
fun MonsterSlayerScreen() {
    // ── State ──
    var monsterHp by remember { mutableIntStateOf(MAX_HP) }
    var attackCount by remember { mutableIntStateOf(0) }
    var comboProgress by remember { mutableStateOf(listOf<String>()) }

    // ── Derived values ──
    val phase = getMonsterPhase(monsterHp)
    val hpFraction = monsterHp.toFloat() / MAX_HP

    // ── Animated HP bar color ──
    val hpBarColor by animateColorAsState(
        targetValue = when {
            hpFraction > 0.5f -> HpGreen
            hpFraction > 0.25f -> HpYellow
            else -> HpRed
        },
        animationSpec = tween(400),
        label = "hpColor"
    )

    // ── Animated HP bar progress ──
    val animatedHpFraction by animateFloatAsState(
        targetValue = hpFraction,
        animationSpec = tween(300),
        label = "hpProgress"
    )

    // ── Swipe detection helper ──
    fun handleSwipe(direction: String) {
        val next = comboProgress + direction
        comboProgress = if (next.size > COMBO.size) {
            next.takeLast(COMBO.size)
        } else {
            next
        }
        if (comboProgress == COMBO && monsterHp > 0) {
            monsterHp = 0
            comboProgress = listOf()
        }
    }

    // ── UI ──
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    val (dx, dy) = dragAmount
                    if (abs(dx) > abs(dy)) {
                        // Horizontal swipe
                        if (dx > 30) handleSwipe("right")
                        else if (dx < -30) handleSwipe("left")
                    } else {
                        // Vertical swipe
                        if (dy > 30) handleSwipe("down")
                        else if (dy < -30) handleSwipe("up")
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── Monster emoji ──
            Text(
                text = phase.emoji,
                fontSize = 80.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ── Taunt ──
            Text(
                text = "\"${phase.taunt}\"",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── HP bar ──
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Attack count ──
            Text(
                text = "Attacks landed: $attackCount",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ── Attack button ──
            Button(
                onClick = {
                    if (monsterHp > 0) {
                        monsterHp -= 1
                        attackCount += 1
                    }
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

            // ── Reset button (only when defeated) ──
            if (monsterHp == 0) {
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(
                    onClick = {
                        monsterHp = MAX_HP
                        attackCount = 0
                        comboProgress = listOf()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PurpleLight
                    )
                ) {
                    Text(
                        text = "🐉 New Monster",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // ── Combo hint ──
            Spacer(modifier = Modifier.height(24.dp))
            if (monsterHp > 0) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val labels = listOf("↑", "↑", "↓")
                    labels.forEachIndexed { index, arrow ->
                        val filled = index < comboProgress.size &&
                                comboProgress[index] == COMBO[index]
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
        }
    }
}
