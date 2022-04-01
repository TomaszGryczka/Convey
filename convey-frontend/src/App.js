import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Chat from './Components/Chat/Chat.jsx';
import Login from "./Components/Login/Login.jsx";


function App() {
  return (
    <>
      <Router>
          <Routes>
              <Route exact path="/chat" element={<Chat/>}/>
              <Route exact path='/login' element={<Login/>}/>
          </Routes>
      </Router>
    </>  
  );
}

export default App;