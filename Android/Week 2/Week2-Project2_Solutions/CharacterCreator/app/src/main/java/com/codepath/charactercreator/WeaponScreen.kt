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

// ── STARTER CODE NOTES ────────────────────────────────────────────────────────
// Two bugs are intentionally planted in the starter version of this file.
// The solution code below is correct. Comments mark exactly where each bug lives.
//
// STARTER BUG 1 — hardcoded weapon list (line marked below):
//   Replace the correct weaponsByClass[characterClass] lookup with a hardcoded
//   listOf("Longsword", "Battle Axe", "Light Saber", "Halberd") so the
//   weapon list always shows Warrior weapons regardless of the chosen class.
//
// STARTER BUG 2 — wrong argument order (in MainActivity.kt NavHost):
//   In the onWeaponSelected lambda inside MainActivity, change:
//     navController.navigate("ability/$characterClass/$stat/$weapon")
//   to:
//     navController.navigate("ability/$characterClass/$weapon/$stat")
//   so stat and weapon arrive at AbilityScreen / CharacterCard in swapped slots.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun WeaponScreen(
    characterClass: String,
    onWeaponSelected: (String) -> Unit
) {
    var selectedWeapon by remember { mutableStateOf("") }
    val accentColor = classColors[characterClass] ?: PurpleAccent

    // SOLUTION: look up weapons for the chosen class
    // STARTER BUG 1: replace this line with a hardcoded Warrior weapon list
    val weapons = weaponsByClass[characterClass] ?: emptyList()

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
            StepHeader(step = 3, total = 5, label = "CHOOSE YOUR WEAPON")

            Spacer(Modifier.height(12.dp))

            // Context chips — shows class chosen in previous steps
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ContextChip(label = characterClass, emoji = classEmojis[characterClass] ?: "")
            }

            Spacer(Modifier.height(24.dp))

            // 2×2 grid of weapon cards
            val rows = weapons.chunked(2)
            rows.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { weapon ->
                        val iconRes = if (weapon == "Light Saber") R.drawable.lightsaber_icon else null

                        SelectionCard(
                            emoji = weaponEmojis[weapon] ?: "🗡️",
                            label = weapon,
                            description = "",
                            accentColor = accentColor,
                            isSelected = selectedWeapon == weapon,
                            modifier = Modifier.weight(1f),
                            iconRes = iconRes,
                            onClick = { selectedWeapon = weapon }
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { onWeaponSelected(selectedWeapon) },
            enabled = selectedWeapon.isNotEmpty(),
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
                text = if (selectedWeapon.isEmpty()) "SELECT A WEAPON" else "CONFIRM  →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}
