# RentVideo - Video Rental System

A RESTful API service built with Spring Boot to manage an online video rental system.

## Features

- User registration and authentication with JWT
- Role-based authorization (CUSTOMER and ADMIN)
- Video management (browse, create, update, delete)
- Rental management (rent and return videos)
- MySQL database for data persistence

## Technologies Used

- Java 17
- Spring Boot 3.1
- Spring Security with JWT
- Spring Data JPA
- MySQL Database
- Lombok
- Gradle

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and get JWT token

### Videos

- `GET /api/videos` - Get all videos (public)
- `GET /api/videos/{id}` - Get video by ID
- `POST /api/videos/admin` - Create a new video (ADMIN only)
- `PUT /api/videos/admin/{id}` - Update a video (ADMIN only)
- `DELETE /api/videos/admin/{id}` - Delete a video (ADMIN only)

### Rentals

- `GET /api/rentals` - Get all active rentals for the current user
- `POST /api/rentals/videos/{videoId}/rent` - Rent a video
- `POST /api/rentals/videos/{videoId}/return` - Return a rented video

## Business Rules

- Users can have a maximum of 2 active rentals at a time
- Videos can only be rented if they are available
- Only ADMIN users can manage videos (create, update, delete)

## Setup and Installation

1. Clone the repository
2. Configure MySQL database in `application.properties`
3. Build the project: `./gradlew build`
4. Run the application: `./gradlew bootRun`

## API Documentation

API documentation is available through Swagger UI at `/swagger-ui.html` when the application is running.
