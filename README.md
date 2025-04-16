# Flight Booking System

> A simple backend application developed in Java for managing flight reservations.

The system provides basic CRUD (Create, Read, Update, Delete) operations for managing flights, passengers, and reservations. It supports functionalities such as adding new flights, passengers, and bookings, modifying or removing existing records, and retrieving stored data.

## Screenshots
![Example screenshot](/img/img.png)

## Tech Stack

- Java 21
- Spring Boot 3.3.4
- Spring Data JPA
- H2 Database
- Maven
- Docker & Docker Compose
- Swagger (OpenAPI 3)

---

## Features

Functionality for handling **Flights**, **Passengers**, and **Reservations**, offering:

- **Addition**: Add new flights, passengers, or reservations.
- **Editing**: Modify existing records such as seat numbers or flight details.
- **Deletion**: Remove reservations, flights, or passenger data.
- **Browsing**: Browse all reservations, flights, and passengers.
- **Seat availability check**: Prevents creating a reservation for a seat that’s already taken.
- **Email confirmation**: Sends an automatic email after a reservation is created.

## Getting Started

#### 1. **Create `.env` file** in the root directory:

```env
APP_MAIL_USERNAME=your_email@gmail.com
APP_MAIL_PASSWORD=your_password
```
#### 2. Clone the application


    git clone https://github.com/Qumpell/FlightBookingSystem.git
    cd FlightBookingSystem

### Run with Docker

**Build and start the app**

```bash
    docker-compose up --build
 ```


### Run locally (without Docker)

```bash 
    mvn clean spring-boot:run
```

### Access API:
 
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- App runs on: `http://localhost:8080`

## Testing
Basic unit tests are provided for core service functionalities.  

---