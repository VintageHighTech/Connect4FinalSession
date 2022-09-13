import { createTheme } from '@mui/material/styles';

export const buttonSize = {
  minWidth: "145px",
  maxWidth: "145px",
  maxHeight: "40px",
  minHeight: "40px",
  fontSize: 17
};

export const startButtonTheme = createTheme({
  palette: {
    
    secondary: {
      main: '#cc2d2d',
    },
    primary: {
      main: '#b8a437',
    },
    action: {
      disabledBackground: '#838584',
      disabled: 'black'
    }
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: buttonSize
      }
    }
  }
});

// NOTE: Colours changed to new UI theme
/*
blue: #00a4c1
blue win: #60fafc
orange: #ff652f
orange win: #ffb82f
board: #425859
background: #1f2833
*/

export const discTheme = createTheme({
  palette: {
    info: {
      main: '#282c34'
    },
    player1: {
      main: '#ff652f',
    },
    player2:{
      main: '#00a4c1'
    },
    player1Win: {
      main: '#ffb82f',
    },
    player2Win:{
      main: '#60fafc',
    }
  }
});

export const selectButtonTheme = createTheme({
  components: {
    MuiSelect: {
      styleOverrides: {
        root: buttonSize
      }
    }
  }
});

