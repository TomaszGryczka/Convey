import React, { useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Link, useNavigate } from "react-router-dom";

import AlertInfo from "../Registration/RegistrationAlert";

import "./Login.css";

import { signIn } from "../../Util/Util";

const Login = (props) => {

  const navigate = useNavigate();

  const loginAlert = React.createRef();

  useEffect(() => {
    if(loginAlert.current === undefined) {
      return;
    }

    signedUp();

  }, [loginAlert.current])
  

  const handleSubmit = event => {
    event.preventDefault();

    if(validateLogin(event.target.username.value, event.target.password.value)) {
      localStorage.clear();
      loginUser(event.target.username, event.target.password);
    }
  }

  const loginUser = (username, password) => {

    signIn({
        username: username.value,
        password: password.value,
    }).then((response) => {
          showRegistrationAlert("success", "User logged in!", "");
          localStorage.setItem("accessToken", response.token);
          navigate("/chat");
        }).catch((error) => {
          if(error.status === 401) {
            showRegistrationAlert("danger", "Error!", "Incorrect username or password!");
          } else {
            showRegistrationAlert("danger", "Error!", "Something went wrong.");
          }
      });
  }

  const validateLogin = (username, password) => {
    if(username.trim() === "") {
      showRegistrationAlert("danger", "Error!", "Empty username!");
      return false;
    } else if (password.trim() === "") {
      showRegistrationAlert("danger", "Error!", "Empty password!");
      return false;
    }

    return true;
  }

  const showRegistrationAlert = (variant, heading, message) => {
    loginAlert.current.setVariant(variant);
    loginAlert.current.setHeading(heading);
    loginAlert.current.setMessage(message);
    loginAlert.current.setVisible(true);
  }

  const signedUp = () => {
    const success = localStorage.getItem("success");
    if(success) {
      showRegistrationAlert("success", "User registered!", "You can now log in with your credentials.");
    }

    localStorage.removeItem("success");
  }

  return (
    <div className="Login">
      <div className="text-center m-4">
        <h1>Welcome to Convey!</h1>
        <h4>Please sign in with your credentials.</h4>
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
        
        <div className="text-center">
          <Button className="m-3" variant="primary" type="submit">
            Log in
          </Button>
        </div>

        <div className="text-center">
          <Link to={"/register"}>
            Sign up for an account
          </Link>
        </div>
      </Form>

      <AlertInfo ref={loginAlert}/>
    </div>
  );
}

export default Login;