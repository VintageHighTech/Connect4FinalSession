import './App.css';
import React, { useState, useEffect } from 'react';
import ConnectBoard from './components/ConnectBoard';
import GameMenu from './components/gameMenu';
import Grid from '@mui/material/Grid';
import RetrieveBlankBoard from './components/RetrieveBlankBoard';
import Connect4Service from './api/Connect4Service';
import Button from '@mui/material/Button' // ** TEMP ** to test requestMove from virtual player

function App() {
  // Time to tidy up!!
  const defaultPlayerOne = {
    playerNumber: 1,
    playerType: -1
  }
  const defaultPlayerTwo = {
    playerNumber: 2,
    playerType: -1
  }

  const [boardStatus, setBoardStatus] = useState(RetrieveBlankBoard());
  const [displayStatus, setDisplayStatus] = useState("Select Players to start game. Yellow goes first.");
  const [playerOne, setPlayerOne] = useState(defaultPlayerOne);
  const [playerTwo, setPlayerTwo] = useState(defaultPlayerTwo);
  const [resetEnabled, setResetEnabled] = useState(true);

  // *** NEW variables to be used in place of boardStatus variables
  let playerOneType = -1;
  let playerTwoType = -1;
  let inProgress = false;
  let currentPlayer = 1;
  /// ***

  //useEffect resets the game to default values.
  useEffect(() => {
    console.log(playerOne); // TEMP
    Connect4Service.initialiseGame()
    .then(
      response => {
        setBoardStatus(response.data)
        console.log('From useEffect(): ' + response.data)
      }
    )
  }, []);

  function StartGame() {
    Connect4Service.startGame(playerOne.playerType, playerTwo.playerType)
      .then(
        response => {
          console.log("Start Game returned data", response.data)
          updateFrontEnd(response.data)
          setResetEnabled(false)
          backEndMakeMove();
          // showLocalStatus(); // *** TEMP ***
        }
      )
  };

  function showLocalStatus() { // ** TEMP to check backend is successfully revising frontend local variales **
    console.log('PlayerOneType: ', playerOneType);
    console.log('PlayerTwoType: ', playerTwoType);
    console.log('InProgress: ', inProgress);
    console.log('Current player is: ', currentPlayer);
  };

  function backEndMakeMove() {
    if (currentPlayer === 1 && playerOneType !== 0) {
      requestMove(1);
    };
    if (currentPlayer === 2 && playerTwoType !== 0) {
      requestMove(2);
    };
  };
  
  function updateFrontEnd(data) {
    setBoardStatus(data);
    playerOneType = data.playerOneType;
    playerTwoType = data.playerTwoType;
    currentPlayer = data.currentPlayer;
    inProgress = data.inProgress;
  };

  function MakeMove(columnIndex){
    Connect4Service.makeMove(columnIndex)
    .then(
      response => {
        updateFrontEnd(response.data)
        if (inProgress) {
          backEndMakeMove();
          // showLocalStatus(); // *** TEMP ***
        }  
        if (response.data.error != null) {
          setDisplayStatus(response.data.error)
        } else if (boardStatus.inProgress) {
          setDisplayStatus(response.data.currentPlayer === 1 ? "Yellow's move." : "Red's move.");
        }
      }
    )
  };

  function requestMove(playerNumber) {
    // console.log('Request Move, player : ', playerNumber);
    Connect4Service.requestMove(playerNumber)
    .then(
      response => {
        updateFrontEnd(response.data)
        if (inProgress) {
          backEndMakeMove();
          // showLocalStatus(); // *** TEMP ***
        };
        if (response.data.error != null) {
          setDisplayStatus(response.data.error)
        } else if (boardStatus.inProgress) {
          setDisplayStatus(response.data.currentPlayer === 1 ? "Yellow's move." : "Red's move.");
        }
      }
    )
  };

  function ResetGame() {
    Connect4Service.resetGame()
    .then(
      response => {
        setBoardStatus(response.data)
        setPlayerOne(defaultPlayerOne);
        setPlayerTwo(defaultPlayerTwo);
        setDisplayStatus("Select Players to start game. Yellow goes first.");
        setResetEnabled(true);
      }
    )
  };

  return (  
    <>
      <Grid alignItems='center' container direction='column' alignContent='center' justifyContent='center'>
        <ConnectBoard
          boardStatus={boardStatus}
          setBoardStatus={setBoardStatus}
          MakeMove={MakeMove}
        />
        <p>{displayStatus}</p>
        <GameMenu
          boardStatus={boardStatus}
          resetGame={ResetGame}
          startGame={StartGame}
          setPlayerOne={setPlayerOne}
          setPlayerTwo={setPlayerTwo}
          playerOne={playerOne}
          playerTwo={playerTwo}
          inProgress={boardStatus.inProgress}
          // inProgress={inProgress}
          resetEnabled={resetEnabled}
        />
      </Grid>
      {/* <Button onClick={() => requestMove(currentPlayer)}>Request Move</Button> */}
    </>
  );
};

export default App;
