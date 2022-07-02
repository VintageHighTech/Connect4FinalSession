package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Random;
import java.util.Arrays;

public class PlayerEasy implements Player {

    Random ran = new Random();

    public int[] makeMove(int[][] board, int playerNumber) {
        System.out.println("I'm Easy");
        boolean successfulMove = false;
        int i = 0;
        int columnIndex = -1;
        int[] strategicMove = Board.potentialWin(board, playerNumber);
        System.out.println(Arrays.toString(strategicMove));
        if (strategicMove[0] != -1) {
            board[strategicMove[0]][strategicMove[1]] = playerNumber;
            return strategicMove;
        }
        while (!successfulMove) {
            if (Board.boardFull(board)) {
                return new int[] {-1, -1};
            }
            columnIndex = ran.nextInt(7); // Selects any random column
            for (i = 0; i <= 5; i++) {
                if(board[columnIndex][i] == 0) {
                    board[columnIndex][i] = playerNumber;
                    successfulMove = true;
                    System.out.println("Success: column: " + columnIndex + ", row " + i);
                    break;
                }
                if (!successfulMove) {
                    System.out.println("Column " + columnIndex + "is full");
                }
            }
        }
        return new int[] {columnIndex, i};
    }
}
