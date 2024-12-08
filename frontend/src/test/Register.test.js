import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Register from '../components/Register.jsx';
import '@testing-library/jest-dom'; // Импортируем jest-dom для дополнительных матчеров

// Мокируем useNavigate
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}));

describe('Компонент Register', () => {
  beforeEach(() => {
    // Мокируем глобальный fetch перед каждым тестом
    global.fetch = jest.fn();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  test('отображение ошибки, если пароли не совпадают', () => {
    render(
      <MemoryRouter>
        <Register />
      </MemoryRouter>
    );
  
    const passwordInput = screen.getByPlaceholderText(/Enter your password/i);
    const confirmPasswordInput = screen.getByPlaceholderText(/Confirm your password/i);
    const submitButton = screen.getByText(/Create Account/i);
  
    // Вводим разные пароли
    fireEvent.change(passwordInput, { target: { value: 'password123' } });
    fireEvent.change(confirmPasswordInput, { target: { value: 'differentPassword' } });
    fireEvent.click(submitButton);
  
    // Ожидаем, что будет отображена ошибка
    expect(screen.getByText(/Passwords do not match/i)).toBeInTheDocument();
  });

  test('успешная регистрация вызывает API и редиректит', async () => {
    // Мокируем успешный ответ от fetch
    global.fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve({ message: 'Registration successful' }),
    });

    render(
      <MemoryRouter>
        <Register />
      </MemoryRouter>
    );

    const emailInput = screen.getByPlaceholderText(/Enter your email/i);
    const passwordInput = screen.getByPlaceholderText(/Enter your password/i);
    const confirmPasswordInput = screen.getByPlaceholderText(/Confirm your password/i);
    const submitButton = screen.getByText(/Create Account/i);

    // Заполняем форму
    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });
    fireEvent.change(confirmPasswordInput, { target: { value: 'password123' } });
    fireEvent.click(submitButton);

    // Ожидаем, что был вызван fetch с правильными параметрами
    await waitFor(() => {
      expect(global.fetch).toHaveBeenCalledWith(
        'http://26.188.13.76:8080/api/users/register', // Замените на любой другой URL, так как это моки
        expect.objectContaining({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email: 'test@example.com', password: 'password123' }),
        })
      );
    });

    // Ожидаем редирект на главную
    await waitFor(() => expect(mockNavigate).toHaveBeenCalledWith('/'));
  });

  test('отображение ошибки при сбое регистрации', async () => {
    // Мокируем ошибку от fetch
    global.fetch.mockResolvedValueOnce({
      ok: false,
      json: () => Promise.resolve({ message: 'Registration failed' }),
    });
  
    render(
      <MemoryRouter>
        <Register />
      </MemoryRouter>
    );
  
    const emailInput = screen.getByPlaceholderText(/Enter your email/i);
    const passwordInput = screen.getByPlaceholderText(/Enter your password/i);
    const confirmPasswordInput = screen.getByPlaceholderText(/Confirm your password/i);
    const submitButton = screen.getByText(/Create Account/i);
  
    // Заполняем форму
    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });
    fireEvent.change(confirmPasswordInput, { target: { value: 'password123' } });
    fireEvent.click(submitButton);
  
    // Ожидаем, что будет отображена ошибка
    await waitFor(() => expect(screen.getByText(/Registration failed/i)).toBeInTheDocument());
  });
});
