package com.vintagehightech.projects.connect4.connect4restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

@RestController
// Don't think I need the below anymore: running on same port in single jar
@CrossOrigin(origins= {"http://localhost:3000", "https://c4frontend74-257ad.web.app" })
public class Connect4Controller {

    /*
    Destroying old sessions after time:
        - use invalidate()?
        - how to know session name/number?
            - could set a timer on frontend that then sends a 'terminate' call to backend

        - use a 'socket'
            - frontend then listens for an event from the backend


     */

    @Autowired
    private GameService newGameService;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @GetMapping(path = "/initial")
    public Connect4Game initialiseGame(HttpSession session, Connect4Game game) {
        game = GameActions.resetGame();
        session.setAttribute("game", game);
        System.out.println(session.getId()); // ** TEMP **
//        System.out.println("From initialiseGame: game = " + (Connect4Game) session.getAttribute("game")); // *** TEMP ***
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

    // THIS WORKS!! but do i need to parse the integers???
    @GetMapping(path = "/start/{playerOne}/{playerTwo}")
    public Connect4Game startGame(@PathVariable String playerOne,
                                  @PathVariable String playerTwo,
                                  HttpServletRequest request) {
//        System.out.println((Connect4Game) request.getSession().getAttribute("game")); // *** TEMP ***
        Connect4Game temp = (Connect4Game) request.getSession().getAttribute("game");
        Connect4Game resetTempBoard = newGameService.resetBoard(temp);
//        System.out.println("temp = " + temp); // *** TEMP ***
        // add returnTemp to reset board ONLY, not game.
        Connect4Game returnTemp = newGameService.startNewGame(resetTempBoard,
                Integer.parseInt(playerOne),
                Integer.parseInt(playerTwo));
        request.getSession().setAttribute("game", returnTemp);
        System.out.println(request.getSession().getId()); // ** TEMP **
        return (Connect4Game) request.getSession().getAttribute("game");
    }


    @GetMapping(path = "/move/{columnIndex}")
    public Connect4Game makeMove(@PathVariable int columnIndex, HttpServletRequest request) {
        Connect4Game temp = (Connect4Game) request.getSession().getAttribute("game");
        Connect4Game returnTemp = newGameService.makeMove(temp, columnIndex);
        request.getSession().setAttribute("game", returnTemp);
        System.out.println(request.getSession().getId()); // ** TEMP **
        return (Connect4Game) request.getSession().getAttribute("game");
    }

    @GetMapping(path = "/requestmove/{playerNumber}")
    public Connect4Game requestMove(@PathVariable int playerNumber, HttpServletRequest request) {
        Connect4Game temp = (Connect4Game) request.getSession().getAttribute("game");
        Connect4Game returnTemp = newGameService.requestMove(temp, playerNumber);
        request.getSession().setAttribute("game", returnTemp);
        System.out.println(request.getSession().getId()); // ** TEMP **
        return (Connect4Game) request.getSession().getAttribute("game");
    }

    @GetMapping(path = "/reset")
    public Connect4Game resetGame(HttpServletRequest request) {
        request.getSession().setAttribute("game", newGameService.resetGame());
        return (Connect4Game) request.getSession().getAttribute("game");
    }
}
