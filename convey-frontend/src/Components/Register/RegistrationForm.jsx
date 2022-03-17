import React, { Component } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

import BasicForm from "../BasicForm/BasicForm";
import "./RegistrationForm.css";

class RegistrationForm extends Component {
  handleSubmit = (event) => {
    event.preventDefault();

    this.registerUser(event.target.username.value, event.target.password.value);
  };

  registerUser(username, password) {
    fetch("http://localhost:8080/users", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    });
  }

  render() {
    return (
      <div className="Registration">
        <div className="text-center m-4">
          <h1>Sign Up for Convey!</h1>
        </div>

        <Form onSubmit={this.handleSubmit}>
          <BasicForm />

          <div className="text-center">
            <Button className="m-3" variant="primary" type="submit">
              Register
            </Button>
          </div>
        </Form>
      </div>
    );
  }
}

export default RegistrationForm;
