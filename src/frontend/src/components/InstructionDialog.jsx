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
                    {"Connect 4"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Select one of the menu options for player one (orange) and player two (blue):
                        <br/><br/>
                        Human, Easy, Medium or Hard.
                        <br/><br/>
                        If you wish to play against the computer, select 'Human' as either blue or orange, then
                        select one of the difficulty levels as your opponent. You can, if you wish, watch the
                        computer play against itself by selecting a difficulty level for both orange & blue.
                        <br/><br/>
                        Press 'START' to play. Press 'RESET' to interrupt the game. Once the game is over, press
                        'START' to automatically play a new game with the same menu options.
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
