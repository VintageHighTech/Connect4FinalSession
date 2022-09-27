import axios from "axios";

axios.defaults.withCredentials = true

axios.interceptors.response.use(undefined, (err) => {
  const { config, message} = err;
  if (!config || !config.retry) {
    return Promise.reject(err);
  }
  // retry while Network timeout or Network Error
  if (!(message.includes("timeout") || message.includes("Network Error"))) {
    return Promise.reject(err);
  }
  config.retry -= 1;
  const delayRetryRequest = new Promise((resolve) => {
    setTimeout(() => {
      console.log("retry the request", config.url);
      resolve();
    }, config.retryDelay || 1000);
  });
  return delayRetryRequest.then(() => axios(config));
});

class Connect4Service {

initialiseGame() {
    console.log('game initialised by frontend');
    return axios.get('initial', {retry: 5, retryDelay: 3000});
  };

  retrieveBoard() {
    return axios.get('board');
  };

  startGame(playerOne, playerTwo) {
    return axios.get(`start/${playerOne}/${playerTwo}`, {retry: 5, retryDelay: 3000});
  };

  makeMove(columnIndex) {
    return axios.get(`move/${columnIndex}`, {retry: 5, retryDelay: 3000});
  };

  requestMove = (playerNumber) => {
    return axios.get(`requestmove/${playerNumber}`, {retry: 5, retryDelay: 3000});
  };

  resetGame() {
    return axios.get(`reset`);
  };

  endSession() {
    axios.get('terminate');
  };

}

export default new Connect4Service();

