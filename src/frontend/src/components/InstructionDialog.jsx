import * as React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import Button from '@mui/material/Button';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

export default function TimeoutDialog(props) {

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
          {"Game Session Timed Out"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            No moves made for 30 minutes. Game expired. Select 'RESET' to start a new game.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} autoFocus>
            RESET
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
