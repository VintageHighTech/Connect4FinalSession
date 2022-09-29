import './App.css';
import React, {useState, useEffect} from 'react';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box'
import Connect4Service from './api/connect4Service';
import ConnectBoard from './components/connectBoard';
import GameMenu from './components/gameMenu';
import RetrieveBlankBoard from './components/retrieveBlankBoard';
import LostConnectionDialog from "./components/lostConnectionDialog";
import TimeoutDialog from './components/timeoutDialog'
import LoadingBox from "./components/loadingBox";
import animateDisc from "./components/animateDisc";

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
    const [enableBoard, setEnableBoard] = useState(false);
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

    //useEffect initialises the game (boardStatus) on first render with default values.
    useEffect(() => {
        console.log(playerOne); // TEMP
        Connect4Service.initialiseGame()
            .then(
                response => {
                    setDisplayLoading(false);
                    setBoardStatus(response.data);
                    setDisplayStatus(response.data.message);
                    console.log('From useEffect(): ' + response.data);
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    }, []);

    const expirationDelay = 30 * 60 * 1000; // 30 minutes until game times out.

    // useEffect terminates session after a predetermined period of inaction
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
                    // console.log("Start Game returned data", response.data); // *** TEMP ***
                    setDisplayStatus(response.data.message);
                    updateFrontEnd(response.data);
                    setResetEnabled(false);
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
            }, 1200)
            return () => {
                clearTimeout(loadingDelay)
            };
        } else {
            clearTimeout(loadingDelay);
            setDisplayLoading(false);
        }
    }

    function backEndMakeMove() {
        if (currentPlayer === 1 && playerOneType !== 0) {
            requestMove(1);
        }
        if (currentPlayer === 2 && playerTwoType !== 0) {
            requestMove(2);
        }
    }

    function updateFrontEnd(data) {
        setBoardStatus(data)
        playerOneType = data.playerOneType;
        playerTwoType = data.playerTwoType;
        currentPlayer = data.currentPlayer;
        inProgress = data.inProgress;
        setEnableBoard(false);
        if (currentPlayer === 1 && playerOneType === 0 && inProgress) {
            setEnableBoard(true);
        }
        if (currentPlayer === 2 && playerTwoType === 0 && inProgress) {
            setEnableBoard(true);
        }
    }

    function MakeMove(columnIndex) {
        if (!enableBoard) {
            return;
        }
        setLoadingDelay(true);
        setEnableBoard(false);
        Connect4Service.makeMove(columnIndex)
            .then(
                async response => {
                    setLoadingDelay(false);
                    await animateDisc(setBoardStatus, response.data)
                    updateFrontEnd(response.data)
                    if (inProgress) {
                        backEndMakeMove();
                    }
                    setDisplayStatus(response.data.message);
                }
            ).catch(function (error) {
                setOpenError(true);
                console.log(error);
        })
    }

    function requestMove(playerNumber) {
        setLoadingDelay(true);
        setEnableBoard(false);
        Connect4Service.requestMove(playerNumber, setDisplayLoading)
            .then(
                async response => {
                    setLoadingDelay(false);
                    await animateDisc(setBoardStatus, response.data);
                    updateFrontEnd(response.data)
                    if (inProgress) {
                        backEndMakeMove();
                    }
                    setDisplayStatus(response.data.message);
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    }

    function ResetGame() {
        inProgress = false;
        Connect4Service.resetGame()
            .then(
                response => {
                    setBoardStatus(response.data)
                    setPlayerOne(defaultPlayerOne);
                    setPlayerTwo(defaultPlayerTwo);
                    setDisplayStatus("Select players to start. Orange goes first.");
                    setResetEnabled(true);
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    }

    function HandleDialog() {
        setLoadingDelay(false);
        setOpenDialog(false);
        setOpenError(false);
        Connect4Service.initialiseGame()
            .then(
                response => {
                    updateFrontEnd(response.data)
                    ResetGame();
                }
            )
    }

    function showLocalStatus() { // ** TEMP to check backend successfully revised frontend local variables **
        console.log('playerOneType: ', playerOneType);
        console.log('playerTwoType: ', playerTwoType);
        console.log('inProgress: ', inProgress);
        console.log('current player is: ', currentPlayer);
    }

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
