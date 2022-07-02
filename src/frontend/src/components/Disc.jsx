import React from 'react';
import Button from '@mui/material/Button';
import { createTheme, ThemeProvider } from '@mui/material/styles';

const discTheme = createTheme({
  palette: {
    info: {
      main: '#282c34'
    },
    primary: {
      main: '#ffdf33'
    },
    secondary:{
      main: '#ff3333'
    },
  }
});

function Disc(props) {
  let discColor = props.color === 0 ? "info" : props.color === 1 ? "primary" : "secondary";

  return (
    <ThemeProvider theme={discTheme}>
      <Button
        onClick={props.select}
        variant="contained"
        color={discColor}
        style={{
          maxWidth: "45px",
          maxHeight: "45px",
          minWidth: "45px",
          minHeight: "45px"
        }}
        sx={{
          borderRadius: 10,
        }}
      >
      </Button>
    </ThemeProvider>
  )
};

export default Disc