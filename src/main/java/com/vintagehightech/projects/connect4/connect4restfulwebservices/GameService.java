package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    /* When dealing with Service, rather than passing a Connect4Game object from the session
        attributes into each method, simply set the value of new4Game to be that of the object:
        GameService.new4Game = (Connect4Game) mySession.getAttribute("game");
        ****** BAD IDEA!! *******
     */

    // new4Game will eventually be redundant because Connect4Game(s) will be passed as arguments
    Connect4Game new4Game = new Connect4Game();

    public Connect4Game retrieveGame() {
        return new4Game;
    }

    public Connect4Game startNewGame(Connect4Game game, int p1, int p2) {
        game = GameActions.setPlayers(game, p1, p2);
        game.setInProgress(true); //Okay to use instance of Connect4Game because this is a setter
        return game;
    }

    public Connect4Game makeMove(Connect4Game game, int columnIndex) {
        game = GameActions.moveMade(game, columnIndex);
        return game;
    }

    public Connect4Game resetGame() {
       return GameActions.resetGame();
    }

    public Connect4Game requestMove(Connect4Game game, int playerNumber) {
        game = GameActions.requestMove(game, playerNumber);
        return game;
    }


}
