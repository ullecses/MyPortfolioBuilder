# MyPortfolioBuilder
The project is a web service that allows users to create and publish online portfolios of professional skills, projects and company achievements. The goal of the system is to create a convenient and accessible tool that allows users to quickly set up a personalized portfolio using excellent technical methods. The system also supports multimedia, provides mobile adaptation and allows users to analyze the traffic of their portfolio.

The stack of technologies used: Java, Spring Boot (Spring Data JPA, Spring Security, Spring Web), PostgreSQL, Hibernate, Log4J, Maven, Lombok, pgAdmin.

# API Endpoints

## Users

### Get User by ID

- **GET** `/api/users/{id}`
  - **Parameters:**
    - `id` (integer) — User ID
  - **Response:**
    - `200 OK` — User object

### Update User

- **PUT** `/api/users/{id}`
  - **Parameters:**
    - `id` (integer) — User ID
  - **Request Body:** User object in JSON format
  - **Response:**
    - `200 OK` — Updated User object

### Delete User

- **DELETE** `/api/users/{id}`
  - **Parameters:**
    - `id` (integer) — User ID
  - **Response:**
    - `200 OK`

### Get All Users

- **GET** `/api/users`
  - **Response:**
    - `200 OK` — Array of User objects

### Create User

- **POST** `/api/users`
  - **Request Body:** User object in JSON format
  - **Response:**
    - `200 OK` — Created User object

### Register User

- **POST** `/api/users/register`
  - **Request Body:** `UserRequestDTO` object in JSON format
  - **Response:**
    - `200 OK` — User object

### User Login

- **POST** `/api/users/login`
  - **Request Body:** `UserRequestDTO` object in JSON format
  - **Response:**
    - `200 OK` — String (token or message)

## Portfolios

### Get Portfolio by ID

- **GET** `/api/portfolios/{id}`
  - **Parameters:**
    - `id` (integer) — Portfolio ID
  - **Response:**
    - `200 OK` — Portfolio object

### Update Portfolio

- **PUT** `/api/portfolios/{id}`
  - **Parameters:**
    - `id` (integer) — Portfolio ID
  - **Request Body:** Portfolio object in JSON format
  - **Response:**
    - `200 OK` — Updated Portfolio object

### Delete Portfolio

- **DELETE** `/api/portfolios/{id}`
  - **Parameters:**
    - `id` (integer) — Portfolio ID
  - **Response:**
    - `200 OK`

### Get All Portfolios

- **GET** `/api/portfolios`
  - **Response:**
    - `200 OK` — Array of Portfolio objects

### Create Portfolio

- **POST** `/api/portfolios`
  - **Request Body:** `PortfolioRequestDTO` object in JSON format
  - **Response:**
    - `200 OK` — Created Portfolio object

## Notes

- All requests requiring a request body must contain valid JSON data matching the specified schemas.
- For detailed information on each request and response, you can use the Swagger UI interface.

