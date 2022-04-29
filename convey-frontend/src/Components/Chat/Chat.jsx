import React from 'react';
import { Button } from 'react-bootstrap';
import ScrollToBottom from "react-scroll-to-bottom";

import "./Chat.css";

function Chat() {
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
              <div class='wrap'>
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