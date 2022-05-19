import React, {Component} from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useNavigate } from "react-router-dom";

import RegistrationAlert from "../Registration/RegistrationAlert";

import "./Login.css";

import { signIn } from "../../Util/Util";

class Login extends Component {
  constructor(props) {
    super(props);
    this.registrationAlert = React.createRef();
  }

  handleSubmit = event => {
    event.preventDefault();
    localStorage.clear();
    this.loginUser(event.target.username, event.target.password);
  }

  loginUser(username, password) {

    const navigate = this.props;

    signIn({
        username: username.value,
        password: password.value,
    }).then((response) => {
          this.showRegistrationAlert("success", "User logged in!", "");
          localStorage.setItem("accessToken", response.token);
          navigate.navigate("/chat");
        }).catch((error) => {
          if(error.status === 401) {
            this.showRegistrationAlert("danger", "Error!", "Incorrect username or password!");
          } else {
            this.showRegistrationAlert("danger", "Error!", "Something went wrong.");
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
      <div className="Login">
        <div className="text-center m-4">
          <h1>Welcome to Convey!</h1>
          <h4>Please sign in with your credentials.</h4>
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
          
          <div className="text-center">
            <Button className="m-3" variant="primary" type="submit">
              Log in
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

  return <Login {...props} navigate={navigate} />;
}