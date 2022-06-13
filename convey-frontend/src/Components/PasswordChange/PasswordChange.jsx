import React from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Link, useNavigate } from "react-router-dom";

import "./PasswordChange.css";
import { changePassword } from "../../Util/Util";
import "../Registration/RegistrationAlert"
import AlertInfo from "../Registration/RegistrationAlert";

const PasswordChange = (props) => {
  const registrationAlert = React.createRef();
  const navigate = useNavigate();

  const handleSubmit = event => {
    event.preventDefault();

    if(validateChangePassword(event.target.username.value, event.target.oldPassword.value, event.target.newPassword.value)) {
      localStorage.clear();
      change(event.target.username.value, event.target.oldPassword.value, event.target.newPassword.value);
    }
  }

  const change = (username, oldPassword, newPassword) => {
    changePassword({
        username: username,
        oldPassword: oldPassword,
        newPassword: newPassword,
    }).then(() => {
        const success = true;
        localStorage.setItem("successPassword", success);
        navigate("/login");
    }).catch((error) => {
        showRegistrationAlert("danger", "Something went wrong!", "Please try again later.");
    });
  }

  const showRegistrationAlert = (variant, heading, message) => {
    registrationAlert.current.setVariant(variant);
    registrationAlert.current.setHeading(heading);
    registrationAlert.current.setMessage(message);
    registrationAlert.current.setVisible(true);
  }

  const validateChangePassword = (username, oldPassword, newPassword) => {
    if(username.trim() === "") {
      showRegistrationAlert("danger", "Error!", "Empty username!");
      return false;
    } else if (oldPassword.trim() === "") {
      showRegistrationAlert("danger", "Error!", "Empty old password!");
      return false;
    } else if(newPassword.trim() === "") {
      showRegistrationAlert("danger", "Error!", "Empty new password!");
      return false;
    }

    return true;
  }

  return (
    <div className="Registration">
      <div className="text-center m-4">
        <h1>Change Password</h1>
      </div>
      
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="username">
          <Form.Label>Username</Form.Label>
          <Form.Control
            autoFocus
            name="username"
            placeholder="Enter username"
          />
        </Form.Group>

        <Form.Group controlId="oldPassword">
          <Form.Label className="mt-4">Old Password</Form.Label>
          <Form.Control
            type="password"
            name="oldpassword"
            placeholder="Enter old password"
          />
        </Form.Group>

        <Form.Group controlId="newPassword">
          <Form.Label className="mt-4">New Password</Form.Label>
          <Form.Control
            type="password"
            name="newpassword"
            placeholder="Enter new password"
          />
        </Form.Group>
        
        <div className="text-center">
          <Button className="m-3" variant="primary" type="submit">
            Change Password
          </Button>
        </div>

        <div className="text-center">
          <Link to={"/chat"}>
          Go back to chat
          </Link>
        </div>
      </Form>

      <AlertInfo ref={registrationAlert}/>
    </div>
  );
}

export default PasswordChange;