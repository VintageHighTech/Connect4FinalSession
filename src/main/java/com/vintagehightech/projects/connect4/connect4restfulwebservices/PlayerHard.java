package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Arrays;

public class PlayerHard implements Player {
    int currentPlayer;
    int oppositionPlayer;
    final int depthTarget = 8;
    boolean firstMove = true;

    public int[] makeMove(int[][] board, int[] latestMove, int playerNumber) {
        System.out.println("Hard Level Move");

        if (firstMove) {
            int column = SimpleMoves.bestFirstMove(board, playerNumber);
            firstMove = false;
            board[column][0] = playerNumber;
            return new int[] {column, 0};
        }

        // *** Check board for potential win or potential block move:
        int[] checkWinOrBlock = Board.potentialWin(board, playerNumber);
        if (checkWinOrBlock[0] != -1) {
            board[checkWinOrBlock[0]][checkWinOrBlock[1]] = playerNumber;
            System.out.println("Blocked by Hard!");
            return checkWinOrBlock;
        }

        /*
        Set global variables for current player and opposition to avoid
        passing values between methods.
          */
        this.currentPlayer = playerNumber;
        this.oppositionPlayer = playerNumber == 1 ? 2 : 1;

        /*
        Do we need to copy the board??
         */
        int[][] tempBoard = Arrays.copyOf(board, board.length);

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        int[] allScores = new int[7];

        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 5; j++) {
                if (tempBoard[i][j] == 0) {
                    tempBoard[i][j] = playerNumber;
                    int score = minimax(
                            tempBoard,
                            depthTarget,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            new int[] {i, j},
                            false);
//                    System.out.println("Column " + i + ": score = " + score); // *** TEMP ***
                    tempBoard[i][j] = 0;
                    allScores[i] = score;

                    if (score > bestScore) {
                        bestMove = new int[]{i, j};
                        bestScore = score;

                    }
                    break;
                }
            }
        }

        if (allScoresAreEqual(allScores)) {
            bestMove = SimpleMoves.simpleMove(board, latestMove, playerNumber);
        }

//        System.out.println("bestMove = " + Arrays.toString(bestMove)); // *** TEMP ***
//        System.out.println("All scores: " + Arrays.toString(allScores)); // *** TEMP ***

        board[bestMove[0]][bestMove[1]] = playerNumber;
        return bestMove;
    }

    public int minimax(int[][] board, int depth, int alpha, int beta, int[] coordinates, boolean isMax) {

        int score = endStateScore(board, depth, coordinates[0], coordinates[1], isMax);

        if (depth <= 0) {
            return score;
        }
        if (Board.boardFull(board)) {
            return 0;
        }
        if (score == depth + 1){
            return score;
        }
        if (score == - (depth + 1)){
            return score;
        }
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i <= 6; i++) {
                for (int j = 0; j <= 5; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = currentPlayer;
                        best = Math.max(
                                best,
                                minimax(
                                        board,
                                        depth - 1,
                                        alpha,
                                        beta,
                                        new int[] {i, j},
                                        false
                                ));
                        board[i][j] = 0;
                        break;
                    }
                }
                alpha = Math.max(alpha, best);
                if (beta <= alpha) {
                    break;
                }
            }
//            System.out.println("Maximise Best = " + best); // *** TEMP ***
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i <= 6; i++) {
                for (int j = 0; j <= 5; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = oppositionPlayer;
                        best = Math.min(
                                best,
                                minimax(
                                        board,
                                        depth - 1,
                                        alpha,
                                        beta,
                                        new int[] {i, j},
                                        true
                                ));
                        board[i][j] = 0;
                        break;
                    }
                }
                beta = Math.min(beta, best);
                if (beta <= alpha) {
                    break;
                }
            }
//            System.out.println("Minimise Best = " + best); // *** TEMP ***
            return best;
        }
    }

    public int endStateScore(int[][] board, int depth, int col, int row, boolean isMax) {
        int player = isMax ? oppositionPlayer : currentPlayer;

        if (player == currentPlayer) {
            if (Board.winningMove(board, col, row, player)) {
                return depth + 1;
            }
        }
        if (player == oppositionPlayer) {
            if (Board.winningMove(board, col, row, player)) {
                return - (depth + 1);
            }
        }
        return 0;
    }

    public boolean allScoresAreEqual(int[] scores) {
        int firstDigit = scores[0];
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] != firstDigit) {
                return false;
            }
        }
        return true;
    }
}
