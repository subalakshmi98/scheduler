# scheduler-service

A Simulation of a meeting scheduling platform. The service should enable users to manage their time slots, schedule meetings, and view their custom calendar availability.

# Technology Stack

- Java 21
- Spring Boot 3
- Spring Data JPA (Hibernate)
- H2 In-Memory Database
- Maven
- JUnit 5
- Mockito
- MockMvc
- Swagger / OpenAPI
- Spring Boot Actuator
- Docker

# Features

# User Management
- Create user
- Email-based unique user identification
- Automatic calendar creation for every user

# Time Slot Management
- Create available slots
- View all slots
- Modify existing slots
- Delete slots
- Update Status
- Prevent overlapping slots
- Automatically generate slot numbers (S1, S2, S3...)

# Meeting Scheduling
- Convert FREE slot into a meeting
- With Meeting title, description and Participants
- Automatically mark slot as BOOKED
- Prevent booking BUSY or already BOOKED slots

# Availability

- View availability for a selected time range with an aggregated response

# API Endpoints

- POST   /api/v1/users

- POST   /api/v1/users/{email}/slots
- GET    /api/v1/users/{email}/slots
- GET    /api/v1/users/{email}/slots/{slotNumber}
- PUT    /api/v1/users/{email}/slots/{slotNumber}
- PATCH  /api/v1/users/{email}/slots/{slotNumber}
- DELETE /api/v1/users/{email}/slots/{slotNumber}

- POST   /api/v1/users/{email}/slots/{slotNumber}/meetings

- GET    /api/v1/users/{email}/availability?from=...&to=...

# Validation

Bean Validation is implemented using Jakarta Validation for Valid email format, Mandatory fields

# Exception Handling

A global exception handler provides consistent error responses.

# Testing

- Unit Tests : Implemented using Junit, Mockito
- Integration Tests : Implemented using SpringBootTest, MockMvc, H2 Database 

# Swagger Documentation

Swagger UI is available after starting the application:

` http://localhost:8080/swagger-ui/index.html `

# Actuator

Health endpoint:

`http://localhost:8080/actuator/health `

Metrics:

`http://localhost:8080/actuator/metrics `

# Running Locally

- Clone: git clone <repository-url>
- Build: mvn clean package
- Run: mvn spring-boot:run
- Application starts on: http://localhost:8080

# Running with Docker

- Build Docker Image: docker build -t scheduler .
- Run Container: docker run -p 8080:8080 scheduler

# Assumptions

- Email uniquely identifies a user.
- Calendar is maintained as a domain concept and is not exposed as a REST resource.
- Every user automatically owns one calendar.
- Slot numbers are generated sequentially for each user (S1, S2, S3...).
- Participants do not need to be registered platform users.
- A meeting can only be created from a FREE slot.
- BOOKED slots cannot be modified or deleted.
- Slot overlap is not permitted for the same user.
- Availability groups slots into FREE and BUSY (including BOOKED).

# Scalability Considerations

The solution is designed to support hundreds of users and thousands of slots.Design considerations include:

- UUID as internal identifiers
- Business-friendly slot numbers
- Repository-based overlap validation
- Time-range filtering at the database level
- Layered architecture (Controller → Service → Repository)

# Database

The application currently uses an H2 in-memory database for simplicity and local execution.

The persistence layer is implemented using Spring Data JPA and Hibernate, making the solution compatible with PostgreSQL through configuration changes without modifying business logic.

# Future Improvements
- PostgreSQL profile
- Authentication & Authorization
- Email notifications
- Recurring meetings
- Time zone support
- Pagination and sorting
- API versioning strategy
- Audit logging
- Test Containers