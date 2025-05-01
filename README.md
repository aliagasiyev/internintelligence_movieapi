
![Screenshot 2025-01-24 233519](https://github.com/user-attachments/assets/e2fcceca-0030-48e3-a272-c2257a2c6130)

# 🎬 InternIntelligence_PortfoliAPI – Movie API

This is a RESTful API designed to manage a movie database using **Spring Boot 3.3** and **Java 21**. It enables full **CRUD** operations and includes interactive **Swagger UI** for testing and integration.

---

## 🔧 Features

- **Create** – Add new movie records to the database  
- **Read** – Retrieve movie information (title, director, release year, genre, IMDb rating)  
- **Update** – Modify details of existing movie records  
- **Delete** – Remove movie records by ID  
- **Interactive Docs** – Swagger UI for testing API endpoints  

---

## 🛠️ Technologies Used

- Spring Boot 3.3  
- Java 21  
- PostgreSQL 42.7.3  
- Docker Compose  
- MongoDB 5.0.1  
- Redis 3.3.3  
- RabbitMQ 5.21.0  
- OpenTelemetry 1.37.0  
- JUnit 5.10.3  
- ArchUnit 1.2.1  

---

## 📦 Movie Data Model

| Field         | Type    | Description                         |
|---------------|---------|-------------------------------------|
| `id`          | Long    | Auto-generated unique identifier    |
| `title`       | String  | Name of the movie                   |
| `director`    | String  | Movie director's name               |
| `releaseYear` | Integer | Year the movie was released         |
| `genre`       | String  | Genre (e.g., Action, Comedy, Drama) |
| `imdbRating`  | Double  | IMDb rating of the movie            |

---

## 🔗 API Endpoints

| Method | Endpoint                | Description                     |
|--------|-------------------------|---------------------------------|
| GET    | `/api/v1/movies`        | Retrieve all movies             |
| GET    | `/api/v1/movies/{id}`   | Retrieve movie by ID            |
| POST   | `/api/v1/movies`        | Add new movie                   |
| PUT    | `/api/v1/movies/{id}`   | Update movie by ID              |
| DELETE | `/api/v1/movies/{id}`   | Delete movie by ID              |

---

## 📘 Request and Response Examples

### MovieRequest (POST/PUT)
```json
{
  "title": "Inception",
  "director": "Christopher Nolan",
  "releaseYear": 2010,
  "genre": "Sci-Fi",
  "imdbRating": 8.8
}
