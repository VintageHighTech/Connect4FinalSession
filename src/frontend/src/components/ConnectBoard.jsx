import React from 'react';
import {Box, Grid, Typography} from '@mui/material';
import Disc from './disc';

export default function ConnectBoard(props) {

    function boardColumn(column, cIndex) {
        return (
            <Grid container direction="column-reverse">
                {column.map((piece, index) => {
                    return (
                        <Grid item key={index}>
                            <Disc color={piece} select={() => props.MakeMove(cIndex)}/>
                        </Grid>
                    )
                })}
            </Grid>
        )
    }

    return (
        <>
            <Typography fontFamily="Ubuntu"
                        fontSize={38} fontWeight="700"
                        color="#00a4c1"
                        marginBottom="10px"
            >
                Connect 4
            </Typography>
            <Box
                justifyContent="center"
                alignItems="center"
                alignContent="center"
                borderRadius={6}
                padding="2px"
                sx={{
                    align: 'center',
                    backgroundColor: '#425859'
                }}
            >
                <Grid container direction="row" justifyContent="center" rowSpacing={3}>
                    {props.boardStatus.board.map((column, index) => {
                        return (
                            <Grid item xs={1.7} key={index}>
                                {boardColumn(column, index)}
                            </Grid>
                        )
                    })}
                </Grid>
            </Box>
        </>
    );
}
