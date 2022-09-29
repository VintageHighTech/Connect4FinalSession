package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    public Connect4Game startNewGame(Connect4Game game, int p1, int p2) {
        Connect4Game returnGame = GameActions.setPlayers(game, p1, p2);
        game.setMessage("Orange's move.");
        game.setInProgress(true);
        return returnGame;
    }

    public Connect4Game makeMove(Connect4Game game, int columnIndex) {
        return GameActions.moveMade(game, columnIndex);
    }

    public Connect4Game resetGame() {
       return GameActions.resetGame();
    }

    public Connect4Game requestMove(Connect4Game game, int playerNumber) {
        return GameActions.requestMove(game, playerNumber);
    }

    public Connect4Game resetBoard(Connect4Game game) {
        return GameActions.resetBoard(game);
    }
}
