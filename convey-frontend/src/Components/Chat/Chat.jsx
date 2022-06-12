import React, { useEffect, useState } from "react";
import ScrollToBottom from "react-scroll-to-bottom";
import { useRecoilState } from "recoil";
import { Button, message } from "antd";
import {
  chatActiveContact,
  chatMessages,
  loggedInUser,
} from "../../Atom/State";
import {
  countNewMessages,
  findChatMessage,
  findChatMessages,
  getCurrentUser,
  getUsers,
} from "../../Util/Util";

import "./Chat.css";
import { useNavigate } from "react-router-dom";

let stompClient = null;

const Chat = (props) => {

  const navigate = useNavigate();

  const [currentUser, setloggedInUser] = useRecoilState(loggedInUser);

  const [contacts, setContacts] = useState([]);

  const [activeContact, setActiveContact] = useRecoilState(chatActiveContact);

  const [messages, setMessages] = useRecoilState(chatMessages);

  const [text, setText] = useState("");

  useEffect(() => {
    if(localStorage.getItem("accessToken") === null) {
      navigate("/login");
    }

    loadCurrentUser();
  }, []);

  useEffect(() => {
    if (currentUser.id === undefined) return;

    connect();
    loadContacts();
  }, [currentUser.id]);

  useEffect(() => {
    if (activeContact.id === undefined || currentUser.id === undefined) return;
    findChatMessages(activeContact.id, currentUser.id).then((messages) => {
      setMessages(messages);
    });

    loadContacts();
  }, [activeContact]);

  const connect = () => {
    const Stomp = require("stompjs");
    var SockJS = require("sockjs-client");
    SockJS = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(SockJS);
    stompClient.connect({}, onConnected, onError);
  };

  const loadCurrentUser = async () => {
    await getCurrentUser()
      .then((response) => {
        setloggedInUser(response);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const onConnected = () => {
    stompClient.subscribe(
      "/user/" + currentUser.id + "/queue/messages",
      onMessageReceived
    );
  };

  const onError = (err) => {
    console.log("Error connecting!");
  };

  const loadContacts = () => {
    getUsers().then((users) => {

      let copyOfUsers = [...users];

      copyOfUsers.map((user) => {
        countNewMessages(user.id, currentUser.id).then((numOfMessages) => {
          let contactCopy = structuredClone(user);
          contactCopy.newMessages = numOfMessages;

          return contactCopy;
        });
      
        setContacts(copyOfUsers);
        if (activeContact.id === undefined && copyOfUsers.length > 0 && currentUser.id !== undefined) {
          
          let contactCopy = structuredClone(users[0]);
          contactCopy.newMessages = 0;
          
          setActiveContact(copyOfUsers[0]);
       }
      
      });
      return copyOfUsers;
    })
  }

  const sendMessage = (msg) => {
    if(currentUser.id === undefined || activeContact.id === undefined) {
      return;
    }

    if (msg.trim() !== "") {
      const message = {
        senderId: currentUser.id,
        recipientId: activeContact.id,
        senderName: currentUser.username,
        recipientName: activeContact.username,
        content: msg,
        timestamp: new Date(),
      };

      stompClient.send("/app/chat", {}, JSON.stringify(message));

      const newMessages = [...messages];
      message.id = messages.length + 1;
      newMessages.push(message);
      setMessages(newMessages);
    }
  };

  const onMessageReceived = (msg) => {
    const notification = JSON.parse(msg.body);

    if (activeContact.id === notification.senderId) {
      findChatMessage(notification.id).then((message) => {
        const newMessages = [...messages];
        newMessages.push(message)
        setMessages(newMessages);
      });
    } else {
      message.info("Received new message from " + notification.senderName);
    }
    loadContacts();
  };

  return (
    <div id="frame">
      <div id="sidepanel">
        <div className="profile">
          <div className="wrap">
            <div id="profile-img" className="online">
            {(new String(currentUser.username)).charAt(0).toUpperCase()}
            </div>
            <p>{currentUser.username}</p>
            <div id="status-options">
              <ul>
                <li id="status-online" className="active">
                  <span className="status-circle"></span> <p>Online</p>
                </li>
                <li id="status-away">
                  <span className="status-circle"></span> <p>Away</p>
                </li>
                <li id="status-busy">
                  <span className="status-circle"></span> <p>Busy</p>
                </li>
                <li id="status-offline">
                  <span className="status-circle"></span> <p>Offline</p>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div id="search" />
        <div className="contacts">
          <ul>
            {contacts.map((contact) => {
              return (
                <li
                  key={contact.username}
                  onClick={() => {
                    setActiveContact(contact);
                  }}
                  className={
                    activeContact && contact.id === activeContact.id
                      ? "contact active"
                      : "contact"
                  }
                >
                  <div className="wrap">
                    <span className="contact-status online"></span>
                    <div id="profile-img">
                      {contact.username.charAt(0).toUpperCase()}
                    </div>
                    <div className="meta">
                      <p className="name">{contact.username}</p>
                      {activeContact.id !== contact.id && 
                      contact.newMessages !== undefined &&
                        contact.newMessages > 0 && (
                          <p className="preview">
                            <span>{contact.newMessages} new messages</span>
                          </p>
                        )}
                    </div>
                  </div>
                </li>
              );
            })}
          </ul>
        </div>
        <div id="bottom-bar">
          {/* <Button id="addcontact">
            <i className="fa fa-user fa-fw" aria-hidden="true"></i>{" "}
            <span>Profile</span>
          </Button> */}
          <Button id="settings">
            <i className="fa fa-cog fa-fw" aria-hidden="true"></i>{" "}
            <span>Settings</span>
          </Button>
        </div>
      </div>
      <div className="content">
        <div className="contact-profile">
          <div id="profile-img">
            {activeContact.username !== undefined && activeContact.username.charAt(0).toUpperCase()}
          </div>
          <p>{activeContact && activeContact.username}</p>
        </div>
        <ScrollToBottom className="messages">
          <ul>
            {messages.map((msg) => {
              return (
                <li
                  key={msg.id}
                  className={
                    msg.senderId == currentUser.id ? "sent" : "replies"
                  }
                >
                  <p>{msg.content}</p>
                </li>
              );
            })}
          </ul>
        </ScrollToBottom>
        <div className="message-input">
          <div className="wrap">
            <input
              name="user_input"
              size="large"
              placeholder="Write your message..."
              value={text}
              onClick={() => {
                let activeContactCopy = structuredClone(activeContact);
                activeContactCopy.newMessages = 0;
                setActiveContact(activeContactCopy);
              }}
              onChange={(event) => setText(event.target.value)}
              onKeyPress={(event) => {
                if (event.key === "Enter") {
                  sendMessage(text);
                  setText("");
                }
              }}
            />
            <Button
              className="bi bi-send-fill"
              onClick={() => {
                sendMessage(text);
                setText("");
              }}
            ></Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Chat;