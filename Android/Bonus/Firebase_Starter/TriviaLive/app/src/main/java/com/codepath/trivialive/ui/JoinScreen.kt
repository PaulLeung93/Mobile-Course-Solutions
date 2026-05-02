package com.codepath.trivialive.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codepath.trivialive.ui.theme.GameBackground
import com.codepath.trivialive.ui.theme.GamePrimary
import com.codepath.trivialive.ui.theme.GameSecondary
import com.codepath.trivialive.viewmodel.GameUiState

@Composable
fun JoinScreen(
    uiState: GameUiState,
    onJoinGame: (String, String) -> Unit,
    onNavigateToQuestion: () -> Unit
) {
    var roomCode by remember { mutableStateOf("") }
    var playerName by remember { mutableStateOf("") }

    LaunchedEffect(uiState.gameStatus) {
        if (uiState.gameStatus == "active") onNavigateToQuestion()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameBackground)
            .padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.hasJoined) {
            Text(text = "⏳", style = MaterialTheme.typography.displayLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "You're in!",
                style = MaterialTheme.typography.headlineMedium,
                color = GameSecondary
            )
            Text(
                text = "Waiting for the host to start the game...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Text(
                text = "Join a Game",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Enter the room code from your host",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 28.dp)
            )

            OutlinedTextField(
                value = roomCode,
                onValueChange = { roomCode = it.uppercase().take(6) },
                label = { Text("Room Code") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleLarge.copy(letterSpacing = 6.dp.value.let {
                    androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Sp)
                })
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it.take(20) },
                label = { Text("Your Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            uiState.errorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(color = GamePrimary)
            } else {
                Button(
                    onClick = { onJoinGame(roomCode, playerName) },
                    enabled = roomCode.length == 6 && playerName.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GamePrimary)
                ) {
                    Text("Join Game", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
