// ONLY USED FOR TESTING before generating API calls to actual backend

let board = [ 
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0]
];

let player = true; // player 1 = true; player 2 = false;

export const updateBoard = (columnIndex) => {
    // console.log(columnIndex);
    let newColumn = board[columnIndex];
    for (let i = 0; i < newColumn.length; i++) {
        if (newColumn[i] === 0) {
            newColumn[i] = player ? 1 : 2;
            player = !player;
            break;
        }
    }
    board[columnIndex] = newColumn;
    return board;
};

export let retrieveBoard = () => {
    return board;
};

export const whosNext = () => {
    return player;
};

