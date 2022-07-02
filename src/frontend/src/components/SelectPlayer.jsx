import React from 'react';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

export default function SelectPlayer(props) {

  const handleChange = (event) => {
      let revisePlayer = {...props.player};
      revisePlayer.playerType = event.target.value;
      props.handleChoice(revisePlayer);
    };

  return (
    <>
      <FormControl>
        <Select
            sx={{
              backgroundColor: props.player.playerNumber === 1 ? '#ffdf33' : '#ff3333',
              fontWeight: 600,
              fontSize: 15,
              maxWidth: "135px",
              maxHeight: "30px",
              minWidth: "135px",
              minHeight: "30px"
            }}
            disabled={props.disabled}
            labelId="player1-label"
            id="player1-select"
            value={props.player.playerType}
            // label={props.player}
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
      </FormControl>
    </>
  )
};

