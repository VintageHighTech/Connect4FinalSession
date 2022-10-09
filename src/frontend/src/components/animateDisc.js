    export default async function animateDisc(updateBoard, data) {
        let column = data.latestMove[0];
        let row = data.latestMove[1];
        if(column === -1 || row === -1) {
            return;
        }
        let player = data.board[column][row];
        data.board[column][row] = 0;
        let count = 0;
        if (row < 5) {
            for (let i = 5; i >= row; i--) {
                await new Promise(resolve => setTimeout(resolve, 75));
                let tempBoard = [...data.board]
                if (i < 5) {
                    tempBoard[column][i + 1] = 0;
                }
                tempBoard[column][i] = player;
                updateBoard({
                    ...data,
                    board: tempBoard
                });
                count++;
            }
        }
        data.board[column][row] = player;
        updateBoard(data);
    }


