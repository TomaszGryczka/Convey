import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import ScrollToBottom from "react-scroll-to-bottom";
import { useRecoilState, useRecoilValue } from 'recoil';
import { chatActiveContact, loggedInUser } from '../../Atom/State';
import { countNewMessages, getUsers } from '../../Util/Util';

import "./Chat.css";

let stompClient = null;

const Chat = (props) =>  {

  const currentUser = useRecoilValue(loggedInUser);

  const [contacts, setContacts] = useState([]);

  const [activeContact, setActiveContact] = useRecoilState(chatActiveContact);



  useEffect(() => {
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

  const loadContacts = () => {
    const promise = getUsers().then((users) => {
      users.map((user) => {
        countNewMessages(user.id, currentUser.id)
        .then((numOfMessages) => {
          user.newMessages = numOfMessages;
          return user;
        })
      })
    });

    promise.then((promises) => {
      Promise.all(promises).then((users) => {
        setContacts(users);
        if(activeContact === undefined && users.length > 0) {
          setActiveContact(users[0]);
        }
      })
    })
  }


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