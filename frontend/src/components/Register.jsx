import React, { useState } from 'react';
import '../styles/form.css';
import { useNavigate } from 'react-router-dom';
import { RxExit } from "react-icons/rx";

function Register() {
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleLoginClick = () => {
    navigate('/login');
  };

  const handleExitClick = () => {
    navigate('/');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setErrorMessage('Passwords do not match');
      return;
    }
    setErrorMessage('');

    const userData = { email, password };
    console.log(userData);


    /*====================================================================== */
    // Имитация успешной регистрации
    /*console.log(`User with email ${email} registered successfully.`);
    localStorage.setItem('isAuthenticated', 'true'); // Устанавливаем флаг, что пользователь зарегистрирован
 
    // Переход на главную страницу
    navigate('/'); // Перенаправляем на главную страницу после "успешной регистрации"
  };*/
    /*===================================================================== */


    try {
      const response = await fetch('localhost:8080/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      if (response.ok) {
        const data = await response.json();
        console.log('JWT Token:', data.token); // Здесь мы предполагаем, что токен возвращается в поле `token`
            alert(data.message || 'Данные успешно отправлены');
            localStorage.setItem('isAuthenticated', 'true');
            navigate('/');
      } else {
        const errorData = await response.json();
        //alert(errorData.message ||'Registration failed');
        setErrorMessage(errorData.message || 'Registration failed');
      }
    } catch (error) {
      alert('Something went wrong');
      console.error(error);
    }
  };
  const handlePasswordFocus = () => {
    setErrorMessage('');
  };
  return (
    <div className="register">
      <div className="formm">
        <div className="image-container"></div>
        <div className="container2">
          <RxExit className="exit-icon" onClick={handleExitClick} style={{ cursor: 'pointer' }} />
          <form className="reglogform" onSubmit={handleSubmit}>
            <h1>Welcome to Generator</h1>
            <div className="input-box">
              <label className="email">Email</label>
              <input type="email" placeholder="Enter your email" className="form-style"
                value={email} onChange={(e) => setEmail(e.target.value)} required />
            </div>
            <div className="input-box">
              <label className="password">Password</label>
              <input type="password" placeholder="Enter your password" className="form-style" value={password}
                onChange={(e) => setPassword(e.target.value)} onFocus={handlePasswordFocus}
                required
              />
            </div>
            <div className="input-box">
              <label className="password">Confirm Password</label>
              <input type="password" placeholder="Confirm your password" className="form-style" value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)} onFocus={handlePasswordFocus}
                required
              />
              {/* Отображение сообщения об ошибке */}
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            </div>
            <button type="submit" className="btn-form">Create Account</button>

            <div className="register-link">
              <p>Already have an account? <button type="button" className='slka' onClick={handleLoginClick}>Login</button></p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Register;
