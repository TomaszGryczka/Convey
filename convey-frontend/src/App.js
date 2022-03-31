import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Chat from './Components/Chat/Chat.jsx';
import LoginForm from "./Components/Login/LoginForm.jsx";


function App() {
  return (
    <>
      <Router>
          <Routes>
              <Route exact path="/chat" element={<Chat/>}/>
              <Route exact path='/login' element={<LoginForm/>}/>
          </Routes>
      </Router>
    </>  
  );
}

export default App;