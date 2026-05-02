package com.codepath.trivialive.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codepath.trivialive.model.Question
import com.codepath.trivialive.ui.theme.AnswerCorrect
import com.codepath.trivialive.ui.theme.AnswerNeutral
import com.codepath.trivialive.ui.theme.AnswerWrong
import com.codepath.trivialive.ui.theme.GameBackground
import com.codepath.trivialive.ui.theme.GamePrimary
import com.codepath.trivialive.ui.theme.GameSurface
import com.codepath.trivialive.ui.theme.OptionColors
import com.codepath.trivialive.viewmodel.GameUiState

private val optionLabels = listOf("A", "B", "C", "D")

@Composable
fun QuestionScreen(
    uiState: GameUiState,
    questions: List<Question>,
    onSubmitAnswer: (Int) -> Unit,
    onAdvanceQuestion: () -> Unit,
    onLoadLeaderboard: () -> Unit,
    onNavigateToLeaderboard: () -> Unit
) {
    LaunchedEffect(uiState.gameStatus) {
        if (uiState.gameStatus == "finished") {
            onLoadLeaderboard()
            onNavigateToLeaderboard()
        }
    }

    val currentQ = questions.getOrNull(uiState.currentQuestion) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameBackground)
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Q ${uiState.currentQuestion + 1} / ${questions.size}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (uiState.isHost) {
                Text(
                    text = "${uiState.players.size} players",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { (uiState.currentQuestion + 1).toFloat() / questions.size },
            modifier = Modifier.fillMaxWidth(),
            color = GamePrimary,
            trackColor = GameSurface
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GameSurface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = currentQ.text,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp).fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (uiState.isHost) {
            HostQuestionControls(
                uiState = uiState,
                onAdvanceQuestion = onAdvanceQuestion
            )
        } else {
            PlayerAnswerButtons(
                question = currentQ,
                uiState = uiState,
                onSubmitAnswer = onSubmitAnswer
            )
        }
    }
}

@Composable
private fun PlayerAnswerButtons(
    question: Question,
    uiState: GameUiState,
    onSubmitAnswer: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        question.options.forEachIndexed { index, option ->
            val bgColor: Color = when {
                !uiState.hasAnswered -> OptionColors[index]
                index == question.correctIndex -> AnswerCorrect
                else -> AnswerWrong.copy(alpha = 0.5f)
            }
            Button(
                onClick = { onSubmitAnswer(index) },
                enabled = !uiState.hasAnswered,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = bgColor,
                    disabledContainerColor = bgColor
                )
            ) {
                Text(
                    text = "${optionLabels[index]}.  $option",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }

        if (uiState.hasAnswered) {
            Spacer(modifier = Modifier.height(8.dp))
            val feedbackText = if (uiState.lastAnswerCorrect == true) "✅  Correct! +100 points" else "❌  Wrong answer"
            val feedbackColor = if (uiState.lastAnswerCorrect == true) AnswerCorrect else AnswerWrong
            Text(
                text = feedbackText,
                style = MaterialTheme.typography.titleMedium,
                color = feedbackColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Waiting for the host to continue...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HostQuestionControls(
    uiState: GameUiState,
    onAdvanceQuestion: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Scoreboard",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (uiState.players.isEmpty()) {
            Text(
                text = "No players yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            val sorted = uiState.players.sortedByDescending { it.score }
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(sorted) { player ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = GameSurface),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(player.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text("${player.score} pts", style = MaterialTheme.typography.titleMedium, color = GamePrimary)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAdvanceQuestion,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GamePrimary)
        ) {
            Text("Next Question  →", style = MaterialTheme.typography.titleMedium)
        }
    }
}
