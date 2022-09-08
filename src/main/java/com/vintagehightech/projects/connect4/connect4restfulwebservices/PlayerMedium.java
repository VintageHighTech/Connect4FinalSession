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

        boolean successfulMove = false;
        int columnIndex = -1;
        int[] strategicMove = Board.potentialWin(board, playerNumber);
        if (strategicMove[0] != -1) {
            board[strategicMove[0]][strategicMove[1]] = playerNumber;
            return strategicMove;
        }
        while (!successfulMove) { // this could just be "while(true)" - successfulMove is never used
            columnIndex = ran.nextInt(7); // Selects any random column
            for (int i = 0; i <= 5; i++) {
                if(board[columnIndex][i] == 0) {
                    board[columnIndex][i] = playerNumber;
                    successfulMove = true;
//                    System.out.println("Success: column: " + columnIndex + ", row " + i);
                    return new int[] {columnIndex, i};
                }
            }
        }
        return new int[] {-1, -1}; /* This return statement is never executed. There will always
                                        be a 'successful move', because this method will never
                                        be called if the board is already full. Consider refactoring
                                        so there's a single (reachable) return statement at the end,
                                        rather than two conditional returns.
                                    */
    }
}
