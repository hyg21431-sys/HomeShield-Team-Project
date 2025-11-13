// App.js
import React, { useState } from 'react';
import './App.css';
import Map from './Pages/Map';

function App() {
  const [showMap, setShowMap] = useState(false);

  return (
    <div className="App">
      {!showMap ? (
        <button onClick={() => setShowMap(true)}>
          지도 보기
        </button>
      ) : (
        <Map />
      )}
    </div>
  );
}

export default App;
