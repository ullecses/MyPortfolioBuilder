# Используем Node.js для сборки приложения
FROM node:18 as build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем остальные файлы
COPY . .

# Собираем приложение
RUN npm run build

# Используем nginx для сервировки статических файлов
FROM nginx:1.23

# Копируем собранное приложение в nginx
COPY --from=build /app/build /usr/share/nginx/html

# Экспонируем порт
EXPOSE 80

# Запускаем nginx
CMD ["nginx", "-g", "daemon off;"]
