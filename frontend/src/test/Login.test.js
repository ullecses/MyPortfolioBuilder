import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Login from '../components/Login.jsx';
import '@testing-library/jest-dom'; // Для дополнительных матчеров

// Мокируем useNavigate
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}));

describe('Компонент Login', () => {
  beforeEach(() => {
    // Мокируем глобальный fetch перед каждым тестом
    global.fetch = jest.fn();
    jest.clearAllMocks();
  });

  test('Отображение ошибки при неправильных учетных данных', async () => {
    // Мокаем ответ fetch с кодом 401 (Unauthorized)
    global.fetch.mockResolvedValueOnce({
      ok: false,
      status: 401,
      json: () => Promise.resolve({ message: 'Invalid email or password' }),
    });

    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );

    // Заполняем поля и отправляем форму
    fireEvent.change(screen.getByPlaceholderText(/Enter your email/i), { target: { value: 'wrong@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/Enter your password/i), { target: { value: 'wrongpassword' } });
    fireEvent.click(screen.getByRole('button', { name: /Login/i }));

    // Проверяем, что отображается ошибка
    await waitFor(() =>
      expect(screen.getByText(/Invalid email or password/i)).toBeInTheDocument()
    );
  });

  test('успешный логин сохраняет токен и редиректит', async () => {
    const mockJWT = 'valid-jwt-token';
  
    global.fetch.mockResolvedValueOnce({
      ok: true,
      headers: {
        get: (header) => (header === 'content-type' ? 'application/json' : null),
      },
      json: () => Promise.resolve({ jwt: mockJWT }),
    });
  
    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );
  
    const emailInput = screen.getByPlaceholderText(/Enter your email/i);
    const passwordInput = screen.getByPlaceholderText(/Enter your password/i);
    const submitButton = screen.getByText(/Login/i);
  
    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });
    fireEvent.click(submitButton);
  
    // Проверяем, что fetch был вызван с правильными параметрами
    await waitFor(() =>
      expect(global.fetch).toHaveBeenCalledWith(
        'http://26.188.13.76:8080/api/users/login',
        expect.objectContaining({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email: 'test@example.com', password: 'password123' }),
          credentials: 'same-origin',
        })
      )
    );
  
    // Проверяем, что токен сохранен и редирект выполнен
    await waitFor(() => {
      expect(localStorage.getItem('token')).toBe(mockJWT);
      expect(mockNavigate).toHaveBeenCalledWith('/');
    });
  });

  test('отображение ошибки при сбое на сервере', async () => {
    global.fetch.mockResolvedValueOnce({
      ok: false,
      status: 500,
      json: () => Promise.resolve({ message: 'Internal Server Error' }),
    });
  
    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );
  
    const emailInput = screen.getByPlaceholderText(/Enter your email/i);
    const passwordInput = screen.getByPlaceholderText(/Enter your password/i);
    const submitButton = screen.getByText(/Login/i);
  
    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });
    fireEvent.click(submitButton);
  
    await waitFor(() =>
      expect(screen.getByText(/Login failed: Unexpected server response/i)).toBeInTheDocument()
    );
  });

  test('Отображение ошибки при отсутствии ответа от сервера', async () => {
    // Мокаем сетевую ошибку
    global.fetch.mockRejectedValueOnce(new Error('Network Error'));

    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );

    // Заполняем поля и отправляем форму
    fireEvent.change(screen.getByPlaceholderText(/Enter your email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/Enter your password/i), { target: { value: 'password123' } });
    fireEvent.click(screen.getByRole('button', { name: /Login/i }));

    // Проверяем, что отображается сообщение об ошибке
    await waitFor(() =>
      expect(screen.getByText(/Something went wrong. Please try again./i)).toBeInTheDocument()
    );
  });
});
