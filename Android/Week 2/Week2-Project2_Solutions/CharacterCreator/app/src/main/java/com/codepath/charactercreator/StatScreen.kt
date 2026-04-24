package com.codepath.charactercreator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── STARTER CODE NOTES ────────────────────────────────────────────────────────
// The UI in this screen is fully built — the selection cards and confirm button
// are complete and functional.
//
// STARTER TODO: The confirm button's onClick is stubbed out. Students must fill
// in the navController.navigate() call that passes characterClass and selectedStat
// to the weapon screen. The comment marks exactly where the call goes.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun StatScreen(
    characterClass: String,
    onStatSelected: (String) -> Unit
) {
    var selectedStat by remember { mutableStateOf("") }
    val accentColor = classColors[characterClass] ?: PurpleAccent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepHeader(step = 2, total = 4, label = "CHOOSE YOUR STAT BOOST")

        Spacer(Modifier.height(12.dp))

        // Context — shows the class chosen in the previous step
        ContextChip(
            label = characterClass,
            emoji = classEmojis[characterClass] ?: ""
        )

        Spacer(Modifier.height(24.dp))

        // 2×2 grid of stat selection cards
        val rows = stats.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { stat ->
                    SelectionCard(
                        emoji = statEmojis[stat] ?: "",
                        label = stat,
                        description = statDescriptions[stat] ?: "",
                        accentColor = accentColor,
                        isSelected = selectedStat == stat,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedStat = stat }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.weight(1f))

        // STARTER TODO: fill in onStatSelected(selectedStat) inside the onClick below.
        // onStatSelected is the callback from MainActivity that navigates to
        // "weapon/$characterClass/$selectedStat" — students must call it here.
        Button(
            onClick = { onStatSelected(selectedStat) },
            enabled = selectedStat.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                disabledContainerColor = accentColor.copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = if (selectedStat.isEmpty()) "SELECT A STAT" else "CONFIRM  →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}
