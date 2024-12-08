import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate} from 'react-router-dom';
import './header.css';
import logo from './logo.svg';
import { FaUserCircle} from 'react-icons/fa';
import { CiGlobe } from "react-icons/ci";
import { useTranslation } from 'react-i18next';

function Header() {
  const location = useLocation();
  const navigate = useNavigate();
  const { t, i18n } = useTranslation();  // подключаем i18n

  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [language, setLanguage] = useState(i18n.language);
/*  useEffect(() => {
    setIsAuthenticated(localStorage.getItem('isAuthenticated') === 'true');
  }, []);*/

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token); // Проверяем наличие токена
  }, []);

  // Функция выхода из системы
  /*const handleLogout = () => {
    localStorage.setItem('isAuthenticated', 'false');
    setIsAuthenticated(false);
    navigate('/'); // Перенаправляем пользователя на главную страницу после выхода
  };*/

  const handleLogout = () => {
    localStorage.removeItem('token'); // Удаляем токен
    setIsAuthenticated(false);
    navigate('/'); // Перенаправляем пользователя на главную страницу
  };

  // Функция переключения языка
  const toggleLanguage = () => {
    const newLanguage = language === 'en' ? 'ru' : 'en';
    setLanguage(newLanguage);
    i18n.changeLanguage(newLanguage);  // Переключаем язык
  };

  return (
    <header className='header'>
      <div className="container">
        <div className="header_row">
          <div className="header_logo">
          <img src={logo} className="headerlogo" />
            <span>{t('header.title')}</span>
          </div>

          <nav className="header_nav">
            <ul>
              <li className={location.pathname === '/' ? 'active' : ''}><Link to="/" className="headerBtn">{t('header.home')}</Link></li>
              <div className='cherta'></div>
              <li className={location.pathname === '/generator' ? 'active' : ''}><Link to="/generator" className="headerBtn">{t('header.generator')}</Link></li>
              <div className='cherta'></div>
              <li className={location.pathname === '/faq' ? 'active' : ''}><Link to="/faq" className="headerBtn">{t('header.faq')}</Link></li>
              <div className='cherta'></div>
              <li className={location.pathname === '/examples' ? 'active' : ''}><Link to="/examples" className="headerBtn">{t('header.examples')}</Link></li>
            </ul>
          </nav>

          <div className="headerbtn-lr">
            {/* Кнопка переключения языка */}
            <button onClick={toggleLanguage} className="languageToggle">
              <CiGlobe size={25} />
              <span className="languageLabel">{language.toUpperCase()}</span>
            </button>
            {isAuthenticated ? (
              <>
              <button onClick={handleLogout} className='btn-log'>
                {t('header.logOutBtn')}
                </button>
                <Link to="/profile" className='iconProfile'>
                  <FaUserCircle size={28} /> {/* Иконка профиля */}
                </Link>
                
              </>
            ) : (
              <>
                <Link to="/login" className='btn-log'>{t('header.loginBtn')}</Link>
                <Link to="/register" className='btn-reg'>{t('header.registerBtn')}</Link>
              </>
            )}
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;