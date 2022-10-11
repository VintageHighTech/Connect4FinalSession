# CONNECT 4
-----------
##  OUTLINE

	
	This web app comprises a Java & Spring Boot backend with ReactJS frontend. Axios,
	a promise-based HTTP client for node.js, makes API calls to the Java backend. The
	board status and game logic are managed by the backend.

	The game controller uses HttpSessions to manage games. Each game session is assigned
    an ID. The frontend stores the session id as a cookie.
	
	The game includes three difficulty levels: Easy, Medium & Hard:

	The Easy player will make make a predetermined "bestFirstMove" for its first move.
	It will then take the first opportunity to win or block a potential winning move.
	After that, all other moves are chosen at random.
	
	The Medium player will make a predetermined "bestFirstMove" for its first move.
	For each subsequent move it will first check if there is a potential winning move.
	If not, it will check for a potential blocking move. If neither move is applicable,
	it will choose a column at random.
	
	The Hard player comprises a combination of the MiniMax algorithm - with alpha beta pruning -
	and blocking moves. MiniMax looks at all potential moves up to eight moves ahead of the
	current board. For this reason, several potential moves may return the same value, i.e. the
	moves are equally good/bad. In this case, all potential good moves are passed to the nextBestMove
	method of the SimpleMoves class. The total number of Connect 4 game situations is
	4,531,985,219,092. For this reason, it isn't practical for the MiniMax algorithm to determine
	every possible endgame.  
	
	"bestFirstMove" looks at the bottom row of the board and determines the best first move
	to make, whether the board is empty or a single move has already been made. The 2D arrays
	"oppositionIsOne" & "oppositionIsTwo" contain integer arrays that represent every possible
	state of the first row. The index of each row in the 2D array corresponds to the index
	of the best move to make in the "firstMoves" array.
	
	"nextBestMove" firstly uses the "checkNeighbour" method to establish if a blocking move
	should be made. If not, it uses the "hasMostNeighbours" method to make a move in the
	position that has the greatest number of adjacent opposition pieces.
	