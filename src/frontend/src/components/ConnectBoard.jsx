import React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Disc from './Disc';
import '../App.css';

export default function ConnectBoard(props) {

  function Column(column, cIndex) {
    return (
      <Grid container direction="column-reverse" style={{ gap: 5}}>
        {column.map((piece, index) => {
          return (
            <Grid key={index}>
              <Disc color={piece} select={() => props.MakeMove(cIndex)}/>
            </Grid>
          )
        })}
      </Grid>
    )
  };

  return (
    <>
      <h1>Connect 4</h1>
      <Box 
        className="App"
        display="flex"
        alignItems="center"
        alignContent="center"
        borderRadius={7}
        sx={{
          align: 'center',
          width: 378,
          height: 325,
          backgroundColor: '#0079ff'
        }}
      >
        <Grid container direction="row" justifyContent="center" style={{ gap: 5}}>
          {props.boardStatus.board.map((column, index) => { // was just 'board.map'
            return (
              <Grid key={index}>
                {Column(column, index)}
              </Grid>
            )
          })}
        </Grid>
      </Box>
    </>
  )
};
