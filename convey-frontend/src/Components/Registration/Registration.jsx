import React, { Component } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

import "./Registration.css";

class Registration extends Component {

    handleSubmit = event => {
        event.preventDefault();
        this.registerUser(event.target.username, event.target.password, event.target.email);
    }

    registerUser(username, password, email) {
        fetch('http://localhost:8080/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username.value,
                password: password.value,
                email: email.value,
            })
        }).then(function(response) {
            if(response.status === 201) {
                console.log("User registered");
            } else {
                console.log("User not registered");
            }
        })
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
            </div>
          );
    }
  
}

export default Registration;
