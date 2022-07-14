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

export const selectButtonTheme = createTheme({
  components: {
    MuiSelect: {
      styleOverrides: {
        root: buttonSize
      }
    }
  }
});

