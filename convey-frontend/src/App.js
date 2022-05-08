import React from 'react';
import { BrowserRouter as Router, Navigate, Route, Routes } from 'react-router-dom';
import Chat from './Components/Chat/Chat.jsx';
import Login from "./Components/Login/Login.jsx";
import Registration from './Components/Registration/Registration.jsx';
import ProtectedRoute from './Components/ProtectedRoute.jsx';

function App() {
  return (
    <>
      <Router>
          <Routes>
              <Route element={<ProtectedRoute/>}>
                <Route exact path="/chat" element={<Chat/>} />
              </Route>
              <Route exact path='/login' element={<Login/>}/>
              <Route exact path='/register' element={<Registration/>}/>
              <Route exact path="/*" element={<Navigate to="/login"/>}/>
          </Routes>
      </Router>
    </>  
  );
}

export default App;