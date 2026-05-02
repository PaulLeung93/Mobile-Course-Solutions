package com.codepath.trivialive.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val ColorScheme = darkColorScheme(
    primary          = GamePrimary,
    onPrimary        = GameOnPrimary,
    secondary        = GameSecondary,
    background       = GameBackground,
    surface          = GameSurface,
    onBackground     = GameOnSurface,
    onSurface        = GameOnSurface,
    onSurfaceVariant = GameMuted,
    error            = AnswerWrong,
    outline          = Color(0xFF3D3B52)
)

private val AppTypography = Typography(
    displayLarge  = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 48.sp, letterSpacing = (-1).sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
    titleLarge    = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
    titleMedium   = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    bodyMedium    = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge    = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp, letterSpacing = 0.5.sp)
)

@Composable
fun TriviaLiveTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography  = AppTypography,
        content     = content
    )
}
