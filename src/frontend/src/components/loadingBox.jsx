import * as React from 'react';
import Box from '@mui/material/Box';
import Dialog from '@mui/material/Dialog';
import CircularProgress from '@mui/material/CircularProgress';

export default function LoadingBox(props) {

    return(
        <Dialog
            open={props.open}
        >
            <Box
                display="flex"
                sx={{
                    alignItems: "center",
                    justifyItems: "center",
                    justifyContent: "center",
                    width: 150,
                    height: 150,
                    backgroundColor: "#425859"
            }}>
                <CircularProgress color='primary'/>
            </Box>
        </Dialog>
    )
}