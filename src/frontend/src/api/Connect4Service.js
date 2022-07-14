import axios from "axios";

axios.defaults.withCredentials = true

class Connect4Service {

  initialiseGame() {
    console.log('game initialised by frontend');
    return axios.get('initial');
  };

  retrieveBoard() {
    return axios.get('board');
  };

  startGame(playerOne, playerTwo) {
    return axios.get(`start/${playerOne}/${playerTwo}`);
  };

  makeMove(columnIndex) {
    return axios.get(`move/${columnIndex}`);
  };

  requestMove(playerNumber) {
    return axios.get(`requestmove/${playerNumber}`);
  };

  resetGame() {
    return axios.get(`reset`);
  };

  endSession() {
    axios.get('terminate');
  };

}

export default new Connect4Service();