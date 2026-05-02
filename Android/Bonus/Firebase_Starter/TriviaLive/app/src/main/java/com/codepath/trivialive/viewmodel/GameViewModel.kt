package com.codepath.trivialive.viewmodel

import androidx.lifecycle.ViewModel
import com.codepath.trivialive.model.LeaderboardEntry
import com.codepath.trivialive.model.Player
import com.codepath.trivialive.model.Question
import com.codepath.trivialive.model.triviaQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Part 1 — Add Firebase instances here after setup:
// import com.google.firebase.auth.ktx.auth
// import com.google.firebase.firestore.FieldValue
// import com.google.firebase.firestore.ListenerRegistration
// import com.google.firebase.firestore.Query
// import com.google.firebase.firestore.ktx.firestore
// import com.google.firebase.ktx.Firebase

data class GameUiState(
    val isSignedIn: Boolean = false,
    val isHost: Boolean = false,
    val hasJoined: Boolean = false,
    val gameId: String? = null,
    val roomCode: String? = null,
    val playerId: String? = null,
    val playerName: String? = null,
    val gameStatus: String = "waiting",
    val currentQuestion: Int = 0,
    val players: List<Player> = emptyList(),
    val hasAnswered: Boolean = false,
    val lastAnswerCorrect: Boolean? = null,
    val leaderboard: List<LeaderboardEntry> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

class GameViewModel : ViewModel() {

    // Part 1 — Uncomment after Firebase setup:
    // private val auth = Firebase.auth
    // private val db = Firebase.firestore
    // private var gameListener: ListenerRegistration? = null

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    val questions: List<Question> = triviaQuestions

    // ── Auth ─────────────────────────────────────────────────────────────────

    fun createAccount(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        // TODO: Firebase — Part 2
        // auth.createUserWithEmailAndPassword(email, password)
        //     .addOnSuccessListener {
        //         _uiState.update { it.copy(isSignedIn = true, isHost = true, isLoading = false) }
        //     }
        //     .addOnFailureListener { e ->
        //         _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        //     }
    }

    fun signIn(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        // TODO: Firebase — Part 2
        // auth.signInWithEmailAndPassword(email, password)
        //     .addOnSuccessListener {
        //         _uiState.update { it.copy(isSignedIn = true, isHost = true, isLoading = false) }
        //     }
        //     .addOnFailureListener { e ->
        //         _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        //     }
    }

    fun signOut() {
        // TODO: Firebase — Part 2
        // auth.signOut()
        _uiState.update { GameUiState() }
    }

    // ── Game creation — Part 3 ───────────────────────────────────────────────

    fun createGame() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        // TODO: Firebase — Part 3
        // val uid = auth.currentUser?.uid ?: return
        // val gameData = hashMapOf(
        //     "hostId"          to uid,
        //     "status"          to "waiting",
        //     "currentQuestion" to 0,
        //     "questions"       to questions.map { q ->
        //         hashMapOf("text" to q.text, "options" to q.options, "correctIndex" to q.correctIndex)
        //     }
        // )
        // db.collection("games")
        //     .add(gameData)
        //     .addOnSuccessListener { docRef ->
        //         val roomCode = docRef.id.take(6).uppercase()
        //         _uiState.update { it.copy(gameId = docRef.id, roomCode = roomCode, isLoading = false) }
        //         listenToPlayers(docRef.id)
        //     }
        //     .addOnFailureListener { e ->
        //         _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        //     }
    }

    // ── Joining & listening — Part 4 ─────────────────────────────────────────

    fun joinGame(roomCode: String, playerName: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        // TODO: Firebase — Part 4
        // db.collection("games")
        //     .get()
        //     .addOnSuccessListener { result ->
        //         val gameDoc = result.documents.firstOrNull {
        //             it.id.take(6).uppercase() == roomCode.uppercase()
        //         } ?: run {
        //             _uiState.update { it.copy(errorMessage = "Game not found", isLoading = false) }
        //             return@addOnSuccessListener
        //         }
        //         val gameId = gameDoc.id
        //         val playerData = hashMapOf("name" to playerName, "score" to 0)
        //         db.collection("games").document(gameId)
        //             .collection("players")
        //             .add(playerData)
        //             .addOnSuccessListener { playerRef ->
        //                 _uiState.update { it.copy(
        //                     gameId = gameId,
        //                     playerId = playerRef.id,
        //                     playerName = playerName,
        //                     hasJoined = true,
        //                     isLoading = false
        //                 )}
        //                 listenToGame(gameId)
        //             }
        //     }
        //     .addOnFailureListener { e ->
        //         _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        //     }
    }

    fun listenToGame(gameId: String) {
        // TODO: Firebase — Part 4
        // gameListener = db.collection("games").document(gameId)
        //     .addSnapshotListener { snapshot, error ->
        //         if (error != null || snapshot == null) return@addSnapshotListener
        //         val status = snapshot.getString("status") ?: return@addSnapshotListener
        //         val currentQ = snapshot.getLong("currentQuestion")?.toInt() ?: 0
        //         _uiState.update { it.copy(
        //             gameStatus = status,
        //             currentQuestion = currentQ,
        //             hasAnswered = false
        //         )}
        //     }
    }

    fun listenToPlayers(gameId: String) {
        // TODO: Firebase — Part 4
        // db.collection("games").document(gameId)
        //     .collection("players")
        //     .addSnapshotListener { snapshot, error ->
        //         if (error != null || snapshot == null) return@addSnapshotListener
        //         val players = snapshot.documents.map { doc ->
        //             Player(
        //                 id    = doc.id,
        //                 name  = doc.getString("name") ?: "Unknown",
        //                 score = doc.getLong("score")?.toInt() ?: 0
        //             )
        //         }
        //         _uiState.update { it.copy(players = players) }
        //     }
    }

    fun startGame() {
        val gameId = _uiState.value.gameId ?: return
        // TODO: Firebase — Part 4
        // db.collection("games").document(gameId)
        //     .update("status", "active")
    }

    // ── Gameplay — Part 5 ────────────────────────────────────────────────────

    fun submitAnswer(answerIndex: Int) {
        if (_uiState.value.hasAnswered) return
        val state = _uiState.value
        val question = questions[state.currentQuestion]
        val isCorrect = answerIndex == question.correctIndex
        _uiState.update { it.copy(hasAnswered = true, lastAnswerCorrect = isCorrect) }
        // TODO: Firebase — Part 5
        // val gameId   = state.gameId   ?: return
        // val playerId = state.playerId ?: return
        // val points   = if (isCorrect) 100L else 0L
        // db.collection("games").document(gameId)
        //     .collection("players").document(playerId)
        //     .update(
        //         "score",      FieldValue.increment(points),
        //         "lastAnswer", answerIndex
        //     )
    }

    fun advanceQuestion() {
        val state = _uiState.value
        val gameId = state.gameId ?: return
        val nextIndex = state.currentQuestion + 1
        // TODO: Firebase — Part 5
        // val update = if (nextIndex >= questions.size) {
        //     mapOf("currentQuestion" to nextIndex, "status" to "finished")
        // } else {
        //     mapOf("currentQuestion" to nextIndex)
        // }
        // db.collection("games").document(gameId).update(update)
    }

    fun loadLeaderboard() {
        val gameId = _uiState.value.gameId ?: return
        // TODO: Firebase — Part 5
        // db.collection("games").document(gameId)
        //     .collection("players")
        //     .orderBy("score", Query.Direction.DESCENDING)
        //     .get()
        //     .addOnSuccessListener { result ->
        //         val leaderboard = result.documents.mapIndexed { index, doc ->
        //             LeaderboardEntry(
        //                 rank  = index + 1,
        //                 name  = doc.getString("name") ?: "Unknown",
        //                 score = doc.getLong("score")?.toInt() ?: 0
        //             )
        //         }
        //         _uiState.update { it.copy(leaderboard = leaderboard) }
        //     }
    }

    fun deleteGame() {
        val gameId = _uiState.value.gameId ?: return
        // TODO: Firebase — Part 5 (optional cleanup)
        // db.collection("games").document(gameId)
        //     .collection("players").get()
        //     .addOnSuccessListener { result ->
        //         val batch = db.batch()
        //         result.documents.forEach { batch.delete(it.reference) }
        //         batch.commit().addOnSuccessListener {
        //             db.collection("games").document(gameId).delete()
        //         }
        //     }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    override fun onCleared() {
        // TODO: Firebase — Part 4 — remove the listener to prevent memory leaks:
        // gameListener?.remove()
        super.onCleared()
    }
}
