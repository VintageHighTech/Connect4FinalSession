import * as React from 'react';
import LoadingButton from '@mui/lab/LoadingButton';
import Box from '@mui/material/Box';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import CircularProgress from '@mui/material/CircularProgress';

export default function LoadingBox(props) {

    return(
        <Dialog
            open={props.open}
        >
            <Box
                className="App"
                display="flex"
                alignItems="center"
                alignContent="center"
                sx={{
                align: 'center',
                width: 150,
                height:150,
                backgroundColor: "#425859"

            }}>
                <CircularProgress color='primary'/>
            </Box>
        </Dialog>


    )
}