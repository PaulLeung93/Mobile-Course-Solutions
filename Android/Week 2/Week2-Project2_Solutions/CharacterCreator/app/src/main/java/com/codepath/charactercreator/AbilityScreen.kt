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

// AbilityScreen is a STRETCH feature — it is fully complete in both the solution
// and the starter. Students do not need to modify this file; it is provided to
// show how the 4-screen chain extends naturally to a 5-screen chain.
@Composable
fun AbilityScreen(
    characterClass: String,
    stat: String,
    weapon: String,
    onAbilitySelected: (String) -> Unit
) {
    var selectedAbility by remember { mutableStateOf("") }
    val accentColor = classColors[characterClass] ?: PurpleAccent
    val abilities = abilitiesByClass[characterClass] ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepHeader(step = 4, total = 4, label = "CHOOSE YOUR ABILITY")

        Spacer(Modifier.height(12.dp))

        // Context — shows all three previous choices
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ContextChip(label = characterClass, emoji = classEmojis[characterClass] ?: "")
            ContextChip(label = stat,           emoji = statEmojis[stat]           ?: "")
            ContextChip(label = weapon,         emoji = weaponEmojis[weapon]       ?: "🗡️")
        }

        Spacer(Modifier.height(24.dp))

        // 2×2 grid of ability cards
        val rows = abilities.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { ability ->
                    SelectionCard(
                        emoji = abilityEmojis[ability] ?: "✨",
                        label = ability,
                        description = "",
                        accentColor = accentColor,
                        isSelected = selectedAbility == ability,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedAbility = ability }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { onAbilitySelected(selectedAbility) },
            enabled = selectedAbility.isNotEmpty(),
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
                text = if (selectedAbility.isEmpty()) "SELECT AN ABILITY" else "FORGE CHARACTER  →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}
