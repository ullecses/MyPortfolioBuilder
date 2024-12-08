import React, { useState, useEffect, useRef } from 'react';
import '../styles/profile.css'; 

function Profile() {
  const [activeSection, setActiveSection] = useState('portfolio'); // Установим начальное значение на 'portfolio'
  const [username, setUsername] = useState(''); // Состояние для хранения почты
  const handleSectionChange = (section) => {
    setActiveSection(section);
  };
  
  const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const ws = useRef(null);
    const userId = useRef(Date.now() + Math.random().toString());

    useEffect(() => {
      const token = localStorage.getItem('token');
      if (token) {
        console.log('JWT Token:', token); // Вывод самого токена
        try {
          const payload = token.split('.')[1];
          const decodedPayload = JSON.parse(atob(payload));
          console.log('Decoded token payload:', decodedPayload);
          
          // Извлечение почты и получение имени пользователя до '@'
          const email = decodedPayload.sub; 
          setUsername(email.split('@')[0]); // Установка имени пользователя
        } catch (error) {
          console.error('Ошибка при декодировании токена:', error);
        }
      }

        // Подключаемся к WebSocket-серверу
        ws.current = new WebSocket('ws://localhost:8001');

        // Обработка входящих сообщений
        ws.current.onmessage = (event) => {
            try {
                const parsedData = JSON.parse(event.data); // Парсим JSON
                console.log("Received message from server:", parsedData); // Логгирование полученного сообщения
            
                // Проверяем, что получено правильное сообщение
                if (parsedData.type === 'message') {
                    const messageData = parsedData.data; // Это сообщение, отправленное клиентом
                    
                    // Проверяем, что сообщение не пустое и является объектом
                    if (messageData && messageData.text) {
                        setMessages(prevMessages => [...prevMessages, messageData]); // Добавляем текст сообщения
                    } else {
                        console.error("Получено пустое сообщение:", messageData);
                    }
                }
            } catch (error) {
                console.error("Ошибка при разборе сообщения:", error);
            }
        };

        // Очистка при закрытии WebSocket
        return () => ws.current.close();
    }, []);

    // Отправка сообщения на сервер
    const sendMessage = () => {
        if (input.trim()) {
            const message = {
                id: Date.now(),
                userId: userId.current,
                text: input,
            };
            ws.current.send(JSON.stringify(message)); // Отправляем сообщение в формате JSON
            setMessages(prevMessages => [...prevMessages, message]); // Добавляем сообщение в свой список сообщений
            setInput(''); // Очищаем поле ввода
        }
    };


  // Содержимое для секции "Портфолио"
  const renderPortfolio = () => (
    <div>
      <p>Здесь вы можете увидеть ваши работы.</p>
      {/* Вы можете добавить больше элементов или форм здесь */}
    </div>
  );

  // Содержимое для секции "Проекты"
  const renderProjects = () => (
    <div>
      <p>Здесь можно увидеть детали ваших проектов.</p>
      {/* Добавьте формы или другую информацию */}
    </div>
  );

  // Содержимое для секции "Сообщения"
  const renderMessages = () => (
    <div className="renderMessages">
        <div className="chat-box">
            {messages.map((msg) => (
                <div
                    key={msg.id}
                    className={`message ${msg.userId === userId.current ? 'sent' : 'received'}`}
                >
                    {msg.text} {/* Здесь отображается текст сообщения */}
                </div>
            ))}
        </div>
        <div className='btnInput'>
        <input type="text" value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' ? sendMessage() : null}
            placeholder="Введите ваше сообщение"
        />
        <button onClick={sendMessage}>Отправить</button>
    </div>
  </div>
);
  return (
    <div className='profile-page'>
      <div className='profileData'>
        <div className='basePofInfo'>
          <div className='profPhoto'></div>
          <p className='profName'>{username || 'Имя пользователя'}</p> {/* Отображение имени пользователя */}          <div className='profPhone'>
            <label>
              
              <input type="text" readOnly />
            </label>
          </div>
        </div>

        <div className='navegPage'>
          <nav className="profile_nav">
            <button 
              onClick={() => handleSectionChange('portfolio')}
              style={{ fontWeight: activeSection === 'portfolio' ? 'bold' : 'normal' }} // Условный стиль
            >
              Your portfolio
            </button>
            <button 
              onClick={() => handleSectionChange('projects')}
              style={{ fontWeight: activeSection === 'projects' ? 'bold' : 'normal' }} // Условный стиль
            >
              Your details
            </button>
            <button 
              onClick={() => handleSectionChange('messages')}
              style={{ fontWeight: activeSection === 'messages' ? 'bold' : 'normal' }} // Условный стиль
            >
              Messages
            </button>
          </nav>
          <div className='pageInProf'>
            {activeSection === 'portfolio' && renderPortfolio()}
            {activeSection === 'projects' && renderProjects()}
            {activeSection === 'messages' && renderMessages()}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
