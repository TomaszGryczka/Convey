import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
import { loggedInUser } from '../../Atom/State'
import { getCurrentUser } from '../../Util/Util';
import { Card } from 'react-bootstrap';

const Profile = () => {

    const navigate = useNavigate();

    const [currentUser, setLoggedInUser] = userRecoilState(loggedInUser);

    useEffect(() => {
        loadCurrentUser();
    });

    const loadCurrentUser = () => {
        getCurrentUser().then((response) => {
            setIsUserLoggedIn(response);
        }).catch((error) => {
            console.log(error);
        });
    }

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