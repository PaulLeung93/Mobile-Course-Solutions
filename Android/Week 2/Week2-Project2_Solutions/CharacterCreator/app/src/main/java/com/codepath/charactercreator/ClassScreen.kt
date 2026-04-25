package com.codepath.charactercreator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ClassScreen is COMPLETE — no TODOs or bugs are introduced here.
// Students should read this file carefully: it shows the full pattern
// (selection state, card grid, confirm button) that StatScreen and
// WeaponScreen follow.
@Composable
fun ClassScreen(onClassSelected: (String) -> Unit) {
    var selectedClass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepHeader(step = 1, total = 4, label = "CHOOSE YOUR CLASS")

        Spacer(Modifier.height(24.dp))

        // 2×2 grid of class selection cards
        val rows = classes.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { characterClass ->
                    SelectionCard(
                        emoji = classEmojis[characterClass] ?: "",
                        label = characterClass,
                        description = classDescriptions[characterClass] ?: "",
                        accentColor = classColors[characterClass] ?: PurpleAccent,
                        isSelected = selectedClass == characterClass,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedClass = characterClass }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { onClassSelected(selectedClass) },
            enabled = selectedClass.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PurpleAccent,
                disabledContainerColor = PurpleAccent.copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = if (selectedClass.isEmpty()) "SELECT A CLASS" else "CONFIRM  →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}

// ── Shared UI helpers ─────────────────────────────────────────────────────────

// Progress header shown at the top of every selection screen
@Composable
fun StepHeader(step: Int, total: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "STEP $step OF $total",
            fontSize = 11.sp,
            color = GoldAccent,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )
    }
}

// Individual selection card — highlights with the class accent color when selected
@Composable
fun SelectionCard(
    emoji: String,
    label: String,
    description: String,
    accentColor: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    iconRes: Int? = null,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) accentColor else Color.White.copy(alpha = 0.1f)
    val bgColor     = if (isSelected) accentColor.copy(alpha = 0.15f) else CardBg

    Box(
        modifier = modifier
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            if (iconRes != null) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(4.dp))
                )
            } else {
                Text(text = emoji, fontSize = 32.sp)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) accentColor else TextPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 10.sp,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

// Small pill showing a previously chosen selection — used as context on later screens
@Composable
fun ContextChip(label: String, emoji: String) {
    Row(
        modifier = Modifier
            .background(CardBgDim, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = emoji, fontSize = 12.sp)
        Text(text = label, fontSize = 11.sp, color = TextMuted, fontWeight = FontWeight.Medium)
    }
}
