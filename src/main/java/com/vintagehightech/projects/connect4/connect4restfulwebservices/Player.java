package com.vintagehightech.projects.connect4.connect4restfulwebservices;

public interface Player {

    /*
        The three difficulty levels - Easy, Medium & Hard - implement this interface.
        Each level has its own implementation of makeMove.
     */

    public int[] makeMove(int[][] board, int[] latestMove, int playerNumber);

}
