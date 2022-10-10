package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

    /*
        The Hard player comprises a combination of the MiniMax algorithm and blocking moves.
        MiniMax looks at all potential moves up to eight moves ahead of the current board. For
        this reason, several potential moves may return the same value,
        i.e. the moves are equally good. In this case, all potential good moves are passed
        to the nextBestMove method of the SimpleMoves class.
     */

public class PlayerHard implements Player {
    int currentPlayer;
    int oppositionPlayer;
    final int depthTarget = 8;
    boolean firstMove = true;
    HashMap<List<Integer>, Integer> positions = new HashMap<>(7);



    public int[] makeMove(int[][] board, int[] latestMove, int playerNumber) {
        if (firstMove) {
            int column = SimpleMoves.bestFirstMove(board, playerNumber);
            firstMove = false;
            board[column][0] = playerNumber;
            return new int[] {column, 0};
        }

        // *** Check board for potential win or potential block move:
        int[] checkWinOrBlock = Board.potentialWinOrBlock(board, playerNumber);
        if (checkWinOrBlock[0] != -1) {
            board[checkWinOrBlock[0]][checkWinOrBlock[1]] = playerNumber;
            return checkWinOrBlock;
        }

        /*
        Set global variables for current player and opposition to avoid
        passing values between methods.
          */
        this.currentPlayer = playerNumber;
        this.oppositionPlayer = playerNumber == 1 ? 2 : 1;


        int[][] tempBoard = Arrays.copyOf(board, board.length);

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

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
                    tempBoard[i][j] = 0;
                    List<Integer> temp = new ArrayList<>(2);
                    temp.add(i);
                    temp.add(j);
                    positions.put(temp, score);
                    if (score > bestScore) {
                        bestScore = score;
                    }
                    break;
                }
            }
        }

        bestMove = SimpleMoves.nextBestMove(board, positions, bestScore, playerNumber);
        positions.clear();
        board[bestMove[0]][bestMove[1]] = playerNumber;
        return bestMove;
    }

    public int minimax(int[][] board, int depth, int alpha, int beta, int[] coordinates, boolean isMax) {

        int score = endStateScore(board, depth, coordinates[0], coordinates[1], isMax);

        if (depth <= 0) {
            return score;
        }
        if (Board.boardIsFull(board)) {
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
            return best;
        }
    }

    public int endStateScore(int[][] board, int depth, int col, int row, boolean isMax) {
        int player = isMax ? oppositionPlayer : currentPlayer;

        if (player == currentPlayer) {
            if (Board.isWinningMove(board, col, row, player, false)) {
                return depth + 1;
            }
        }
        if (player == oppositionPlayer) {
            if (Board.isWinningMove(board, col, row, player, false)) {
                return - (depth + 1);
            }
        }
        return 0;
    }
}
