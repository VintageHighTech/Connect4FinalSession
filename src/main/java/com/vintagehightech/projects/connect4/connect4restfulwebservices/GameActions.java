package com.vintagehightech.projects.connect4.connect4restfulwebservices;

public class GameActions {

    public static Connect4Game setPlayers(Connect4Game game, int p1, int p2) {
        switch (p1) {
            case (0):
                game.playerOneType = 0;
                break;
            case (1):
                game.one = new PlayerEasy();
                game.playerOneType = 1;
                break;
            case (2):
                game.one = new PlayerMedium();
                game.playerOneType = 2;
                break;
            case (3):
                game.one = new PlayerHard();
                game.playerOneType = 3;
        }
        switch (p2) {
            case (0):
                game.playerTwoType = 0;
                break;
            case (1):
                game.two = new PlayerEasy();
                game.playerTwoType = 1;
                break;
            case (2):
                game.two = new PlayerMedium();
                game.playerTwoType = 2;
                break;
            case (3):
                game.two = new PlayerHard();
                game.playerTwoType = 3;
                break;
        }
        return game;
    }
    /*
        makeHumanMove handles moves made by a human player. It firstly checks if there is an empty
        position in the chosen column. It then checks if the move was a winning move. If not,
        it checks if the board is full i.e. the game is a draw. It also sets the message
        parameter to indicate who's move is next.
     */
    public static Connect4Game makeHumanMove(Connect4Game game, int columnIndex) {
        if (!game.inProgress) {
            return game;
        }
        for (int i = 0; i <= 5; i++) {
            if(game.board[columnIndex][i] == 0) {
                game.board[columnIndex][i] = game.currentPlayer;

                if (Board.isWinningMove(game.board, columnIndex, i, game.currentPlayer, true)) {
                    game.gameOver = true;
                    game.inProgress = false;
                    game.message = String.format("Game Over. %s Wins!", game.currentPlayer == 1 ? "Orange" : "Blue");
                } else if (Board.boardIsFull(game.board)) {
                    game.gameOver = true;
                    game.inProgress = false;
                    game.message = "Game Over. It's a draw!";
                } else {
                    game.currentPlayer = game.currentPlayer == 1 ? 2 : 1;
                    game.message = game.currentPlayer == 1 ? "Orange's move" : "Blue's move";
                }
                game.latestMove = new int[] {columnIndex, i};
                return game;
            }
        }
        game.message = "Can't move here!";
        game.latestMove = new int[] {-1, -1};
        return game;
    }

    /*
        requestMove calls the makeMove method from an AI Player using the Player interface.
        It then checks if the move was a winning move. If not,
        it checks if the board is full i.e. the game is a draw. It also sets the message
        parameter to indicate who's move is next.
     */
    public static Connect4Game requestMove(Connect4Game game, int playerNumber) {
        if (!game.inProgress) {
            return game;
        }
        int[] index = playerNumber == 1 ? game.one.makeMove(game.board, game.latestMove, playerNumber)
                : game.two.makeMove(game.board, game.latestMove, playerNumber);

        if (Board.isWinningMove(game.board, index[0], index[1], playerNumber, true)) {
            game.gameOver = true;
            game.inProgress = false;
            game.setLatestMove(index);
            game.message = String.format("Game Over. %s Wins!", game.currentPlayer == 1 ? "Orange" : "Blue");
            return game;
        }
        if (Board.boardIsFull(game.board)) {
            game.gameOver = true;
            game.inProgress = false;
            game.setLatestMove(index);
            game.message = "Game Over. It's a draw!";
            return game;
        }
        game.setLatestMove(index);
        game.currentPlayer = game.currentPlayer == 1 ? 2 : 1;
        game.message = game.currentPlayer == 1 ? "Orange's move" : "Blue's move";
        return game;
    }

    public static Connect4Game resetGame() {
        Connect4Game game = new Connect4Game();
        game.playerOneType = -1;
        game.playerTwoType = -1;
        game.message = "Select players to start. Orange goes first.";
        game.setBoard(new int[7][6]);
        game.setLatestMove(new int[] {-1, -1});
        game.currentPlayer = 1;
        game.gameOver = false;
        game.inProgress = false;
        return game;
    }

    public static Connect4Game resetBoard(Connect4Game game) {
        game.setLatestMove(new int[] {-1, -1});
        game.setBoard(new int[7][6]);

        /*
        The following lines initialise the board using one of the test boards
        in the TestBoards class. For a normal game to function, these lines are
        commented out.
         */

//        TestBoards testBoardInstance = new TestBoards();
//        int[][] test = testBoardInstance.ui1;
//        game.setBoard(test);

        game.message = "Select players to start. Orange goes first."; // this hasn't worked!!
        game.currentPlayer = 1;
        game.gameOver = false;
        game.inProgress = false;
        return game;
    }
}
