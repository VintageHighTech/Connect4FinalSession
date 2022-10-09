import React from 'react';
import Button from '@mui/material/Button';
import {ThemeProvider} from '@mui/material/styles';
import {discTheme} from '../muiStyleElements';

function Disc(props) {

    let discColor = 0;

    switch (props.color) {
        case 0:
            discColor = "info";
            break;
        case 1:
            discColor = "player1";
            break;
        case 2:
            discColor = "player2";
            break;
        case 3:
            discColor = "player1Win";
            break;
        case 4:
            discColor = "player2Win";
            break;
    }

    return (
        <ThemeProvider theme={discTheme}>
            <Button
                onClick={props.select}
                variant="contained"
                color={discColor}
                style={{
                    margin: "3px",
                    maxWidth: "45px",
                    maxHeight: "45px",
                    minWidth: "39px",
                    minHeight: "39px",
                    transition: 'none'
                }}
                sx={{
                    borderRadius: 10,
                }}
            >
            </Button>
        </ThemeProvider>
    )
}

export default Disc