// 📝 TODO — add a character name input to this screen (see the TODO in the scrollable column below).
package com.codepath.charactercreator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FaceSelection(
    val name: String,
    val skinTone: String,
    val eyeColor: String,
    val hairStyle: String,
    val hairColor: String,
    val earType: String,
)

@Composable
fun FaceScreen(onFaceSelected: (FaceSelection) -> Unit) {
    var selectedSkin      by remember { mutableStateOf(skinTones[0].key) }
    var selectedEye       by remember { mutableStateOf(eyeOptions[0].key) }
    var selectedHairStyle by remember { mutableStateOf(hairStyles[0].key) }
    var selectedHairColor by remember { mutableStateOf(hairColors[0].key) }
    var selectedEar       by remember { mutableStateOf(earOptions[0].key) }
    var name              by remember { mutableStateOf("") }

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
            StepHeader(step = 1, total = 5, label = "DESIGN YOUR CHARACTER")

            Spacer(Modifier.height(24.dp))

            // TODO: Add an OutlinedTextField here so the player can type their character's name.
            // The `name` state variable above is already wired to the navigation — just add the UI.
            // Fill in the blanks below and uncomment:
            //
            // OutlinedTextField(
            //     value = ???,
            //     onValueChange = { ??? },
            //     label = { Text("Character Name") },
            //     singleLine = true,
            //     modifier = Modifier.fillMaxWidth()
            // )

            Spacer(Modifier.height(16.dp))

            // Live face preview
            FacePreview(
                skinTone  = selectedSkin,
                eyeColor  = selectedEye,
                hairStyle = selectedHairStyle,
                hairColor = selectedHairColor,
                earType   = selectedEar,
            )

            Spacer(Modifier.height(24.dp))

            // ── Skin Tone ──
            FaceSection(title = "SKIN TONE") {
                skinTones.forEach { tone ->
                    ColorSwatch(
                        color     = tone.previewColor,
                        label     = tone.label,
                        isSelected = selectedSkin == tone.key,
                        onClick   = { selectedSkin = tone.key }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Eyes ──
            FaceSection(title = "EYE COLOR") {
                eyeOptions.forEach { eye ->
                    ColorSwatch(
                        color     = eye.previewColor,
                        label     = eye.label,
                        isSelected = selectedEye == eye.key,
                        onClick   = { selectedEye = eye.key }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Hair Style ──
            HairStyleSection(
                selected = selectedHairStyle,
                onSelect = { selectedHairStyle = it }
            )

            Spacer(Modifier.height(16.dp))

            // ── Hair Color ──
            FaceSection(title = "HAIR COLOR") {
                hairColors.forEach { color ->
                    ColorSwatch(
                        color     = color.previewColor,
                        label     = color.label,
                        isSelected = selectedHairColor == color.key,
                        onClick   = { selectedHairColor = color.key }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Ears ──
            FaceSection(title = "EAR TYPE") {
                earOptions.forEach { ear ->
                    LabelSwatch(
                        label     = "${ear.emoji} ${ear.label}",
                        isSelected = selectedEar == ear.key,
                        onClick   = { selectedEar = ear.key }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                onFaceSelected(
                    FaceSelection(
                        name      = name,
                        skinTone  = selectedSkin,
                        eyeColor  = selectedEye,
                        hairStyle = selectedHairStyle,
                        hairColor = selectedHairColor,
                        earType   = selectedEar,
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent)
        ) {
            Text(
                text = "CONFIRM  →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}

// ── Face preview composable ───────────────────────────────────────────────────
// Stacks the LPC head layers (ears behind, head/skin, eyes, hair) in a static
// south-facing idle pose (walk row, frame 0). Uses the same SpriteLayer renderer
// as CharacterSprite, just limited to the head-relevant layers.
@Composable
fun FacePreview(
    skinTone: String,
    eyeColor: String,
    hairStyle: String,
    hairColor: String,
    earType: String,
    modifier: Modifier = Modifier,
) {
    val style = hairStyles.find { it.key == hairStyle }
    val earsPath = earsAssetPath(earType, skinTone)
    val headPath  = headAssetPath(skinTone)
    val eyesPath  = eyesAssetPath(eyeColor)
    val hairPath  = hairAssetPath(hairStyle, hairColor)
    val bodyPath  = bodyAssetPath(skinTone)

    Box(
        modifier = modifier
            .size(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(PurpleAccent.copy(alpha = 0.08f))
            .border(2.dp, PurpleAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Render order: body → ears → pants → shirt → head → eyes → hair
        SpriteLayer(assetPath = bodyPath,          frame = 0, rowIndex = SpriteFrames.WALK_ROW, modifier = Modifier.fillMaxSize())
        SpriteLayer(assetPath = earsPath,          frame = 0, rowIndex = SpriteFrames.WALK_ROW, modifier = Modifier.fillMaxSize())
        SpriteLayer(assetPath = DEFAULT_PANTS_PATH, frame = 0, rowIndex = SpriteFrames.WALK_ROW, modifier = Modifier.fillMaxSize())
        SpriteLayer(assetPath = DEFAULT_SHIRT_PATH, frame = 0, rowIndex = SpriteFrames.WALK_ROW, modifier = Modifier.fillMaxSize())
        SpriteLayer(assetPath = headPath,          frame = 0, rowIndex = SpriteFrames.WALK_ROW, modifier = Modifier.fillMaxSize())
        SpriteLayer(assetPath = eyesPath,          frame = 0, rowIndex = SpriteFrames.WALK_ROW, modifier = Modifier.fillMaxSize())
        SpriteLayer(assetPath = hairPath,          frame = 0, rowIndex = SpriteFrames.WALK_ROW, modifier = Modifier.fillMaxSize())
    }
}

// ── Hair style grid (wraps across rows for many options) ─────────────────────

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HairStyleSection(selected: String, onSelect: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "HAIR STYLE",
            fontSize = 10.sp,
            color = GoldAccent,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
        )
        Spacer(Modifier.height(8.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            hairStyles.forEach { style ->
                LabelSwatch(
                    label      = style.label,
                    isSelected = selected == style.key,
                    onClick    = { onSelect(style.key) }
                )
            }
        }
    }
}

// ── Shared picker helpers ─────────────────────────────────────────────────────

@Composable
private fun FaceSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 10.sp,
            color = GoldAccent,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
        )
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

// Filled circle swatch for skin/eye/hair color pickers
@Composable
private fun ColorSwatch(
    color: Color,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color)
                .then(
                    if (isSelected) Modifier.border(2.dp, Color.White, CircleShape)
                    else Modifier.border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                )
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 8.sp,
            color = if (isSelected) TextPrimary else TextMuted,
            textAlign = TextAlign.Center,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

// Text pill for hair style / ear type pickers
@Composable
private fun LabelSwatch(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) PurpleAccent.copy(alpha = 0.25f) else CardBg)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) PurpleAccent else Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (isSelected) PurpleAccent else TextMuted,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
