// index.js
import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App.js';
import './styles/common.css';

const container = document.getElementById('root');
const root = createRoot(container); // Создаем корневой узел с использованием createRoot

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App /> {/* Оборачиваем App в BrowserRouter */}
    </BrowserRouter>
  </React.StrictMode>
);
