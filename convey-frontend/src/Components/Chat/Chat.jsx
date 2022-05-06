import React, { useEffect } from 'react';
import { Button } from 'react-bootstrap';
import ScrollToBottom from "react-scroll-to-bottom";

import "./Chat.css";

let stompClient = null;

const Chat = (props) =>  {

  useEffect(() => {
    if(false) {

    }

    connect();

  });

  const connect = () => {
    const Stomp = require("stompjs");
    var SockJS = require("sockjs-client");
    SockJS = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(SockJS);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    console.log("Connected!");
  };

  const onError = (err) => {
    console.log("Error connecting!");
  };


  return (
      <div id='frame'>
          <div id='sidepannel'>
              <div id='contacts'>
              </div>
              <div id='bottom-bar'>

              </div>
          </div>
          <div id='content'>
            <ScrollToBottom className='messages'>
              <ul>

              </ul>
            </ScrollToBottom>
            <div id='message-input'>
              <div className='wrap'>
              <input
                name="user_input"
                size="large"
                placeholder="Write your message..."
              />
              
                <Button className="bi bi-send-fill">
                    

                </Button>
              </div>

            </div>
          </div>
      </div>
  );
}

export default Chat;