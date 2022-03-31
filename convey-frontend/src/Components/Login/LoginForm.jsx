import React from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useNavigate } from 'react-router-dom'

import "./LoginForm.css";

function LoginForm() {

  let navigate = useNavigate();

  return (
    <div className="Login">
      <div className="text-center m-4">
        <h1>Welcome to Convey!</h1>
        <h4>Please sign in with your credentials.</h4>
      </div>
      <Form>

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
        
        <div className="text-center">
          <Button className="m-3" variant="primary" onClick={() => {
            navigate("/chat");
          }}>
            Login
          </Button>
        </div>
      </Form>
    </div>
  );
}

export default LoginForm;
