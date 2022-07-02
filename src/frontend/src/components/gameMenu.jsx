import React from 'react';
import Button from '@mui/material/Button'
import SelectPlayer from './SelectPlayer';
import Stack from '@mui/material/Stack';
import { ThemeProvider, createTheme } from '@mui/material/styles';

export default function GameMenu (props) {

    const startButtonTheme = createTheme({
      palette: {
        action: {
          disabledBackground: '#838584',
          disabled: 'black'
        }
      }
    });

    return (
        <>
        <Stack
          marginTop={2}
          direction={{ xs: 'column', sm: 'row' }}
          spacing={{ xs: 1, sm: 2, md: 4 }}
          alignItems='center'
          justifyContent='center'
        >
          <SelectPlayer
            player={props.playerOne}
            handleChoice={props.setPlayerOne}
            disabled={props.inProgress}
          />
          <SelectPlayer
            player={props.playerTwo}
            handleChoice={props.setPlayerTwo}
            disabled={props.inProgress}
          />
          <ThemeProvider theme={startButtonTheme}>
            <Button
              disabled={!props.resetEnabled || props.playerOne.playerType === -1 || props.playerTwo.playerType === -1}
              onClick={() => props.startGame()}
              variant="contained"
              color="success"
              sx={{
                minWidth: "135px",
                maxWidth: "135px",
                maxHeight: "30px",
                minHeight: "30px"
              }}
            >
              START
            </Button>
            <Button
              disabled={props.resetEnabled}
              onClick={() => props.resetGame()}
              variant="contained"
              color="warning"
              sx={{
                minWidth: "135px",
                maxWidth: "135px",
                maxHeight: "30px",
                minHeight: "30px"
              }}
              >
                RESET
              </Button>
          </ThemeProvider>
        </Stack>
        {/* <p>Player One is {props.playerOne.playerType}</p> */}
        {/* <p>Player Two is {props.playerTwo.playerType}</p> */}
        </>
    )
};