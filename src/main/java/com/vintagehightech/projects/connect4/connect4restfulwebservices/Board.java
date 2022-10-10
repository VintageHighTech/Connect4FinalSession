package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import java.util.Arrays;

public class Board {

    /*
    This class only contains methods to process the board e.g. determine if the last move was a winning move.
     */

    public static boolean isWinningMove(int[][] board, int col, int row, int player, boolean saveWin) {
        int winCount = 0;
        int[][] win = verticalHorizontalDiagonals(board, col, row);
        /*
        The below analyses each line of the 'win' 2D array to see if any line contains 4 consecutive discs
         */
        for (int i = 0; i < win.length; i++) {
            winCount = 0;
            for (int j = 0; j < win[0].length; j++) {
                if (win[i][j] == player) {
                    winCount++;
                    if (winCount == 4) {
                        if (saveWin) {
                            displayWin(board, win[i], i, new int[]{col, row}, player);
                        }
                        return true;
                    }
                } else {
                    winCount = 0;
                }
            }
        }
        return false;
    }

    /*
        Checks all possible next moves to determine if the game can be won. If not, it then checks
        if a potential winning move by the opponent can be blocked.
     */
    public static int[] potentialWinOrBlock(int[][] board, int player) {
        int[] blockingMove = new int[]{-1, -1};
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == 0) {
                    if (checkMove(board, i, j, 3, player)) {
                        return new int[]{i, j};
                    } else {
                        if (checkMove(board, i, j, 3, player == 1 ? 2 : 1)) {
                            blockingMove = new int[]{i, j};
                        }
                    }
                    break;
                }
            }
        }
        return blockingMove;
    }

    public static boolean checkMove(int[][] board, int col, int row, int targetCount, int player) {
        int[][] win = verticalHorizontalDiagonals(board, col, row);
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
                        return true;
                    }
                } else if (i != 3) { // i.e. ignore the zero in the centre of each line.
                    winCount = 0;
                }
            }
        }
        return false;
    }

    /*
        Checks the value of positions (player one or two) around a given position. If there
        are at least two opposition pieces adjacent to the empty position, make a move
        in that position to block a potential future winning move.
     */

    public static boolean checkNeighbours(int[][] board, int col, int row, int player) {
        int[][] lines = verticalHorizontalDiagonals(board, col, row);
        for (int[] line : lines) {
            int count = 0;
            for (int i = 2; i > 0; i--) {
                if (line[i] != player) {
                    break;
                }
                count++;
            }
            for (int i = 4; i < 6; i++) {
                if (line[i] != player) {
                    break;
                }
                count++;
            }
            if (count >= 2) {
                return true;
            }
        }
        return false;
    }

    /*
      The below generates a 2D array. Each line of the array represents the state of the
      board for the horizontal, vertical & diagonals centred around the last move made. Other
      methods can use this to help easily determine a winning move.
    */
    public static int[][] verticalHorizontalDiagonals(int[][] board, int col, int row) {
        int[][] lines = new int[4][7];
        int x = -3;
        for (int i = 0; i < 7; i++) {
            lines[0][i] = row + x >= 0 && row + x < 6 ? board[col][row + x] : 0;
            lines[1][i] = col + x >= 0 && col + x < 7 ? board[col + x][row] : 0;
            lines[2][i] = row + x >= 0 && row + x < 6 && col + x >= 0 && col + x < 7 ? board[col + x][row + x] : 0;
            lines[3][i] = row - x >= 0 && row - x < 6 && col + x >= 0 && col + x < 7 ? board[col + x][row - x] : 0;
            x++;
        }
        return lines;
    }

    /*
        The 'Hard' player uses this method to determine which position on the board for a potential
        next move has the greatest number of opposition discs surrounding it. This is used
        to determine the best blocking move.
     */
    public static int hasMostNeighbours(int[][] board, int col, int row, int player) {
        int count = 0;
        if (col - 1 >= 0 && row + 1 <= 5 && board[col - 1][row + 1] == player) {
            count++;
        }
        if (col - 1 >= 0 && board[col - 1][row] == player) {
            count++;
        }
        if (col - 1 >= 0 && row - 1 >= 0 && board[col - 1][row - 1] == player) {
            count++;
        }
        if (row - 1 >= 0 && board[col][row - 1] == player) {
            count++;
        }
        if (col + 1 <= 6 && row - 1 >= 0 && board[col + 1][row - 1] == player) {
            count++;
        }
        if (col + 1 <= 6 && board[col + 1][row] == player) {
            count++;
        }
        if (col + 1 <= 6 && row + 1 <= 5 && board[col + 1][row + 1] == player) {
            count++;
        }
        return count;
    }

    // Used to determine if the board is full and therefore the game is over without a win.
    public static boolean boardIsFull(int[][] board) {
        for (int[] column : board) {
            if (column[5] == 0) {
                return false;
            }
        }
        return true;
    }

    /*
        When a winning move is made, this changes the value in the winning position to a 3
        for a winning move by player one, and a 4 for a winning move made by player two. The frontend
        translates these numbers into a change of color for each disc that makes up part of the
        winning move.
     */
    public static void displayWin(int[][] board, int[] winLine, int lineNum, int[] coordinates, int player) {
        int col = coordinates[0];
        int row = coordinates[1];
        int winPlayer = player == 1 ? 3 : 4;

        int[][] checkLine = new int[7][2];

        switch (lineNum) {
            case 0:
                checkLine = new int[][]{{0, -3}, {0, -2}, {0, -1}, {0, 0}, {0, 1}, {0, 2}, {0, 3}};
                break;
            case 1:
                checkLine = new int[][]{{-3, 0}, {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0}, {3, 0}};
                break;
            case 2:
                checkLine = new int[][]{{-3, -3}, {-2, -2}, {-1, -1}, {0, 0}, {1, 1}, {2, 2}, {3, 3}};
                break;
            case 3:
                checkLine = new int[][]{{-3, 3}, {-2, 2}, {-1, 1}, {0, 0}, {1, -1}, {2, -2}, {3, -3}};
                break;
        }

        board[col][row] = winPlayer;

        for (int i = 2; i >= 0; i--) {
            if (winLine[i] == player) {
                board[col + checkLine[i][0]][row + checkLine[i][1]] = winPlayer;
            } else {
                break;
            }
        }

        for (int i = 4; i < winLine.length; i++) {
            if (winLine[i] == player) {
                board[col + checkLine[i][0]][row + checkLine[i][1]] = winPlayer;
            } else {
                break;
            }
        }
    }

    // The below methods are for testing purposes only.

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
