import React from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

import "./Login.css";

function Login() {
  return (
    <div className="Login">
      <div className="text-center m-4">
        <h1>Welcome to Convey!</h1>
        <h4>Please sign in with your credentials.</h4>
      </div>
      
      <Form action="/chat">
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
    </div>
  );
}

export default Login;
