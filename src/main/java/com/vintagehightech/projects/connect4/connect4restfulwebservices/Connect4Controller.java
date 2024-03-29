package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

/*
    The game controller uses HttpSessions to manage games. Each game session is assigned
    an ID. The frontend stores the session id as a cookie.
 */

@RestController
@CrossOrigin(origins = {"http://localhost:3000"}) // For the purposes of development on local machine
public class Connect4Controller {

    @Autowired
    private GameService newGameService;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @GetMapping(path = "/initial")
    public Connect4Game initialiseGame(HttpSession session) {
        session.setAttribute("game", GameActions.resetGame());
        return (Connect4Game) session.getAttribute("game");
    }

    @GetMapping(path = "/board")
    public Connect4Game returnGame(HttpSession session) {
        return (Connect4Game) session.getAttribute("game");
    }

    @GetMapping(path = "/terminate")
    public void terminateSession(HttpSession session) {
        session.invalidate();
    }

    /*
        startGame has been revised to check if the value of the stored attribute 'game' is null,
        i.e. if a session does not exist. If it does not, a fresh object of the Connect4Game is instantiated
        and the user (frontend) can start a new game without having to 'reset'.
     */
    @GetMapping(path = "/start/{playerOne}/{playerTwo}")
    public Connect4Game startGame(@PathVariable String playerOne,
                                  @PathVariable String playerTwo,
                                  HttpServletRequest request) {
        Connect4Game temp = new Connect4Game();
        if (request.getSession().getAttribute("game") != null) {
            temp = (Connect4Game) request.getSession().getAttribute("game");
        }
        Connect4Game resetTempBoard = newGameService.resetBoard(temp);
        request.getSession().setAttribute("game",
                newGameService.startNewGame(resetTempBoard,
                        Integer.parseInt(playerOne),
                        Integer.parseInt(playerTwo))
        );
        return (Connect4Game) request.getSession().getAttribute("game");
    }

    @GetMapping(path = "/move/{columnIndex}")
    public Connect4Game makeMove(@PathVariable int columnIndex, HttpServletRequest request) {
        Connect4Game temp = (Connect4Game) request.getSession().getAttribute("game");
        Connect4Game returnTemp = newGameService.makeMove(temp, columnIndex);
        request.getSession().setAttribute("game", returnTemp);
        return (Connect4Game) request.getSession().getAttribute("game");
    }

    @GetMapping(path = "/requestmove/{playerNumber}")
    public Connect4Game requestMove(@PathVariable int playerNumber, HttpServletRequest request) {
        Connect4Game returnTemp = newGameService.requestMove(
                (Connect4Game) request.getSession().getAttribute("game"),
                playerNumber);
        request.getSession().setAttribute("game", returnTemp);
        return (Connect4Game) request.getSession().getAttribute("game");
    }

    @GetMapping(path = "/reset")
    public Connect4Game resetGame(HttpServletRequest request) {
        request.getSession().setAttribute("game", newGameService.resetGame());
        return (Connect4Game) request.getSession().getAttribute("game");
    }
}
