import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(

  // Strict mode disabled to prevent double render for useEffect in App.js
      <App />
);

reportWebVitals(console.log);
