package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import java.util.Arrays;

public class Board {
    // This is just a reference class - no instances will be made

    public static boolean winningMove(int[][] board, int col,int row, int player) {

        /*
          The below generates a 2D array. Each line of the array represents the state of the
          board for the horizontal, vertical & diagonals centred around the last move made.
        */
        int[][] win = new int[4][7];
        int x = - 3;
        for (int i = 0; i < 7; i++) {
            win[0][i] = row + x >= 0 && row + x < 6 ? board[col][row + x] : 0;
            win[1][i] = col + x >= 0 && col + x < 7 ? board[col + x][row] : 0;
            win[2][i] = row + x >= 0 && row + x < 6 && col + x >= 0 && col + x < 7 ? board[col + x][row + x] : 0;
            win[3][i] = row - x >= 0 && row - x < 6 && col + x >= 0 && col + x < 7 ? board[col + x][row - x] : 0;
            x++;
        }
//        terminalDisplay2dArray(win, "Checking if winning move"); // *** TEMP ***
        /*
        Below analyses each line of the 'win' 2D array to see if any line contains 4 consecutive discs
         */
        for (int[] line : win) {
            int winCount = 0;
            for (int disc : line) {
                if (disc == player) {
                    winCount++;
                    if (winCount == 4) {
                        return true;
                    }
                } else {
                    winCount = 0;
                }
            }
        }
        return false;
    }

    public static int[] potentialWin(int[][] board, int player) {
        int[] blockingMove = new int[] {-1, -1};
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == 0) {
                    if (checkMove(board, i, j, 3, player)) {
                        return new int[] {i, j};
                    } else {
                        if (checkMove(board, i, j, 3, player == 1 ? 2 : 1)) {
                           blockingMove = new int[] {i, j};
                        }
                    }
                    break;
                }
            }
        }
        return blockingMove;
    }

    public static boolean checkMove(int[][] board ,int col, int row, int targetCount, int player) {
        int[][] win = new int[4][7];
        int x = - 3;
        for (int i = 0; i < 7; i++) {
            win[0][i] = row + x >= 0 && row + x < 6 ? board[col][row + x] : 0;
            win[1][i] = col + x >= 0 && col + x < 7 ? board[col + x][row] : 0;
            win[2][i] = row + x >= 0 && row + x < 6 && col + x >= 0 && col + x < 7 ? board[col + x][row + x] : 0;
            win[3][i] = row - x >= 0 && row - x < 6 && col + x >= 0 && col + x < 7 ? board[col + x][row - x] : 0;
            x++;
        }
//        terminalDisplay2dArray(win, "Check for best move"); // *** TEMP ***
        /*
        Below analyses each line of the 'win' 2D array to see if there's a possible winning
        or blocking move
         */
        for (int[] line : win) {
            int winCount = 0;
            for (int i = 0; i < line.length; i++) {
                if (line[i] == player) {
                    winCount++;
                    if (winCount == targetCount) {
//                        System.out.println("Best move is: " + col); // *** TEMP ***
                        return true;
                    }
                } else if (i != 3){ // i.e. ignore the zero in the centre of each line.
                    winCount = 0;
                }
            }
        }
        return false;
    }

    public static boolean boardFull(int[][] board) {
        for (int[] column : board) {
            if (column[5] == 0) {
                return false;
            }
        }
        return true;
    }

    public static void terminalDisplay2dArray(int[][] data, String note) {
        System.out.println("..................");
        System.out.println("\n" + note);
        for (int[] line : data) {
            System.out.println(Arrays.toString(line));
        }
        System.out.println("..................");
    }

    public static void terminalDisplayBoard(int[][] board) {
        System.out.println("..................");
        for (int[] line : board) {
            System.out.println(Arrays.toString(line));
        }
        System.out.println("..................");
    }
}
