package com.codepath.profilecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlin.math.abs
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ProfileCard() }
    }
}

// ── Color palette ──
private val Purple       = Color(0xFF534AB7)
private val PurpleLight  = Color(0xFF7F52FF)
private val PurpleDark   = Color(0xFF3C3489)
private val CardGradient = Brush.linearGradient(listOf(PurpleDark, PurpleLight))
private val ScreenGradient = Brush.verticalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF2D2B55)))

// Replace with your actual LinkedIn profile URL
private const val LINKEDIN_URL = "https://linkedin.com/in/YOUR-USERNAME"

// Holographic rainbow stops — fade in/out at edges so the effect blends cleanly
private val HoloColors = listOf(
    Color(0x00FF6B9D),
    Color(0xAAFF6B9D), // pink
    Color(0xAAC44DFF), // purple
    Color(0xAA4DAFFF), // blue
    Color(0xAA00E5CC), // cyan
    Color(0xAA66FF66), // green
    Color(0xAAFFDD00), // yellow
    Color(0xAAFF6B9D), // back to pink
    Color(0x00FF6B9D),
)

@Composable
fun ProfileCard() {
    Box(
        modifier = Modifier.fillMaxSize().background(ScreenGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BusinessCard()
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Drag to tilt  •  Tap to flip",
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
fun BusinessCard() {
    var flipped by remember { mutableStateOf(false) }
    var normalizedX by remember { mutableStateOf(0f) }
    var normalizedY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    // Spring back to flat when finger is lifted
    val animX by animateFloatAsState(
        targetValue = if (isDragging) normalizedX else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "animX"
    )
    val animY by animateFloatAsState(
        targetValue = if (isDragging) normalizedY else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "animY"
    )

    val flipAnim by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "flip"
    )

    val isShowingBack = flipAnim > 90f
    val totalRotY = animX * 15f + flipAnim

    Box(
        modifier = Modifier
            .fillMaxWidth(0.88f)
            // Single pointer handler: short press = flip, drag = tilt
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()
                    var didDrag = false

                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull() ?: break
                        if (!change.pressed) {
                            if (!didDrag) flipped = !flipped
                            isDragging = false
                            break
                        }
                        val pos = change.position
                        val dx = pos.x - down.position.x
                        val dy = pos.y - down.position.y
                        if (!didDrag && sqrt(dx * dx + dy * dy) > 16f) didDrag = true
                        if (didDrag) {
                            isDragging = true
                            val w = size.width.toFloat()
                            val h = size.height.toFloat()
                            normalizedX = ((pos.x - w / 2f) / (w / 2f)).coerceIn(-1f, 1f)
                            normalizedY = ((pos.y - h / 2f) / (h / 2f)).coerceIn(-1f, 1f)
                            change.consume()
                        }
                    }
                }
            }
            .graphicsLayer {
                rotationX = -animY * 15f
                rotationY = totalRotY
                cameraDistance = 14f * density
            }
    ) {
        // Front face — always composed so the Box locks to its size
        Box(Modifier.graphicsLayer { alpha = if (!isShowingBack) 1f else 0f }) {
            FrontFace()
        }

        // Back face — fills the same footprint via matchParentSize
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer { rotationY = 180f; alpha = if (isShowingBack) 1f else 0f }
        ) {
            BackFace()
        }

        HolographicOverlay(normalizedX = animX, normalizedY = animY, modifier = Modifier.matchParentSize())
    }
}

// Rainbow gradient + specular highlight that intensify with tilt angle
@Composable
fun HolographicOverlay(normalizedX: Float, normalizedY: Float, modifier: Modifier = Modifier) {
    val intensity = (abs(normalizedX) + abs(normalizedY)).coerceIn(0f, 1f)

    BoxWithConstraints(
        modifier = modifier.clip(RoundedCornerShape(20.dp))
    ) {
        val w = constraints.maxWidth.toFloat()
        val h = constraints.maxHeight.toFloat()

        if (intensity > 0.01f) {
            // Holographic rainbow that sweeps across the card as you tilt
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = intensity * 0.6f }
                    .background(
                        Brush.linearGradient(
                            colors = HoloColors,
                            start = Offset(normalizedX * w * 0.5f, normalizedY * h * 0.3f),
                            end = Offset(w + normalizedX * w * 0.5f, h + normalizedY * h * 0.3f)
                        )
                    )
            )

            // Specular white glint that tracks the light source
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = intensity * 0.4f }
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color.White, Color.Transparent),
                            center = Offset(
                                w * (0.5f + normalizedX * 0.4f),
                                h * (0.25f + normalizedY * 0.15f)
                            ),
                            radius = w * 0.65f
                        )
                    )
            )
        }
    }
}

@Composable
fun FrontFace() {
    var showSkills by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 24.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
    ) {
        // ── Gradient header ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardGradient)
                .padding(top = 48.dp, bottom = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Profile photo with white ring border
                // Add your photo to res/drawable/ and replace R.drawable.profile_photo
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .border(3.dp, Color.White.copy(alpha = 0.7f), CircleShape)
                        .padding(3.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_photo),
                        contentDescription = "Profile photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(Modifier.height(14.dp))
                Text("John Doe", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Mobile Dev Cohort 2025", fontSize = 13.sp, color = Color.White.copy(alpha = 0.75f))
            }
        }

        // ── White body ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CS student passionate about building intuitive mobile experiences. " +
                    "Started coding in high school and haven't looked back since. " +
                    "This course is my first deep dive into Android and iOS.",
                fontSize = 13.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(Modifier.height(20.dp))

            ContactRow()

            Spacer(Modifier.height(16.dp))

            // Collapsible skills
            OutlinedButton(
                onClick = { showSkills = !showSkills },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Purple)
            ) {
                Text(if (showSkills) "Hide Skills ▲" else "Show Skills ▼")
            }

            if (showSkills) {
                Spacer(Modifier.height(10.dp))
                SkillChips(listOf("Kotlin", "Jetpack Compose", "SwiftUI", "Git", "Python"))
            }
        }
    }
}

@Composable
fun BackFace() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .shadow(elevation = 24.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(CardGradient)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Scan to Connect", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text("on LinkedIn", fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f))
        Spacer(Modifier.height(20.dp))

        // QR code on a white card — generated locally, no internet needed
        Box(modifier = Modifier.background(Color.White, RoundedCornerShape(16.dp)).padding(16.dp)) {
            Image(
                painter = rememberQrCodePainter(LINKEDIN_URL),
                contentDescription = "LinkedIn QR code",
                modifier = Modifier.size(180.dp)
            )
        }

        Spacer(Modifier.height(16.dp))
        Text(LINKEDIN_URL, fontSize = 10.sp, color = Color.White.copy(alpha = 0.5f), textAlign = TextAlign.Center)
    }
}

@Composable
fun ContactRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0EFF8), RoundedCornerShape(12.dp))
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactItem(icon = Icons.Default.Code, label = "@johndoe")
        Box(Modifier.width(1.dp).height(36.dp).background(Color(0xFFDDDAF0)))
        ContactItem(icon = Icons.Default.Email, label = "john@email.com")
        Box(Modifier.width(1.dp).height(36.dp).background(Color(0xFFDDDAF0)))
        ContactItem(icon = Icons.Default.LocationOn, label = "New York")
    }
}

@Composable
fun ContactItem(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null, tint = Purple, modifier = Modifier.size(18.dp))
        Spacer(Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, color = Color(0xFF555555), textAlign = TextAlign.Center)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillChips(skills: List<String>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F7FF), RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        skills.forEach { skill ->
            Box(
                modifier = Modifier
                    .background(Purple.copy(alpha = 0.08f), RoundedCornerShape(50.dp))
                    .border(1.dp, Purple.copy(alpha = 0.25f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(text = skill, fontSize = 12.sp, color = Purple, fontWeight = FontWeight.Medium)
            }
        }
    }
}
