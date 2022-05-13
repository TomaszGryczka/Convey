import React, { Component } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useNavigate } from "react-router-dom";

import "./Registration.css";
import { signUp } from "../../Util/Util";
import "./RegistrationAlert";
import RegistrationAlert from "./RegistrationAlert";

class Registration extends Component {
  constructor(props) {
    super(props);
    this.registrationAlert = React.createRef();
  }

  handleSubmit = event => {
    localStorage.clear();
    event.preventDefault();
    this.registerUser(event.target.username, event.target.password, event.target.email);
  }

  registerUser(username, password, email) {

    const navigate = this.props;

    signUp({
        username: username.value,
        password: password.value,
        email: email.value,
    }).then(() => {
        //this.showRegistrationAlert("success", "User registered!", "You can now log in with your credentials.");
        const success = ["success", "User registered!", "You can now log in with your credentials."];
        navigate.navigate("/login", success);
        window.location.reload();
    }).catch((error) => {
      if(error.status === 409) {
        this.showRegistrationAlert("danger", "User already exists!", "Please choose different name.");
      } else {
        this.showRegistrationAlert("danger", "User not registered!", "Please try again later.");
      }
    });
  }

  showRegistrationAlert(variant, heading, message) {
    this.registrationAlert.current.setVariant(variant);
    this.registrationAlert.current.setHeading(heading);
    this.registrationAlert.current.setMessage(message);
    this.registrationAlert.current.setVisible(true);
  }

  render() {
      return (
          <div className="Registration">
            <div className="text-center m-4">
              <h1>Sign Up for Convey!</h1>
            </div>
            
            <Form onSubmit={this.handleSubmit}>
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
            </Form>

            <RegistrationAlert ref={this.registrationAlert}/>
          </div>
        );
  }
  
}

export default function(props) {
  const navigate = useNavigate();

  return <Registration {...props} navigate={navigate} />;
}
