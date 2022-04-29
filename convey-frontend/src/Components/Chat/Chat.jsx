import React from 'react';
import { Button } from 'react-bootstrap';
import ScrollToBottom from "react-scroll-to-bottom";

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
              
              <Button>SendMessage</Button>
            </div>
          </div>
      </div>
  );
}

export default Chat;