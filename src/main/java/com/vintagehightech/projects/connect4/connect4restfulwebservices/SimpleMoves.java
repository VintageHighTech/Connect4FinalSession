package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SimpleMoves {

    static Random ran = new Random();

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
    static final int[] a2 = {3, 1, 2, 3, 2, 3, 4, 5};

    public static int bestFirstMove(int[][] board, int player) {
        int[][] initialBoard = player == 1 ? oppositionIsTwo : oppositionIsOne;

//        Board.terminalDisplayBoard(board); // ** TEMP FOR TESTING ***

        int[] bottomRow = new int[7];
        for (int i = 0; i < board.length; i++) {
            bottomRow[i] = board[i][0];
        }

//        System.out.println(Arrays.toString(bottomRow));
//        System.out.println(Arrays.toString(initialBoard[4]));

        int bestMove = -1;
        for (int i = 0; i < initialBoard.length ; i++) {
            if (Arrays.equals(initialBoard[i], bottomRow)) {
                bestMove = a2[i];
            }
        }
        return bestMove;
    }

    public static int[] nextBestMove(int[][] board, HashMap<List<Integer>, Integer> positions, int targetScore, int player) {
        int[] bestMove = {-1, -1};
        int column;
        int row;
        int tempScore;
        int bestScore = Integer.MIN_VALUE;

//        System.out.println("Scores from nextBestMove"); // *** TEMP ***
//        System.out.println("------------------------"); // *** TEMP ***

        for (var score : positions.entrySet()) {
            if (score.getValue() == targetScore) {
                column = score.getKey().get(0);
                row = score.getKey().get(1);

                if (Board.checkNeighbours(board, column, row, player == 1 ? 2 : 1))  {
//                    System.out.println("Returned best move = [" + column + ", " + row + "]"); // *** TEMP ***
                    return new int[] {column, row};
                }

                tempScore = Board.hasMostNeighbours(board, column, row, player == 1 ? 2 : 1);
//                System.out.printf("score: %d, move: %d,%d%n", tempScore, column, row); // *** TEMP ***

                if (tempScore > bestScore) {
                    bestMove = new int[] {column, row};
                    bestScore = tempScore;
                }
            }
        }
//        System.out.println("Returned best move = " + Arrays.toString(bestMove)); // *** TEMP ***
//        System.out.println("--------------------------"); // *** TEMP ***

        return bestMove;
    }
}
