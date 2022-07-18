package com.vintagehightech.projects.connect4.connect4restfulwebservices;
import java.util.Random;
import java.util.Arrays;

public class PlayerEasy implements Player {

    Random ran = new Random();

//    public int[] makeMove(int[][] board, int playerNumber) {
//        System.out.println("I'm Easy");
//        boolean successfulMove = false;
//        int i = 0;
//        int columnIndex = -1;
//        int[] strategicMove = Board.potentialWin(board, playerNumber);
//        System.out.println(Arrays.toString(strategicMove)); // **** TEMP ****
//        if (strategicMove[0] != -1) {
//            board[strategicMove[0]][strategicMove[1]] = playerNumber;
//            return strategicMove;
//        }
//        // ****** GOT IT *******
//        /*
//            If the final move is a 'strategicMove' i.e. either winning or blocking,
//            we do not check to see if the board is full. We only check if we make a
//            random move.
//            Maybe we should move the boardFull check out of this method and use it
//            wherever this method returns its result.
//         */
//
//        /* Maybe don't repeatedly pick random numbers from 0 to 6 until
//            you find an empty (zero) space. Keep a log of checked columns.
//            This is where it gets stuck on an infinite loop.
//        */
//        // Add new call to method Board.randomMove(board, playerNumber);
//        // Make more efficient- pick rndm number; check if top position is empty; if so, make move
//        while (!successfulMove) {
//            columnIndex = ran.nextInt(7); // Selects any random column
//            for (i = 0; i <= 5; i++) {
//                if(board[columnIndex][i] == 0) {
//                    board[columnIndex][i] = playerNumber;
//                    successfulMove = true;
//                    System.out.println("Success: column: " + columnIndex + ", row " + i);
//                    break;
//                }
//                if (!successfulMove) {
//                    System.out.println("Column " + columnIndex + "is full");
//                }
//            }
//        }
//        if (Board.boardFull(board)) {
//            return new int[] {-1, -1};
//        }
//        return new int[] {columnIndex, i};
//    }

    public int[] makeMove(int[][] board, int playerNumber) {
        System.out.println("I'm Easy");
        boolean successfulMove = false;
        int i = 0;
        int columnIndex = -1;
        int[] strategicMove = Board.potentialWin(board, playerNumber);
        System.out.println(Arrays.toString(strategicMove)); // **** TEMP ****
        if (strategicMove[0] != -1) {
            board[strategicMove[0]][strategicMove[1]] = playerNumber;
            return strategicMove;
        }
        while (!successfulMove) {
            columnIndex = ran.nextInt(7); // Selects any random column
            for (i = 0; i <= 5; i++) { // no need for i = 0; it's defined at top of method
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
