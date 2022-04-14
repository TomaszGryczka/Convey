import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Chat from './Components/Chat/Chat.jsx';
import Login from "./Components/Login/Login.jsx";
import Registration from './Components/Registration/Registration.jsx';


function App() {
  return (
    <>
      <Router>
          <Routes>
              <Route exact path="/chat" element={<Chat/>}/>
              <Route exact path='/login' element={<Login/>}/>
              <Route exact path='/register' element={<Registration/>}/>
          </Routes>
      </Router>
    </>  
  );
}

export default App;