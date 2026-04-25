package com.codepath.charactercreator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── STARTER CODE NOTES ────────────────────────────────────────────────────────
// The layout and styling of this screen are fully built. The four text fields
// currently display placeholder strings ("???").
//
// STARTER TODO 1: Replace the placeholder for classDisplay with characterClass
// STARTER TODO 2: Replace the placeholder for statDisplay  with stat
// STARTER TODO 3: Replace the placeholder for weaponDisplay with weapon
// STARTER TODO 4: Replace the placeholder for abilityDisplay with ability
// STARTER TODO 5: Replace the placeholder description with buildDescription(...)
//
// All five values arrive as parameters at the top of this function — students
// just need to wire them into the display variables below.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun CharacterCardScreen(
    characterClass: String,
    stat: String,
    weapon: String,
    ability: String,
    onStartOver: () -> Unit
) {
    // SOLUTION: use the navigation arguments passed into this screen
    // STARTER: replace each right-hand side with "???" so students must wire these up
    val classDisplay   = characterClass
    val statDisplay    = stat
    val weaponDisplay  = weapon
    val abilityDisplay = ability
    val description    = buildDescription(characterClass, stat, weapon, ability)

    val accentColor = classColors[characterClass] ?: PurpleAccent
    var isAttacking by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "YOUR CHARACTER",
                fontSize = 11.sp,
                color = GoldAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(Modifier.height(20.dp))

            // Character sprite (animated idle, tapping triggers attack)
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, accentColor.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                    .background(accentColor.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                CharacterSprite(
                    characterClass = characterClass,
                    weapon = weapon,
                    isAttacking = isAttacking,
                    onAttackComplete = { isAttacking = false },
                    modifier = Modifier.size(160.dp)
                )
            }

            Spacer(Modifier.height(4.dp))
            Text(
                text = "Tap to attack",
                fontSize = 10.sp,
                color = TextMuted,
                fontStyle = FontStyle.Italic
            )

            Spacer(Modifier.height(16.dp))

            // Class name
            Text(
                text = "${classEmojis[characterClass] ?: ""}  $classDisplay",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = accentColor,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(16.dp))

            // Stat / Weapon / Ability detail card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(CardBg)
                    .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CharacterStatRow(
                    emoji = statEmojis[statDisplay]     ?: "💪",
                    label = "Stat Boost",
                    value = statDisplay
                )
                CharacterStatRow(
                    emoji = weaponEmojis[weaponDisplay] ?: "🗡️",
                    label = "Weapon",
                    value = weaponDisplay
                )
                CharacterStatRow(
                    emoji = abilityEmojis[abilityDisplay] ?: "✨",
                    label = "Ability",
                    value = abilityDisplay
                )
            }

            Spacer(Modifier.height(16.dp))

            // Generated description
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(accentColor.copy(alpha = 0.12f), CardBgDim)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "\"$description\"",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    color = TextPrimary.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.weight(1f))

            // Action buttons
            Button(
                onClick = { isAttacking = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text(
                    text = "⚔️  ATTACK",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick = onStartOver,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    contentColor = TextMuted
                )
            ) {
                Text(
                    text = "🔄  START OVER",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

@Composable
private fun CharacterStatRow(emoji: String, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                text = label.uppercase(),
                fontSize = 9.sp,
                color = TextMuted,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}
