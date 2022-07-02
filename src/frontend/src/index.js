import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// import 'bootstrap/dist/css/bootstrap.css';
// Material UI: npm install @mui/material @emotion/react @emotion/styled
// Roboto Font: npm install @fontsource/roboto

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // Strict mode disabled to prevent double render for useEffect in App.js
  // <React.StrictMode>
      <App />
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
