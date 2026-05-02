package com.codepath.trivialive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codepath.trivialive.ui.HostLoginScreen
import com.codepath.trivialive.ui.JoinScreen
import com.codepath.trivialive.ui.LeaderboardScreen
import com.codepath.trivialive.ui.LobbyScreen
import com.codepath.trivialive.ui.HomeScreen
import com.codepath.trivialive.ui.QuestionScreen
import com.codepath.trivialive.viewmodel.GameViewModel

@Composable
fun AppNavigation(viewModel: GameViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                onHostClick = { navController.navigate("auth") },
                onJoinClick = { navController.navigate("join") }
            )
        }

        composable("auth") {
            HostLoginScreen(
                uiState = uiState,
                onSignIn = viewModel::signIn,
                onCreateAccount = viewModel::createAccount,
                onNavigateToLobby = {
                    navController.navigate("lobby") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }

        composable("lobby") {
            LobbyScreen(
                uiState = uiState,
                onCreateGame = viewModel::createGame,
                onStartGame = viewModel::startGame,
                onListenToPlayers = viewModel::listenToPlayers,
                onNavigateToQuestion = {
                    navController.navigate("question") {
                        popUpTo("lobby") { inclusive = false }
                    }
                }
            )
        }

        composable("join") {
            JoinScreen(
                uiState = uiState,
                onJoinGame = viewModel::joinGame,
                onNavigateToQuestion = {
                    navController.navigate("question") {
                        popUpTo("join") { inclusive = false }
                    }
                }
            )
        }

        composable("question") {
            QuestionScreen(
                uiState = uiState,
                questions = viewModel.questions,
                onSubmitAnswer = viewModel::submitAnswer,
                onAdvanceQuestion = viewModel::advanceQuestion,
                onLoadLeaderboard = viewModel::loadLeaderboard,
                onNavigateToLeaderboard = {
                    navController.navigate("leaderboard") {
                        popUpTo("question") { inclusive = true }
                    }
                }
            )
        }

        composable("leaderboard") {
            LeaderboardScreen(
                uiState = uiState,
                onPlayAgain = {
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
