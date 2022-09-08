package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Arrays;

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
    static final int[] a2 = {3, 1, 2, 3, 2, 3, 4, 5};

    public static int bestFirstMove(int[][] board, int player) {
        int[][] initialBoard = player == 1 ? oppositionIsTwo : oppositionIsOne;

//        Board.terminalDisplayBoard(board); // ** TEMP FOR TESTING ***

        int[] bottomRow = new int[7];
        for (int i = 0; i < board.length; i++) {
            bottomRow[i] = board[i][0];
        }

        System.out.println(Arrays.toString(bottomRow));
        System.out.println(Arrays.toString(initialBoard[4]));

        int bestMove = -1;
        for (int i = 0; i < initialBoard.length ; i++) {
            if (Arrays.equals(initialBoard[i], bottomRow)) {
                bestMove = a2[i];
            }
        }
        return bestMove;
    }

    public static int[] simpleMove(int[][] board, int[] latestMove, int playerNumber) {

        if (latestMove[1] < 5 && board[latestMove[0]][latestMove[1] + 1] == 0) {
            return new int[] {latestMove[0], latestMove[1] + 1};
        }

        int left = latestMove[0] - 1;
        int right = latestMove[0] + 1;

        while (true) {

            if (left >= 0) {
                for (int i = 0; i <= 5; i++) {
                    if (board[left][i] == 0) {
                        return new int[]{left, i};
                    }
                }
            }
            left--;

            if (right <= 6) {
                for (int i = 0; i <= 5; i++) {
                    if (board[right][i] == 0) {
                        return new int[]{right, i};
                    }
                }
            }
            right++;
        }
    }
}
