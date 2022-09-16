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

setLoadingDelay(setter) {
  let loadingDelay = setTimeout(() => {
    setter(true);
  }, 750);
  return () => {clearTimeout(loadingDelay)};
}

initialiseGame() {
    // this.setLoadingDelay(); // Have to add the setter argument to the function call
    console.log('game initialised by frontend');
    return axios.get('initial', {retry: 5, retryDelay: 3000});
  };

  retrieveBoard() {
    return axios.get('board');
  };

  startGame(playerOne, playerTwo) {
    // this.setLoadingDelay(); // Have to add the setter argument to the function call
    return axios.put(`start/${playerOne}/${playerTwo}`);
  };

  makeMove(columnIndex) {
    return axios.put(`move/${columnIndex}`);
  };

  requestMove = (playerNumber, setter) => {
    this.setLoadingDelay(setter);
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