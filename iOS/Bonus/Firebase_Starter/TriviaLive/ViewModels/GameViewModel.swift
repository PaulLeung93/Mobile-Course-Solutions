import Foundation

// Part 1 — Add Firebase imports here after setup:
// import FirebaseAuth
// import FirebaseFirestore

@MainActor
class GameViewModel: ObservableObject {

    // Part 1 — Uncomment after Firebase setup:
    // private let auth = Auth.auth()
    // private let db   = Firestore.firestore()
    // private var gameListener: ListenerRegistration?

    // ── Published state ──────────────────────────────────────────────────────
    @Published var isSignedIn        = false
    @Published var isHost            = false
    @Published var hasJoined         = false
    @Published var gameId:     String?  = nil
    @Published var roomCode:   String?  = nil
    @Published var playerId:   String?  = nil
    @Published var playerName: String?  = nil
    @Published var gameStatus        = "waiting"
    @Published var currentQuestion   = 0
    @Published var players:    [Player]          = []
    @Published var hasAnswered       = false
    @Published var lastAnswerCorrect: Bool?       = nil
    @Published var leaderboard: [LeaderboardEntry] = []
    @Published var errorMessage: String?          = nil
    @Published var isLoading         = false

    // ── Question bank ────────────────────────────────────────────────────────
    let questions: [Question] = triviaQuestions

    // ── Auth — Part 2 ─────────────────────────────────────────────────────────

    func createAccount(email: String, password: String) {
        isLoading = true
        errorMessage = nil
        // TODO: Firebase — Part 2
        // auth.createUser(withEmail: email, password: password) { [weak self] _, error in
        //     Task { @MainActor in
        //         self?.isLoading = false
        //         if let error { self?.errorMessage = error.localizedDescription; return }
        //         self?.isSignedIn = true
        //         self?.isHost     = true
        //     }
        // }
    }

    func signIn(email: String, password: String) {
        isLoading = true
        errorMessage = nil
        // TODO: Firebase — Part 2
        // auth.signIn(withEmail: email, password: password) { [weak self] _, error in
        //     Task { @MainActor in
        //         self?.isLoading = false
        //         if let error { self?.errorMessage = error.localizedDescription; return }
        //         self?.isSignedIn = true
        //         self?.isHost     = true
        //     }
        // }
    }

    func signOut() {
        // TODO: Firebase — Part 2
        // try? auth.signOut()
        resetState()
    }

    // ── Game creation — Part 3 ────────────────────────────────────────────────

    func createGame() {
        isLoading = true
        errorMessage = nil
        // TODO: Firebase — Part 3
        // guard let uid = auth.currentUser?.uid else { return }
        // let gameData: [String: Any] = [
        //     "hostId":          uid,
        //     "status":          "waiting",
        //     "currentQuestion": 0,
        //     "questions": questions.map { q in
        //         ["text": q.text, "options": q.options, "correctIndex": q.correctIndex]
        //     }
        // ]
        // var ref: DocumentReference?
        // ref = db.collection("games").addDocument(data: gameData) { [weak self] error in
        //     Task { @MainActor in
        //         self?.isLoading = false
        //         if let error { self?.errorMessage = error.localizedDescription; return }
        //         guard let docId = ref?.documentID else { return }
        //         self?.gameId   = docId
        //         self?.roomCode = String(docId.prefix(6)).uppercased()
        //         self?.listenToPlayers(gameId: docId)
        //     }
        // }
    }

    // ── Joining & listening — Part 4 ──────────────────────────────────────────

    func joinGame(roomCode: String, playerName: String) {
        isLoading = true
        errorMessage = nil
        // TODO: Firebase — Part 4
        // db.collection("games").getDocuments { [weak self] snapshot, error in
        //     guard let self, let snapshot, error == nil else { return }
        //     guard let gameDoc = snapshot.documents.first(where: {
        //         String($0.documentID.prefix(6)).uppercased() == roomCode.uppercased()
        //     }) else {
        //         Task { @MainActor in self.errorMessage = "Game not found"; self.isLoading = false }
        //         return
        //     }
        //     let gameId = gameDoc.documentID
        //     let playerData: [String: Any] = ["name": playerName, "score": 0]
        //     self.db.collection("games").document(gameId)
        //         .collection("players").addDocument(data: playerData) { [weak self] error in
        //             Task { @MainActor in
        //                 guard let self, error == nil else { return }
        //                 self.gameId     = gameId
        //                 self.playerName = playerName
        //                 self.hasJoined  = true
        //                 self.isLoading  = false
        //                 self.listenToGame(gameId: gameId)
        //             }
        //         }
        // }
    }

    func listenToGame(gameId: String) {
        // TODO: Firebase — Part 4
        // gameListener = db.collection("games").document(gameId)
        //     .addSnapshotListener { [weak self] snapshot, error in
        //         guard let self, let snapshot, error == nil else { return }
        //         let data = snapshot.data() ?? [:]
        //         Task { @MainActor in
        //             self.gameStatus      = data["status"]          as? String ?? "waiting"
        //             self.currentQuestion = data["currentQuestion"] as? Int    ?? 0
        //             self.hasAnswered      = false
        //         }
        //     }
    }

    func listenToPlayers(gameId: String) {
        // TODO: Firebase — Part 4
        // db.collection("games").document(gameId).collection("players")
        //     .addSnapshotListener { [weak self] snapshot, error in
        //         guard let self, let snapshot, error == nil else { return }
        //         let players = snapshot.documents.map { doc in
        //             Player(id: doc.documentID,
        //                    name: doc["name"]  as? String ?? "Unknown",
        //                    score: doc["score"] as? Int    ?? 0)
        //         }
        //         Task { @MainActor in self.players = players }
        //     }
    }

    func startGame() {
        guard let gameId else { return }
        // TODO: Firebase — Part 4
        // db.collection("games").document(gameId).updateData(["status": "active"])
    }

    // ── Gameplay — Part 5 ─────────────────────────────────────────────────────

    func submitAnswer(index: Int) {
        guard !hasAnswered else { return }
        let question = questions[currentQuestion]
        let isCorrect = index == question.correctIndex
        hasAnswered       = true
        lastAnswerCorrect = isCorrect
        // TODO: Firebase — Part 5
        // guard let gameId, let playerId else { return }
        // let points = isCorrect ? 100 : 0
        // db.collection("games").document(gameId)
        //     .collection("players").document(playerId)
        //     .updateData([
        //         "score":      FieldValue.increment(Int64(points)),
        //         "lastAnswer": index
        //     ])
    }

    func advanceQuestion() {
        guard let gameId else { return }
        let nextIndex = currentQuestion + 1
        // TODO: Firebase — Part 5
        // let update: [String: Any] = nextIndex >= questions.count
        //     ? ["currentQuestion": nextIndex, "status": "finished"]
        //     : ["currentQuestion": nextIndex]
        // db.collection("games").document(gameId).updateData(update)
    }

    func loadLeaderboard() {
        guard let gameId else { return }
        // TODO: Firebase — Part 5
        // db.collection("games").document(gameId)
        //     .collection("players")
        //     .order(by: "score", descending: true)
        //     .getDocuments { [weak self] snapshot, error in
        //         guard let self, let snapshot, error == nil else { return }
        //         let entries = snapshot.documents.enumerated().map { index, doc in
        //             LeaderboardEntry(rank: index + 1,
        //                              name: doc["name"]  as? String ?? "Unknown",
        //                              score: doc["score"] as? Int    ?? 0)
        //         }
        //         Task { @MainActor in self.leaderboard = entries }
        //     }
    }

    func deleteGame() {
        guard let gameId else { return }
        // TODO: Firebase — Part 5 (optional cleanup)
        // db.collection("games").document(gameId)
        //     .collection("players").getDocuments { [weak self] snapshot, error in
        //         guard let self, let snapshot, error == nil else { return }
        //         let batch = self.db.batch()
        //         snapshot.documents.forEach { batch.deleteDocument($0.reference) }
        //         batch.commit { _ in
        //             self.db.collection("games").document(gameId).delete()
        //         }
        //     }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    func clearError() { errorMessage = nil }

    private func resetState() {
        isSignedIn = false; isHost = false; hasJoined = false
        gameId = nil; roomCode = nil; playerId = nil; playerName = nil
        gameStatus = "waiting"; currentQuestion = 0; players = []
        hasAnswered = false; lastAnswerCorrect = nil; leaderboard = []
        errorMessage = nil; isLoading = false
        // TODO: Firebase — Part 4 — remove the listener to prevent memory leaks:
        // gameListener?.remove()
    }
}
