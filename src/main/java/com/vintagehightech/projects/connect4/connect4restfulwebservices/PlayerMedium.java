package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import java.util.Random;

public class PlayerMedium implements Player {
    Random ran = new Random();
    boolean firstMove = true;

    public int[] makeMove(int[][] board, int[] latestMove, int playerNumber) {
//        System.out.println("Medium Player");
        if (firstMove) {
            int column = SimpleMoves.bestFirstMove(board, playerNumber);
            firstMove = false;
            board[column][0] = playerNumber;
            return new int[] {column, 0};
        }

        int columnIndex = -1;
        int[] strategicMove = Board.potentialWinOrBlock(board, playerNumber);
        if (strategicMove[0] != -1) {
            board[strategicMove[0]][strategicMove[1]] = playerNumber;
            return strategicMove;
        }

        while (true) {
            columnIndex = ran.nextInt(7); // Selects any random column
            for (int i = 0; i <= 5; i++) {
                if(board[columnIndex][i] == 0) {
                    board[columnIndex][i] = playerNumber;
//                    System.out.println("Success: column: " + columnIndex + ", row " + i);
                    return new int[] {columnIndex, i};
                }
            }
        }
    }
}
