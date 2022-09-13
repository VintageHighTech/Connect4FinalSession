import React from 'react';
import Button from '@mui/material/Button'
import SelectPlayer from './selectPlayer';
import Stack from '@mui/material/Stack';
import { ThemeProvider} from '@mui/material/styles';
import { startButtonTheme} from '../muiStyleElements';

export default function GameMenu (props) {

  return (
      <Stack
        marginTop={2}
        direction={{ xs: 'column', sm: 'row' }}
        spacing={{ xs: 2, sm: 2, md: 6 }}
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
            disabled={props.inProgress || props.playerOne.playerType === -1 || props.playerTwo.playerType === -1}
            onClick={() => props.startGame()}
            variant="contained"
            color="primary"
          >
            START
          </Button>
          <Button
            disabled={props.resetEnabled}
            onClick={() => props.resetGame()}
            variant="contained"
            color="secondary"
          >
            RESET
          </Button>
        </ThemeProvider>
      </Stack>
  )
};