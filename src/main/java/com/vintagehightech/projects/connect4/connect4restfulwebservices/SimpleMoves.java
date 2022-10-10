package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SimpleMoves {

    static final int[][] oppositionIsOne = {
    //       0, 1, 2, 3, 4, 5, 6
            {0, 0, 0, 0, 0, 0, 0}, //0
            {1, 0, 0, 0, 0, 0, 0}, //1
            {0, 1, 0, 0, 0, 0, 0}, //2
            {0, 0, 1, 0, 0, 0, 0}, //3
            {0, 0, 0, 1, 0, 0, 0}, //4
            {0, 0, 0, 0, 1, 0, 0}, //5
            {0, 0, 0, 0, 0, 1, 0}, //6
            {0, 0, 0, 0, 0, 0, 1}, //7

    };

    static final int[][] oppositionIsTwo = {
    //       0, 1, 2, 3, 4, 5, 6
            {0, 0, 0, 0, 0, 0, 0}, //0
            {2, 0, 0, 0, 0, 0, 0}, //1
            {0, 2, 0, 0, 0, 0, 0}, //2
            {0, 0, 2, 0, 0, 0, 0}, //3
            {0, 0, 0, 2, 0, 0, 0}, //4
            {0, 0, 0, 0, 2, 0, 0}, //5
            {0, 0, 0, 0, 0, 2, 0}, //6
            {0, 0, 0, 0, 0, 0, 2}, //7

    };

    static final int[] firstMoves = {3, 1, 2, 3, 2, 3, 4, 5};

    /*
        bestFirstMove looks at the bottom row of the board and determines the best first move
        to make, whether the board is empty or a single move has already been made. The 2D arrays
        oppositionIsOne & oppositionIsTwo contain integer arrays that represent every possible
        state of the first row. The index of each 'row' in the 2D array corresponds to the index
        of the best move to make in the firstMoves array.
     */

    public static int bestFirstMove(int[][] board, int player) {
        int[][] initialBoard = player == 1 ? oppositionIsTwo : oppositionIsOne;

        int[] bottomRow = new int[7];
        for (int i = 0; i < board.length; i++) {
            bottomRow[i] = board[i][0];
        }
        int bestMove = -1;
        for (int i = 0; i < initialBoard.length ; i++) {
            if (Arrays.equals(initialBoard[i], bottomRow)) {
                bestMove = firstMoves[i];
            }
        }
        return bestMove;
    }

    /*
        nextBestMove firstly uses the checkNeighbour method to establish if a blocking move
        should be made. If not, it uses the hasMostNeighbours method to make a move in the
        position has the greatest number of adjacent opposition pieces.
     */

    public static int[] nextBestMove(int[][] board, HashMap<List<Integer>, Integer> positions, int targetScore, int player) {
        int[] bestMove = {-1, -1};
        int column;
        int row;
        int tempScore;
        int bestScore = Integer.MIN_VALUE;

        for (var score : positions.entrySet()) {
            if (score.getValue() == targetScore) {
                column = score.getKey().get(0);
                row = score.getKey().get(1);

                if (Board.checkNeighbours(board, column, row, player == 1 ? 2 : 1))  {
                    return new int[] {column, row};
                }

                tempScore = Board.hasMostNeighbours(board, column, row, player == 1 ? 2 : 1);

                if (tempScore > bestScore) {
                    bestMove = new int[] {column, row};
                    bestScore = tempScore;
                }
            }
        }
        return bestMove;
    }
}
