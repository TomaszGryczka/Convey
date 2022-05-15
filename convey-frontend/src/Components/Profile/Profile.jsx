import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
import { loggedInUser } from '../../Atom/State'
import { getCurrentUser } from '../../Util/Util';
import { Card } from 'react-bootstrap';
import { useRecoilState, useResetRecoilState } from 'recoil';

const Profile = (props) => {

    const navigate = useNavigate();

    const [currentUser, setloggedInUser] = useRecoilState(loggedInUser);

    useEffect(() => {
        loadCurrentUser();
    });

    const logout = () => {
        localStorage.removeItem("accessToken");
        navigate("/login");
    }

  // TODO: bootstrap card displays profile data
  return (
    <div className='profile-wrapper'>
        <div className='card'></div>
    </div>
  )
}

export default Profile