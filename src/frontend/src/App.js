import './App.css';
import React, {useState, useEffect} from 'react';
import ConnectBoard from './components/connectBoard';
import GameMenu from './components/gameMenu';
import Grid from '@mui/material/Grid';
import RetrieveBlankBoard from './components/retrieveBlankBoard';
import Connect4Service from './api/connect4Service';
import LostConnectionDialog from "./components/lostConnectionDialog";
import TimeoutDialog from './components/timeoutDialog'
import LoadingBox from "./components/loadingBox";
import Box from '@mui/material/Box'

function App() {
    const defaultPlayerOne = {
        playerNumber: 1,
        playerType: -1
    }
    const defaultPlayerTwo = {
        playerNumber: 2,
        playerType: -1
    }

    const [boardStatus, setBoardStatus] = useState(RetrieveBlankBoard());
    const [displayStatus, setDisplayStatus] = useState("Loading Game...");
    const [playerOne, setPlayerOne] = useState(defaultPlayerOne);
    const [playerTwo, setPlayerTwo] = useState(defaultPlayerTwo);
    const [resetEnabled, setResetEnabled] = useState(true);
    const [openDialog, setOpenDialog] = useState(false);
    const [openError, setOpenError] = useState(false);
    const [displayLoading, setDisplayLoading] = useState(false);

    // *** NEW variables to be used in place of boardStatus variables
    let playerOneType = -1;
    let playerTwoType = -1;
    let inProgress = false;
    let currentPlayer = 1;
    /// ***

    let loadingDelay;

    //useEffect resets the game to default values.
    useEffect(() => {
        console.log(playerOne); // TEMP
        Connect4Service.initialiseGame()
            .then(
                response => {
                    setDisplayLoading(false)
                    setBoardStatus(response.data)
                    setDisplayStatus(response.data.error)
                    console.log('From useEffect(): ' + response.data)
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    }, []);

    const expirationDelay = 30 * 60 * 1000; //30 minutes until game times out.

    // UseEffect to terminate session after a predetermined period of inaction
    useEffect(() => {
        if (!openDialog) {
            let gameExpire = setTimeout(() => {
                setOpenDialog(true);
                Connect4Service.endSession();
            }, expirationDelay);
            return () => {
                clearTimeout(gameExpire)
            };
        }
    });

    function StartGame() {
        setLoadingDelay(true)
        Connect4Service.startGame(playerOne.playerType, playerTwo.playerType)
            .then(
                response => {
                    setLoadingDelay(false);
                    console.log("Start Game returned data", response.data)
                    updateFrontEnd(response.data)
                    setResetEnabled(false)
                    backEndMakeMove();
                    // showLocalStatus(); // *** TEMP ***
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    };

    // **** Displays loading dialogue after a predetermined delay when response from server is slow ******
    function setLoadingDelay(state) {
        if (state) {
                loadingDelay = setTimeout(() => {
                setDisplayLoading(true);
            }, 1000)
            return () => {
                clearTimeout(loadingDelay)
            };
        } else {
            clearTimeout(loadingDelay);
            setDisplayLoading(false);
        }
    }

    function showLocalStatus() { // ** TEMP to check backend is successfully revising frontend local variables **
        console.log('PlayerOneType: ', playerOneType);
        console.log('PlayerTwoType: ', playerTwoType);
        console.log('InProgress: ', inProgress);
        console.log('Current player is: ', currentPlayer);
    };

    function backEndMakeMove() {
        if (currentPlayer === 1 && playerOneType !== 0) {
            requestMove(1);
        }
        ;
        if (currentPlayer === 2 && playerTwoType !== 0) {
            requestMove(2);
        }
        ;
    };

    function updateFrontEnd(data) {
        setBoardStatus(data);
        playerOneType = data.playerOneType;
        playerTwoType = data.playerTwoType;
        currentPlayer = data.currentPlayer;
        inProgress = data.inProgress;
    };

    function MakeMove(columnIndex) {
        if (boardStatus.currentPlayer === 1 && boardStatus.playerOneType !== 0) {
            return;
        }
        if (boardStatus.currentPlayer === 2 && boardStatus.playerTwoType !== 0) {
            return;
        }
        setLoadingDelay(true)
        Connect4Service.makeMove(columnIndex)
            .then(
                response => {
                    setLoadingDelay(false)
                    updateFrontEnd(response.data)
                    if (inProgress) {
                        backEndMakeMove();
                        // showLocalStatus(); // *** TEMP ***
                    }
                    if (response.data.error != null) {
                        setDisplayStatus(response.data.error)
                    } else if (boardStatus.inProgress) {
                        setDisplayStatus(response.data.currentPlayer === 1 ? "Orange's move." : "Blues's move.");
                    }
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    };

    function requestMove(playerNumber) {
        setLoadingDelay(true)
        Connect4Service.requestMove(playerNumber, setDisplayLoading)
            .then(
                response => {
                    updateFrontEnd(response.data)
                    setLoadingDelay(false);
                    if (inProgress) {
                        backEndMakeMove();
                    }
                    ;
                    if (response.data.error != null) {
                        setDisplayStatus(response.data.error)
                    } else if (boardStatus.inProgress) {
                        setDisplayStatus(response.data.currentPlayer === 1 ? "Orange's move." : "Blues's move.");
                    }
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    }

    function ResetGame() {
        Connect4Service.resetGame()
            .then(
                response => {
                    setBoardStatus(response.data)
                    setPlayerOne(defaultPlayerOne);
                    setPlayerTwo(defaultPlayerTwo);
                    setDisplayStatus("Select Players to start. Orange goes first.");
                    setResetEnabled(true);
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    };

    function HandleDialog() {
        setLoadingDelay(false);
        setOpenDialog(false);
        setOpenError(false);
        Connect4Service.initialiseGame()
            .then(
                response => {
                    updateFrontEnd(response.data)
                    ResetGame();
                    console.log('From useEffect(): ' + response.data)
                }
            )
    };

    return (
        <Box margin={1}>
            <Grid
                alignItems='center'
                container direction='column'
                alignContent='center'
                justifyContent='center'
            >
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
            <TimeoutDialog
                open={openDialog}
                handleDialog={() => HandleDialog()}
            />
            <LostConnectionDialog
                open={openError}
                handleDialog={() => HandleDialog()}
            />
            <LoadingBox
                open={displayLoading}
            />
        </Box>
    );
}

export default App;
