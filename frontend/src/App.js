import React from 'react';
import { Routes, Route, useLocation } from 'react-router-dom'; 
import Header from './components/header/Header.jsx';
import Footer from './components/Footer.jsx';
import Home from './components/Home.jsx';
import Login from './components/Login.jsx';
import Register from './components/Register.jsx';
import Generator from './components/Generator.jsx';
import FAQ from './components/FAQ.jsx';
import Payment from './components/Payment.jsx';
import ScrollToTop from './components/ScrollToTop.jsx';
import Profile from './components/Profile.jsx';
import ChatIcon from './components/ChatIcon.jsx';
import './i18n.js';  // Импорт i18n перед рендером приложения

function App() {
  const location = useLocation();

  const isAuthPage = location.pathname === '/login' || location.pathname === '/register';

  return (
    <div className="App">
      <ScrollToTop />
      {/* Скрываем хедер и футер на страницах логина и регистрации */}
      {!isAuthPage && <Header />}

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/payment" element={<Payment />} />
        <Route path="/generator" element={<Generator />} />
        <Route path="/faq" element={<FAQ />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>

      {!isAuthPage && <Footer />}
      <ChatIcon />
    </div>
  );
}

export default App;
