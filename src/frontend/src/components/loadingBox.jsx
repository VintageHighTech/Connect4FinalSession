import * as React from 'react';
import LoadingButton from '@mui/lab/LoadingButton';
import Box from '@mui/material/Box';
import Dialog from '@mui/material/Dialog';

export default function LoadingBox(props) {

    return(
        <Dialog
            // open={props.open}
            open={props.open}
            // onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <LoadingButton
                size="large"
                // onClick={handleClick}
                loading={true}
                variant="contained"
                disabled
            >
                disabled
            </LoadingButton>
        </Dialog>
    )
}