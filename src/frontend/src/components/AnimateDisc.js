/*
    Animate disc uses the 'latestMove' parameter returned by the backend to animate the
    disc falling down the board before resting in its final position.
 */

export default async function AnimateDisc(updateBoard, data) {
    let column = data.latestMove[0];
    let row = data.latestMove[1];

    // If statement is used to avoid error caused by game being reset when a disc is being animated.
    if (column === -1 || row === -1) {
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


