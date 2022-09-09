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

    public static Connect4Game moveMade(Connect4Game game, int columnIndex) {
        if (!game.inProgress) {
            return game;
        }
        for (int i = 0; i <= 5; i++) {
            if(game.board[columnIndex][i] == 0) {
                game.board[columnIndex][i] = game.currentPlayer;

                game.error = null; // why set to null??
                if (Board.winningMove(game.board, columnIndex, i, game.currentPlayer)) {
                    game.gameOver = true;
                    game.inProgress = false;
                    game.error = String.format("Game Over. The winner is %s!", game.currentPlayer == 1 ? "Yellow" : "Red");
                } else if (Board.boardFull(game.board)) {
                    game.gameOver = true;
                    game.inProgress = false;
                    game.error = "Game Over. It's a draw!";
                } else {
                    game.currentPlayer = game.currentPlayer == 1 ? 2 : 1;
                }
                game.latestMove = new int[] {columnIndex, i};
                return game; /// Something wrong with this if statement - look at first if!!
            }
        }
        game.error = "Can't move here!";
        game.latestMove = new int[] {-1, -1};
        return game;
    }

    public static Connect4Game requestMove(Connect4Game game, int playerNumber) {
//        System.out.println("requestMove. Player: " + playerNumber); // *** TEMP ***
        if (!game.inProgress) {
            return game;
        }
        int[] index = playerNumber == 1 ? game.one.makeMove(game.board, game.latestMove, playerNumber) : game.two.makeMove(game.board, game.latestMove, playerNumber);

        if (Board.winningMove(game.board, index[0], index[1], playerNumber)) {
            game.gameOver = true;
            game.inProgress = false;
            game.setLatestMove(index);
            game.error = String.format("Game Over. The winner is %s!", game.currentPlayer == 1 ? "Yellow" : "Red");
            return game;
        }
        if (Board.boardFull(game.board)) {
            game.gameOver = true;
            game.inProgress = false;
            game.setLatestMove(index);
            game.error = "Game Over. It's a draw!";
            return game;
        }
        game.setLatestMove(index);
        game.currentPlayer = game.currentPlayer == 1 ? 2 : 1;
        return game;
    }

    public static Connect4Game resetGame() {
        Connect4Game game = new Connect4Game();
        game.playerOneType = -1;
        game.playerTwoType = -1;
        game.error = "Select Players to start game. Yellow goes first.";
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
        The following lines are used to initialise the board using one of the test boards
        in the TestBoards class. For a normal game to function, these lines are
        commented out.
         */

//        TestBoards testBoardInstance = new TestBoards();
//        int[][] test = testBoardInstance.testH9;
//        game.setBoard(test);

        game.error = "Select Players to start game. Yellow goes first."; // this hasn't worked!!
        game.currentPlayer = 1;
        game.gameOver = false;
        game.inProgress = false;
        return game;
    }
}
