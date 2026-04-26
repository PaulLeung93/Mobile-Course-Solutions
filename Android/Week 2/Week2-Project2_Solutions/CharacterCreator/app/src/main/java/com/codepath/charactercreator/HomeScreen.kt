package com.codepath.charactercreator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onBeginClicked: () -> Unit) {
    val screenGradient = Brush.verticalGradient(
        listOf(Color(0xFF0D0D1A), DarkBg, Color(0xFF1A1040))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(screenGradient)
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            // Decorative class icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listOf("⚔️", "🔮", "🗡️", "🏹").forEach { emoji ->
                    Text(text = emoji, fontSize = 28.sp)
                }
            }

            Spacer(Modifier.height(28.dp))

            // Title
            Text(
                text = "CHARACTER\nCREATOR",
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                letterSpacing = 4.sp,
                lineHeight = 46.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Forge your legend",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = GoldAccent,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(12.dp))

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, GoldAccent.copy(alpha = 0.5f), Color.Transparent)
                        )
                    )
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Choose your class, forge your stats,\nwield your weapon, master your ability.",
                fontSize = 13.sp,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(48.dp))

            // Begin button
            Button(
                onClick = onBeginClicked,
                modifier = Modifier
                    .fillMaxWidth(0.72f)
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleAccent
                )
            ) {
                Text(
                    text = "BEGIN YOUR JOURNEY",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "5 steps to forge your character",
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 1.sp
            )
        }
    }
}
