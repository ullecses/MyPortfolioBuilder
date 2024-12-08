
## Prerequisites

Before running the application, ensure you have the following installed:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Project Structure

- **db**: PostgreSQL database container
- **backend**: Spring Boot application
- **frontend**: React application

## Configuration

### Environment Variables

The application uses the following environment variables for database configuration:

- `POSTGRES_USER`: PostgreSQL username
- `POSTGRES_PASSWORD`: PostgreSQL password
- `POSTGRES_DB`: PostgreSQL database name
- `SPRING_DATASOURCE_URL`: JDBC connection string for the database
- `SPRING_DATASOURCE_USERNAME`: Database username for the backend
- `SPRING_DATASOURCE_PASSWORD`: Database password for the backend

These variables are defined in the `docker-compose.yml` file.

## Docker Setup

### Docker Compose File

Here is the `docker-compose.yml` structure used for the application:

```yaml
services:
  db:
    image: postgres:15
    container_name: postgres-db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 7340
      POSTGRES_DB: myportfoliobuilder
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  backend:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: backend-container
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/myportfoliobuilder
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: 7340

  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    container_name: frontend-container
    ports:
      - "3000:80"
    depends_on:
      - backend

volumes:
  postgres_data: 
```
## Instructions for Running the Application

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-repo/myportfoliobuilder.git
   cd myportfoliobuilder
   
2. **Build and run the containers:**
    ```bash
   docker-compose up --build

3. **Access the application:**
   - Frontend: Open your browser and navigate to http://localhost:3000.
   - Backend: API endpoints are available at http://localhost:8080.
   - Database: The PostgreSQL database is exposed on port 5432

