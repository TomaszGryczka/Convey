import React from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Link, useNavigate } from "react-router-dom";

import "./Registration.css";
import { signUp } from "../../Util/Util";
import "./RegistrationAlert";
import AlertInfo from "./RegistrationAlert";

const Registration = (props) => {
  const registrationAlert = React.createRef();
  const navigate = useNavigate();

  const handleSubmit = event => {
    event.preventDefault();

    if(validateRegister(event.target.username.value, event.target.password.value, event.target.email.value)) {
      localStorage.clear();
      registerUser(event.target.username, event.target.password, event.target.email);
    }
  }

  const registerUser = (username, password, email) => {
    signUp({
        username: username.value,
        password: password.value,
        email: email.value,
    }).then(() => {
        const success = true;
        localStorage.setItem("success", success);
        navigate("/login");
    }).catch((error) => {
      if(error.status === 409) {
        showRegistrationAlert("danger", "User already exists!", "Please choose different username.");
      } else {
        showRegistrationAlert("danger", "User not registered!", "Please try again later.");
      }
    });
  }

  const showRegistrationAlert = (variant, heading, message) => {
    registrationAlert.current.setVariant(variant);
    registrationAlert.current.setHeading(heading);
    registrationAlert.current.setMessage(message);
    registrationAlert.current.setVisible(true);
  }

  const hideRegistrationAlert = () => {
    registrationAlert.current.setVisible(false);
  }

  const handleConfirmPassword = (event) => {
    const password = document.getElementById("password").value;
    const passwordconfirm = document.getElementById("passwordconfirm").value;

    if(password !== passwordconfirm) {
      showRegistrationAlert("danger", undefined, "Passwords do not match.");
    } else {
      hideRegistrationAlert();
    }
  }

  const validateRegister = (username, password, email) => {
    if(username.trim() === "") {
      showRegistrationAlert("danger", "Error!", "Empty username!");
      return false;
    } else if (password.trim() === "") {
      showRegistrationAlert("danger", "Error!", "Empty password!");
      return false;
    } else if(!validateOnlyLettersAndNumbers(username)) {
      showRegistrationAlert("danger", "Incorrect username!", "Username should contain only letters and numbers!");
      return false;
    }

    return true;
  }

  const validateOnlyLettersAndNumbers = (stringToValidate) => {
    return /^[A-Za-z0-9]*$/.test(stringToValidate);
  }

  return (
    <div className="Registration">
      <div className="text-center m-4">
        <h1>Sign Up for Convey!</h1>
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

        <Form.Group controlId="password">
          <Form.Label className="mt-4">Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            placeholder="Enter password"
          />
        </Form.Group>

        <Form.Group controlId="passwordconfirm">
          <Form.Label className="mt-4">Confirm Password</Form.Label>
          <Form.Control
            type="password"
            name="passwordcofirm"
            placeholder="Enter password"
            onChange={handleConfirmPassword}
          />
        </Form.Group>

        <Form.Group controlId="email">
          <Form.Label className="mt-4">Email</Form.Label>
          <Form.Control
            type="email"
            name="email"
            placeholder="Enter email"
          />
        </Form.Group>
        
        <div className="text-center">
          <Button className="m-3" variant="primary" type="submit">
            Sign Up
          </Button>
        </div>

        <div className="text-center">
          <Link to={"/login"}>
          Already have an account? Log In
          </Link>
        </div>
      </Form>

      <AlertInfo ref={registrationAlert}/>
    </div>
  );
}

export default Registration;