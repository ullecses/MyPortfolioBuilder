import React, { useState, useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import '../styles/MiniChat.css';

function MiniChat() {
  const [messages, setMessages] = useState([]); // Список сообщений
  const [input, setInput] = useState(''); // Текст сообщения
  const [users, setUsers] = useState([]); // Список подключённых пользователей
  const [recipient, setRecipient] = useState('public'); // ID выбранного получателя (или "public" для всех)
  const [isConnected, setIsConnected] = useState(false); // Статус подключения
  const client = useRef(null); // STOMP клиент

  // Данные текущего пользователя
  const userEmail = localStorage.getItem('userEmail'); // Берём email из localStorage
  const userId = useRef(Date.now().toString()); // Генерация уникального ID пользователя

  useEffect(() => {
    if (!userEmail) {
      console.error('User is not logged in!');
      return;
    }

    // Создаём STOMP-клиент
    client.current = new Client({
      brokerURL: 'ws://localhost:8080/chat', // Подключение к вашему серверу WebSocket
      reconnectDelay: 5000, // Автоматическая попытка переподключения
      heartbeatIncoming: 4000, // Проверка соединения
      heartbeatOutgoing: 4000,
      debug: (str) => console.log(str), // Логирование
    });

    // Обработка успешного подключения
    client.current.onConnect = () => {
      console.log('Connected to WebSocket server');
      setIsConnected(true);

      // Подписка на публичные сообщения
      client.current.subscribe('/topic/messages', (message) => {
        const parsedMessage = JSON.parse(message.body);
        console.log('Received public message:', parsedMessage);
        setMessages((prevMessages) => [...prevMessages, parsedMessage]);
      });

      // Подписка на приватные сообщения
      client.current.subscribe(`/user/${userId.current}/topic/privatemessages`, (message) => {
        const parsedMessage = JSON.parse(message.body);
        console.log('Received private message:', parsedMessage);
        setMessages((prevMessages) => [...prevMessages, parsedMessage]);
      });

      // Подписка на список пользователей
      client.current.subscribe('/topic/users', (usersMessage) => {
        const connectedUsers = JSON.parse(usersMessage.body);
        console.log('Users list updated:', connectedUsers);
        setUsers(connectedUsers); // Обновляем список пользователей
      });

      // Отправка информации о пользователе на сервер
      const user = {
        id: userId.current,
        username: userEmail, // Используем email как имя пользователя
      };
      client.current.publish({
        destination: '/app/user',
        body: JSON.stringify(user),
      });
    };

    // Обработка ошибок подключения
    client.current.onWebSocketError = (error) => {
      console.error('WebSocket error:', error);
    };

    // Обработка ошибок STOMP
    client.current.onStompError = (frame) => {
      console.error('STOMP error:', frame.headers['message'], frame.body);
    };

    // Активация клиента
    client.current.activate();

    return () => {
      if (client.current) client.current.deactivate();
    };
  }, [userEmail]);

  const sendMessage = () => {
    if (input.trim()) {
      const message = {
        user: { id: userId.current, username: userEmail },
        comment: input,
        action: recipient === 'public' ? 'NEW_MESSAGE' : 'NEW_PRIVATE_MESSAGE',
        timestamp: new Date().toISOString(),
        receiverId: recipient === 'public' ? null : recipient, // Если приватное сообщение, добавляем ID получателя
      };

      // Публикация сообщения на сервер
      client.current.publish({
        destination: recipient === 'public' ? '/app/message' : '/app/privatemessage',
        body: JSON.stringify(message),
      });

      setInput(''); // Очистка поля ввода
    }
  };

  return (
    <div className="mini-chat">
      <div className="mini-chat-header">
        {isConnected ? (
          <div>
            <span>Подключен как: {userEmail}</span>
          </div>
        ) : (
          <span>Подключение...</span>
        )}
      </div>
      {isConnected && (
        <div className="mini-chat-content">
          <div className="chat-boxMini">
            {messages.map((msg, index) => (
              <div
                key={index}
                className={`messageM ${msg.user.username === userEmail ? 'sent' : 'received'}`}
              >
                {msg.user.username !== userEmail && (
                  <strong>{msg.user.username}:</strong>
                )}
                {msg.comment}
              </div>
            ))}
          </div>
          <div className="btnInput">
            <select
              value={recipient}
              onChange={(e) => setRecipient(e.target.value)}
            >
              <option value="public">Всем</option>
              {users.map((user) =>
                user.id !== userId.current ? (
                  <option key={user.id} value={user.id}>
                    {user.username}
                  </option>
                ) : null
              )}
            </select>
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
      )}
    </div>
  );
}

export default MiniChat;
