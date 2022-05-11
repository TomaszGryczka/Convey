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
  findChatMessages,
  getCurrentUser,
  getUsers,
} from "../../Util/Util";

import "./Chat.css";

let stompClient = null;

const Chat = (props) => {
  const [currentUser, setloggedInUser] = useRecoilState(loggedInUser);

  const [contacts, setContacts] = useState([]);

  const [activeContact, setActiveContact] = useRecoilState(chatActiveContact);

  const [messages, setMessages] = useRecoilState(chatMessages);

  const [text, setText] = useState("");

  useEffect(() => {
    if (currentUser.id === undefined) return;

    connect();
    loadContacts();
  }, [currentUser]);

  useEffect(() => {
    loadCurrentUser();
    loadContacts();
  }, []);

  useEffect(() => {
    if (activeContact.id === undefined || currentUser.id === undefined) return;
    findChatMessages(activeContact.id, currentUser.id).then((messages) => {
      setMessages(messages);
    });

    loadContacts();
  }, [activeContact]);

  const loadCurrentUser = async () => {
    await getCurrentUser()
      .then((response) => {
        setloggedInUser(response);
        return response;
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const sendMessage = (msg) => {
    if (msg.trim !== "") {
      const message = {
        senderId: currentUser.id,
        recipientId: activeContact.id,
        senderName: currentUser.name,
        recipientName: activeContact.name,
        content: msg,
        timestamp: new Date(),
      };
      stompClient.send("/app/chat", {}, JSON.stringify(message));

      const newMessages = [...messages];
      newMessages.push(message);
      setMessages(newMessages);
    }
  };

  const connect = () => {
    const Stomp = require("stompjs");
    var SockJS = require("sockjs-client");
    SockJS = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(SockJS);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    stompClient.subscribe(
      "/user/" + currentUser.id + "/queue/messages",
      onMessageReceived
    );
  };

  const onMessageReceived = (msg) => {
    console.log(msg);
  };

  const onError = (err) => {
    console.log("Error connecting!");
  };

  const loadContacts = () => {
    const promise = getUsers().then((users) => {
      users.map((user) => {
        countNewMessages(user.id, currentUser.id).then((numOfMessages) => {
          user.newMessages = numOfMessages;
          return user;
        });
      });
      return users;
    });

    promise.then((promises) => {
      Promise.all(promises).then((users) => {
        setContacts(users);
        if (activeContact === undefined && users.length > 0) {
          setActiveContact(users[0]);
        }
      });
    });
  };

  return (
    <div id="frame">
      <div id="sidepanel">
        <div className="profile">
          <div className="wrap">
            <img
              id="profile-img"
              src="https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
              className="online"
              alt=""
            />
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
                  onClick={() => setActiveContact(contact)}
                  className={
                    activeContact && contact.id === activeContact.id
                      ? "contact active"
                      : "contact"
                  }
                >
                  <div className="wrap">
                    <span className="contact-status online"></span>
                    <img id="" src="" alt="" />
                    <div className="meta">
                      <p className="name">{contact.username}</p>
                      {contact.newMessages !== undefined &&
                        contact.newMessages > 0 && (
                          <p class="preview">
                            {contact.newMessages} new messages
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
          <Button id="addcontact">
            <i className="fa fa-user fa-fw" aria-hidden="true"></i>{" "}
            <span>Profile</span>
          </Button>
          <Button id="settings">
            <i className="fa fa-cog fa-fw" aria-hidden="true"></i>{" "}
            <span>Settings</span>
          </Button>
        </div>
      </div>
      <div className="content">
        <div className="contact-profile">
          <img src="" alt="" />
          <p>{activeContact && activeContact.username}</p>
        </div>
        <ScrollToBottom className="messages">
          <ul>
            {messages.map((msg) => {
              return (
                <li
                  className={
                    msg.senderId === currentUser.id ? "sent" : "replies"
                  }
                >
                  {msg.senderId !== currentUser.id && <img src="" alt="" />}
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
