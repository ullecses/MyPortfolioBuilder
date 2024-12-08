import React, { useState, useEffect, useRef } from 'react';
import '../styles/MiniChat.css';

function MiniChat({ onExpand }) {
  //const [activeSection, setActiveSection] = useState('portfolio'); // Начальная секция
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const ws = useRef(null);
  const userId = useRef(Date.now() + Math.random().toString());

  useEffect(() => {
    // Подключение к WebSocket серверу
    ws.current = new WebSocket('ws://localhost:8001');

    ws.current.onmessage = (event) => {
      try {
        const parsedData = JSON.parse(event.data); // Парсим JSON
        console.log("Received message from server:", parsedData);

        if (parsedData.type === 'message' && parsedData.data?.text) {
          setMessages(prevMessages => [...prevMessages, parsedData.data]);
        } else {
          console.error("Получено пустое или некорректное сообщение:", parsedData);
        }
      } catch (error) {
        console.error("Ошибка при разборе сообщения:", error);
      }
    };

    return () => ws.current.close(); // Закрываем WebSocket при размонтировании
  }, []);

  const sendMessage = () => {
    if (input.trim()) {
      const message = {
        id: Date.now(),
        userId: userId.current,
        text: input,
      };
      ws.current.send(JSON.stringify({ type: 'message', data: message })); // Отправка сообщения в формате JSON
      setMessages(prevMessages => [...prevMessages, message]); // Добавляем сообщение в локальный список
      setInput(''); // Очищаем поле ввода
    }
  };

  return (
    <div className="mini-chat">
      <div className="mini-chat-header">
        <span>Chat</span>
      </div>
      <div className="mini-chat-content">
        <div className="chat-boxMini">
          {messages.map((msg) => (
            <div
              key={msg.id}
              className={`messageM ${msg.userId === userId.current ? 'sent' : 'received'}`}
            >
              {msg.text}
            </div>
          ))}
        </div>
        <div className="btnInput">
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
            placeholder="Введите ваше сообщение"
          />
          <button onClick={sendMessage}>Отправить</button>
        </div>
      </div>
    </div>
  );
}

export default MiniChat;
