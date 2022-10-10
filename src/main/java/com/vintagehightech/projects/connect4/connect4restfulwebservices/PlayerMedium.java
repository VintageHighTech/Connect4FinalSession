package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import java.util.Random;

    /*
        The Easy player will make a predetermined "bestFirstMove" for its first move.
        For each subsequent move it will first check if there is a potential winning move.
        If not, it will check for a potential blocking move. If neither move is applicable,
        it will choose a column at random.
     */

public class PlayerMedium implements Player {
    Random ran = new Random();
    boolean firstMove = true;

    public int[] makeMove(int[][] board, int[] latestMove, int playerNumber) {
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
                    return new int[] {columnIndex, i};
                }
            }
        }
    }
}
