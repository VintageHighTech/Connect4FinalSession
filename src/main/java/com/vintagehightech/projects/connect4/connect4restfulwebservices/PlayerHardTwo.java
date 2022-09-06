package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import java.util.Arrays;

public class PlayerHardTwo {

    int player;
    int oppPlayer;

    public int[] makeMove(int[][] board, int playerNumber) {
        System.out.println("Hard Level Move");
        this.player = playerNumber;
        this.oppPlayer = playerNumber == 1 ? 2 : 1;
        int[][] tempBoard = Arrays.copyOf(board, board.length);
//        Board.terminalDisplayBoard(tempBoard);

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 5; j++) {
                if (tempBoard[i][j] == 0) {
                    tempBoard[i][j] = playerNumber;
                    int score = minimax(tempBoard, 6, new int[]{i, j}, false);
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

    public int minimax(int[][] board, int depth, int[] coordinates, boolean isMax) {
        int score = checkScore(board, coordinates[0], coordinates[1], isMax);
        if (depth <= 0) {
            return score;
        }
        if (Board.boardFull(board)) {
            return 0;
        }
        if (score == 10 || score == -10 || score == 20 || score == -20) {
            return score;
        }

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i <= 6; i++) {
                for (int j = 0; j <= 5; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = player;
                        best = Math.max(best, minimax(board, depth - 1, new int[]{i, j}, false));

//                        Board.terminalDisplayBoard(board); // ******** TEMP
//                        System.out.println("Score for Max = " + checkScore(board, i, j, true)); // TEMP
//                        System.out.println("Score for Min = " + checkScore(board, i, j, false)); // TEMP

                        board[i][j] = 0;
                        break;
                    }
                }
            }
//            System.out.println("Maximise Best = " + best);
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i <= 6; i++) {
                for (int j = 0; j <= 5; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = oppPlayer;
                        best = Math.min(best, minimax(board, depth - 1, new int[]{i, j}, true));

//                        Board.terminalDisplayBoard(board); // ******** TEMP
//                        System.out.println("Score for Max = " + checkScore(board, i, j, true)); // TEMP
//                        System.out.println("Score for Min = " + checkScore(board, i, j, false)); // TEMP

                        board[i][j] = 0;
                        break;
                    }
                }
            }
//            System.out.println("Minimise Best = " + best);
            return best;
        }

//        return 0; // **** TEMP *****
    }
    //
    public int checkScore(int[][] board, int col, int row, boolean isMax) {
        if (Board.winningMove(board, col, row, isMax ? player : oppPlayer)) {
            return 20;
        }
        if (Board.winningMove(board, col, row, isMax ? oppPlayer : player)) {
            return -20;
        }
        if (Board.checkMove(board, col, row, isMax ? player : oppPlayer)) {
            return 10;
        }
        if (Board.checkMove(board, col, row, isMax ? oppPlayer : player)) {
            return -10;
        }
        return 0; // Should be 0. Temp set to 5 so I can see when this method returns this statement.
    }
//    public int checkScore(int[][] board, int col, int row, boolean isMax) {
//        if (Board.winningMove(board, col, row, player)) {
//            return 20;
//        }
//        if (Board.checkMove(board, col, row, player)) {
//            return 10;
//        }
//        if (Board.winningMove(board, col, row, oppPlayer)) {
//            return -20;
//        }
//        if (Board.checkMove(board, col, row, oppPlayer)) {
//            return -10;
//        }
//        return 0; // Should be 0. Temp set to 5 so I can see when this method returns this statement.
//    }
}
