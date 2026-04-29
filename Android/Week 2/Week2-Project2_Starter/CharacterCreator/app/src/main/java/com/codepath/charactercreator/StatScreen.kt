// 📝 TODO — the Confirm button does nothing when tapped. Find the TODO below and fix it using ClassScreen as a reference.
package com.codepath.charactercreator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun StatScreen(
    characterClass: String,
    weapon: String,
    onStatSelected: (String) -> Unit
) {
    var selectedStat by remember { mutableStateOf("") }
    val accentColor = classColors[characterClass] ?: PurpleAccent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StepHeader(step = 4, total = 5, label = "CHOOSE YOUR STAT BOOST")

            Spacer(Modifier.height(12.dp))

            // Context — shows the class and weapon chosen in previous steps
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ContextChip(label = characterClass, emoji = classEmojis[characterClass] ?: "")
                ContextChip(label = weapon,         emoji = weaponEmojis[weapon] ?: "🗡️")
            }

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
        }

        Spacer(Modifier.height(8.dp))

        // TODO: The Confirm button does nothing when tapped.
        // Look at how ClassScreen wires up its Confirm button, then apply the same pattern here.
        Button(
            onClick = { /* TODO */ },
            enabled = selectedStat.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
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
