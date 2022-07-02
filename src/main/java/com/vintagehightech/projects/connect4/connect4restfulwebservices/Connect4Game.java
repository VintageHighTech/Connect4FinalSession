package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import org.springframework.stereotype.Component;

@Component
public class Connect4Game {

    int[][] board = new int[7][6];
    int playerOneType = -1;
    int playerTwoType = -1;
    int currentPlayer;
    boolean gameOver;
    boolean inProgress;
    String error;
    Player one;
    Player two;

    public Connect4Game() {
        currentPlayer = 1; // add this to whichever 'startGame' constructor you end up using
    }

    public int[][] getBoard() { // *** NOTE *** getBoard returns the int[][] board from class 'Board'
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getPlayerOneType() {
        return playerOneType;
    }

    public void setPlayerOneType(int playerOneType) {
        this.playerOneType = playerOneType;
    }

    public int getPlayerTwoType() {
        return playerTwoType;
    }

    public void setPlayerTwoType(int playerTwoType) {
        this.playerTwoType = playerTwoType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }
}
