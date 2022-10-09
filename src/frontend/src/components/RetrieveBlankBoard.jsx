export default function RetrieveBlankBoard () {
  return (
    {
      board: [
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0]
      ],
      playerOneType: -1,
      playerTwoType: -1,
      currentPlayer: 0,
      gameOver: false,
      inProgress: true,
      message: null
    }
  )
} 