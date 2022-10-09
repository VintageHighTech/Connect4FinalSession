import React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Disc from './disc';

export default function ConnectBoard(props) {

    function boardColumn(column, cIndex) {
        return (
            <Grid container direction="column-reverse" style={{gap: 5}}>

                {column.map((piece, index) => {
                    return (
                        <Grid key={index}>
                            <Disc color={piece} select={() => props.MakeMove(cIndex)}/>
                        </Grid>
                    )
                })}
            </Grid>
        )
    }

    return (
        <>
            <h1>Connect 4</h1>
            <Box
                // width="95%"
                // height="95%"
                // minWidth="400px"
                // minHeight="400px"
                // maxWidth="500px"
                // maxHeight="500px"
                display="flex"
                justifyContent="center"
                alignItems="center"
                alignContent="center"
                borderRadius={7}
                sx={{
                    align: 'center',
                    width: 378,
                    height: 325,
                    backgroundColor: '#425859'
                }}
            >
                <Grid container direction="row" justifyContent="center" style={{gap: 5}}>
                    {props.boardStatus.board.map((column, index) => {
                        return (
                            <Grid key={index}>
                                {boardColumn(column, index)}
                            </Grid>
                        )
                    })}
                </Grid>
            </Box>
        </>
    )
};