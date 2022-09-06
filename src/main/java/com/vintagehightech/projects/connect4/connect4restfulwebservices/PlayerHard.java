package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Arrays;

public class PlayerHard implements Player {

    int currentPlayer;
    int oppositionPlayer;
    final int depthTarget = 5;

    public int[] makeMove(int[][] board, int playerNumber) {
        System.out.println("Hard Level Move");
        this.currentPlayer = playerNumber;
        this.oppositionPlayer = playerNumber == 1 ? 2 : 1;

        int[][] tempBoard = Arrays.copyOf(board, board.length);
//        Board.terminalDisplayBoard(tempBoard);

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 5; j++) {
                if (tempBoard[i][j] == 0) {
                    tempBoard[i][j] = playerNumber;
                    int score = minimax(tempBoard, depthTarget, Integer.MIN_VALUE, Integer.MAX_VALUE,  false);
                    System.out.println("Column " + i + ": score = " + score);

                    tempBoard[i][j] = 0;
                    if (score > bestScore) {
                        bestMove = new int[]{i, j};
                        bestScore = score;
                    }
                    break;
                }
            }
        }
        System.out.println("bestMove = " + Arrays.toString(bestMove));

//        PlayerEasy temp = new PlayerEasy();
//        return temp.makeMove(board, playerNumber);
        board[bestMove[0]][bestMove[1]] = playerNumber;
        return bestMove;
    }

    public int minimax(int[][] board, int depth, int alpha, int beta, boolean isMax) {
//        int score = checkScore(board, coordinates[0], coordinates[1], player);
        int score = checkScoreAll(board, depth);
        if (depth <= 0) {
            return score;
        }
        if (Board.boardFull(board)) {
           return 0;
        }
        if (score == depthTarget || score == -depthTarget) {
            return score;
        }
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i <= 6; i++) {
                for (int j = 0; j <= 5; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = currentPlayer;
                        best = Math.max(best, minimax(board, depth - 1, alpha, beta, false));
//                        System.out.println("max");


//                        Board.terminalDisplayBoard(board); // ******** TEMP
//                        System.out.println("Score for Max = " + checkScore(board, i, j, true)); // TEMP
//                        System.out.println("Score for Min = " + checkScore(board, i, j, false)); // TEMP

                        board[i][j] = 0;
                        break;
                    }
                }
//                alpha = Math.max(alpha, best);
//                if (beta <= alpha) {
//                    break;
//                }
            }
//            System.out.println("Maximise Best = " + best);
            return best;
        } else {
            int best = Integer.MAX_VALUE;
//            int oppPlayer = player == 1 ? 2 : 1;
            for (int i = 0; i <= 6; i++) {
                for (int j = 0; j <= 5; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = oppositionPlayer;
                        best = Math.min(best, minimax(board, depth - 1, alpha, beta, true));
//                        System.out.println("min");

//                        Board.terminalDisplayBoard(board); // ******** TEMP
//                        System.out.println("Score for Max = " + checkScore(board, i, j, true)); // TEMP
//                        System.out.println("Score for Min = " + checkScore(board, i, j, false)); // TEMP

                        board[i][j] = 0;
                        break;
                    }
                }
//                beta = Math.min(beta, best);
//                if (beta <= alpha) {
//                    break;
//                }
            }
//            System.out.println("Minimise Best = " + best);
            return best;
        }

//        return 0; // **** TEMP *****
    }
//
    public int checkScore(int[][] board, int col, int row, int player) {
        if (player == currentPlayer) {
            if (Board.winningMove(board, col, row, player)) {
                return 20;
            }
            if (Board.checkMove(board, col, row, player)) {
                return 10;
            }
        }
        if (player == oppositionPlayer) {
            if (Board.winningMove(board, col, row, player)) {
                return -20;
            }
            if (Board.checkMove(board, col, row, player)) {
                return -10;
            }
        }
        return 0; // Should be 0. Temp set to 5 so I can see when this method returns this statement.
    }
    public int checkScoreAll(int[][] board, int depth) {
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board[0].length; row++) {
                if (board[col][row] == currentPlayer && Board.winningMove(board, col, row, currentPlayer)) {
                    if (depth == depthTarget - 1) {
                        System.out.println("-------");
                        System.out.println("depth: " + depth + " win: ");
                        Board.terminalDisplayBoard(board);
                        System.out.println("-------");
                    }
                    return  (depth + 1);
                }
                if (board[col][row] == oppositionPlayer && Board.winningMove(board, col, row, oppositionPlayer)) {
                    if (depth == depthTarget - 1) {
                        System.out.println("-------");
                        System.out.println("depth: " + depth + " lose: ");
                        Board.terminalDisplayBoard(board);
                        System.out.println("-------");
                    }
                    return - (depth + 1);
                }
//                if (board[col][row] == currentPlayer && Board.checkMove(board, col, row, currentPlayer)) {
//                    return 10;
//                }
//                if (board[col][row] == oppositionPlayer && Board.checkMove(board, col, row, oppositionPlayer)) {
//                    return -10;
//                }
            }
        }
        return 0;
    }
}
