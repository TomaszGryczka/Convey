import React from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Link, useNavigate } from "react-router-dom";

import RegistrationAlert from "../Registration/RegistrationAlert";

import "./Login.css";

import { signIn } from "../../Util/Util";

const Login = (props) => {

  const navigate = useNavigate();

  const registrationAlert = React.createRef();

  const handleSubmit = event => {
    event.preventDefault();
    localStorage.clear();
    loginUser(event.target.username, event.target.password);
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

  const showRegistrationAlert = (variant, heading, message) => {
    registrationAlert.current.setVariant(variant);
    registrationAlert.current.setHeading(heading);
    registrationAlert.current.setMessage(message);
    registrationAlert.current.setVisible(true);
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

      <RegistrationAlert ref={registrationAlert}/>
    </div>
  );
}

export default Login;