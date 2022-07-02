package com.vintagehightech.testsforpattern;

public class Game {

    /*
    - An inherited class won't work; the player types won't be interchangeable.
    - An Interface works for all AI players with a makeMove() method with no arguments
    - A Human player based on an Interface has to pass a columnIndex as an argument to
      the makeMove() method.
    -

     */

    /*
    - Make an Interface with a makeMove() method with no arguments
    - the Human class that implements this interface 'gets' the co-ordinates
      from

      - A Human makes a move.
      - The co-ordinates are passed to the Connect4Service via Path Variables
      - The co-ordinates are stored in the Game class
      - The co-ordinates are retrieved from the Game class by the makeMove() method
        of the Human version of the Player interface
      - After the move is made, the co-ordinate variables in the Game class are nullified
      - The Human version of makeMove() would have to check if the co-ordinate values
        are null, if they are, don't do anything, then makeMove() is called again once
        the co-ordinate values have been set by the Connect4Service.

      CAN'T WORK OUT HWO TO SWITCH BETWEEN PLAYER AND WAIT FOR HUMAN INPUT

     */
}
