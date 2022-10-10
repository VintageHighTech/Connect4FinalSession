package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Random;

public class PlayerEasy implements Player {

    /*
        The Easy player will make make a predetermined "bestFirstMove" for its first move.
        It will then take its first opportunity to win or its first opportunity to block.
        All other moves are chosen at random.
     */

    Random ran = new Random();
    boolean firstMove = true;
    int blockOrWinCount = 0;

    public int[] makeMove(int[][] board, int[] latestMove, int playerNumber) {
        if (firstMove) {
            int column = SimpleMoves.bestFirstMove(board, playerNumber);
            firstMove = false;
            board[column][0] = playerNumber;
            return new int[]{column, 0};
        }

        int columnIndex = -1;

        if (blockOrWinCount < 1) {
            int[] strategicMove = Board.potentialWinOrBlock(board, playerNumber);
            if (strategicMove[0] != -1) {
                board[strategicMove[0]][strategicMove[1]] = playerNumber;
                blockOrWinCount++;
                return strategicMove;
            }
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
