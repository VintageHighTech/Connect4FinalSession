import './App.css';
import React, {useState, useEffect} from 'react';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box'
import Connect4Service from './api/connect4Service';
import ConnectBoard from './components/ConnectBoard';
import GameMenu from './components/GameMenu';
import RetrieveBlankBoard from './components/RetrieveBlankBoard';
import LostConnectionDialog from "./components/LostConnectionDialog";
import TimeoutDialog from './components/TimeoutDialog'
import LoadingBox from "./components/LoadingBox";
import InstructionDialog from "./components/InstructionDialog";
import AnimateDisc from "./components/AnimateDisc";

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
    const [displayInstruction, setDisplayInstruction] = useState(true);


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
        SetLoadingDelay(true)
        Connect4Service.startGame(playerOne.playerType, playerTwo.playerType)
            .then(
                response => {
                    SetLoadingDelay(false);
                    setDisplayStatus(response.data.message);
                    UpdateFrontEnd(response.data);
                    setResetEnabled(false);
                    BackEndMakeMove();
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        });
    }

    // **** Displays loading dialogue after a predetermined delay when response from server is slow ******
    function SetLoadingDelay(state) {
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

    /*
    If the current player is not 'Human', a call will automatically be made to the backend,
    requesting a move from an AI player
     */
    function BackEndMakeMove() {
        if (currentPlayer === 1 && playerOneType !== 0) {
            RequestMove(1);
        }
        if (currentPlayer === 2 && playerTwoType !== 0) {
            RequestMove(2);
        }
    }

    function UpdateFrontEnd(data) {
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

    /*
        MakeMove function is executed when a human player selects a disc on the board.
        The backend updates the board status and checks if the game is over before
        returning the updated board.
     */
    function MakeMove(columnIndex) {
        if (!enableBoard) {
            return;
        }
        SetLoadingDelay(true);
        setEnableBoard(false);
        Connect4Service.makeMove(columnIndex)
            .then(
                async response => {
                    SetLoadingDelay(false);
                    await AnimateDisc(setBoardStatus, response.data)
                    UpdateFrontEnd(response.data)
                    if (inProgress) {
                        BackEndMakeMove();
                    }
                    setDisplayStatus(response.data.message);
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    }

    /*
        RequestMove function is executed when the current player is not 'Human'. The function
        requests a move from the backend and updates the board when a move is made.
     */
    function RequestMove(playerNumber) {
        SetLoadingDelay(true);
        setEnableBoard(false);
        Connect4Service.requestMove(playerNumber, setDisplayLoading)
            .then(
                async response => {
                    SetLoadingDelay(false);
                    await AnimateDisc(setBoardStatus, response.data);
                    UpdateFrontEnd(response.data)
                    if (inProgress) {
                        BackEndMakeMove();
                    }
                    setDisplayStatus(response.data.message);
                }
            ).catch(function (error) {
            setOpenError(true);
            console.log(error);
        })
    }

    // ResetGame interrupts the game and resets all parameters to their default value.
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
        });
    }

    function HandleDialog() {
        SetLoadingDelay(false);
        setOpenDialog(false);
        setOpenError(false);
        Connect4Service.initialiseGame()
            .then(
                response => {
                    UpdateFrontEnd(response.data)
                    ResetGame();
                }
            )
    }

    // Temporary function to check backend successfully revised frontend local variables.
    function ShowLocalStatus() {
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
            <InstructionDialog
                open={displayInstruction}
                handleDialog={() => setDisplayInstruction(false)}
            />
            <LoadingBox
                open={displayLoading}
            />
        </Box>
    );
}

export default App;
