import React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import Button from '@mui/material/Button';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

export default function InstructionDialog(props) {

    const handleClose = () => {
        props.handleDialog();
    };

    return (
        <div>
            <Dialog
                open={props.open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {"HOW TO PLAY"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Select a menu option for player one (orange) and player two (blue).
                        <br/><br/>
                        To play against the computer, select 'Human' as either blue or orange,
                        then select one of the difficulty levels - Easy, Medium or Hard -
                        as your opponent. You can watch the computer play against itself by
                        selecting a difficulty level for both orange & blue.
                        <br/><br/>
                        Select 'START' to play. Select 'RESET' to interrupt the game.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>
                        CLOSE
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
