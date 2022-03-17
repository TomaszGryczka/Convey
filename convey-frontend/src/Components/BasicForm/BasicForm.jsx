import React, { Component } from "react";
import Form from "react-bootstrap/Form";

class BasicForm extends Component {
  state = {};
  render() {
    return (
      <React.Fragment>
        <Form.Group controlId="username">
          <Form.Label>Username</Form.Label>
          <Form.Control
            autoFocus
            name="username"
            placeholder="Enter username"
          />
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            placeholder="Password"
          />
        </Form.Group>
      </React.Fragment>
    );
  }
}

export default BasicForm;
