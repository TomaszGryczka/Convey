import React from 'react';
import { BrowserRouter as Router, Navigate, Route, Routes } from 'react-router-dom';
import Chat from './Components/Chat/Chat.jsx';
import Login from "./Components/Login/Login.jsx";
import Registration from './Components/Registration/Registration.jsx';
import ProtectedRoute from './Components/ProtectedRoute.jsx';
import { RecoilRoot } from 'recoil';
import { recoilPersist } from 'recoil-persist';
import PasswordChange from './Components/PasswordChange/PasswordChange.jsx';

const { persistAtom } = recoilPersist({
  key: "recoil-persist",
  storage: sessionStorage,
});

const App = (props) => {
  return (
    <RecoilRoot>
      <Router>
          <Routes>
              <Route element={<ProtectedRoute/>}>
                <Route exact path="/chat" element={<Chat/>} />
              </Route>
              <Route exact path="/password" element={<PasswordChange/>}/>
              <Route exact path='/login' element={<Login/>}/>
              <Route exact path='/register' element={<Registration/>}/>
              <Route exact path="/*" element={<Navigate to="/login"/>}/>
              <Route element={<ProtectedRoute/>}>
                <Route exact path="/user" element={<Chat authed={true}/>} />
              </Route>
          </Routes>
      </Router>
    </RecoilRoot>  
  );
}

export default App;