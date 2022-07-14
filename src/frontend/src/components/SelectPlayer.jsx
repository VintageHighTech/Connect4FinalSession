import React from 'react';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { ThemeProvider } from '@emotion/react';
import { selectButtonTheme } from '../muiStyleElements';

export default function SelectPlayer(props) {

  const handleChange = (event) => {
      let revisePlayer = {...props.player};
      revisePlayer.playerType = event.target.value;
      props.handleChoice(revisePlayer);
    };

  return (
    <>
      <FormControl>
      <ThemeProvider theme={selectButtonTheme}>
        <Select
            sx={{
              backgroundColor: props.player.playerNumber === 1 ? '#ffdf33' : '#ff3333',
              fontWeight: 600,
              fontSize: 17,
            }}
            disabled={props.disabled}
            labelId="player1-label"
            id="player1-select"
            value={props.player.playerType}
            onChange={handleChange}
            renderValue={(selected) => {
              switch(selected) {
                case -1: return 'Select';
                case 0: return 'Human'; 
                case 1: return 'Easy';
                case 2: return 'Medium';
                case 3: return 'Unbeatable';
              }
            }}
        >   
            <MenuItem disabled value={-1}>
              <em>Select</em>
            </MenuItem>
            <MenuItem value={0}>Human</MenuItem>
            <MenuItem value={1}>Easy</MenuItem>
            <MenuItem disabled value={2}>Medium</MenuItem>
            <MenuItem disabled value={3}>Unbeatable</MenuItem>
        </Select>
        </ThemeProvider>
      </FormControl>
    </>
  )
};

