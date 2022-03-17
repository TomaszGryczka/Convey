import React, { Component } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

import "./LoginForm.css";
import BasicForm from "../BasicForm/BasicForm.jsx";

class LoginForm extends Component {
  state = {};
  render() {
    return (
      <div className="Login">
        <div className="text-center m-4">
          <h1>Welcome to Convey!</h1>
          <h4>Please sign in with your credentials.</h4>
        </div>
        <Form>
          <BasicForm />

          <div class="text-center">
            <Button className="m-3" variant="primary">
              Login
            </Button>
          </div>
        </Form>
      </div>
    );
  }
}

export default LoginForm;
