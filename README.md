# CSA Capstone Task Management System

The current project features the necessary technologies to run a Spring Boot web application designed to manage tasks across different role-based profiles. It provides RESTful APIs for task creation, assignment, tracking, and completion, supporting user authentication and role-based access.


## Table of Contents
1. [General Overview](#general-overview)
2. [Configuration](#configuration)
3. [Folder Structure](#folder-structure)
4. [API Endpoints](#api-endpoints)
5. [Getting Started](#getting-started)
6. [Contribution](#contribution)


## General Overview
The CSA Capstone Task Management System is designed to streamline task management for teams and organizations. Built with Spring Boot, it offers a robust backend supporting secure user authentication, role-based authorization, and comprehensive RESTful APIs for managing tasks and users.

### Key Features
- **Task Lifecycle Management:** Creates, assigns, updates, and tracks tasks from inception to completion.
- **User Roles:** Supports multiple user roles (e.g., Admin, User) with different permissions.
- **Authentication & Security:** Implements JWT-based authentication and Spring Security for secure access.
- **API-First Approach:** All functionalities are exposed via REST APIs, making integration with frontends or other systems straightforward.
- **Documentation:** Integrated Swagger/OpenAPI documentation for easy API exploration and testing.
- **Testing:** Includes Mockito-based tests to ensure reliability and maintainability.

This system is suitable for small to medium-sized teams looking for a customizable and extensible solution to manage their workflow efficiently.


### Technologies Implemented
* **Backend:** Spring Boot, Spring Data JPA, Spring Security
* **Database:** MySQL
* **Build Tool:** Gradle
* **API Documentation:** OpenAPI
* **Testing:** JUnit, Mockito
* **Other:** Actuator, Logback


## Configuration
```properties
spring.application.name=TaskManagementSystem
spring.datasource.url=jdbc:mysql://localhost:3306/task_management_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
logging.level.root=WARN
logging.level.com.cognizant.TaskManagementSystem=WARN
logging.file.name=logs/application.log
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
management.info.env.enabled=true
info.app.name=TaskManagementSystem
```


## Folder Structure
```
csa-capstone-task-management-system (TaskManagementSystem)
│
├── logs/
│   └── application.log
├── src/
│   ├── main/
│   │   ├── java/com/cognizant/TaskManagementSystem/
│   │   │   ├── config/
│   │   │   │   └── SegurityConfig.java
│   │   │   ├── controllers/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── LoginController.java
│   │   │   │   ├── SignUpController.java
│   │   │   │   ├── TaskController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   ├── JwtResponse.java
│   │   │   │   └── LoginRequest.java
│   │   │   ├── exceptions/
│   │   │   │   ├── InvalidRequestException.java
│   │   │   │   ├── NotFoundException.java
│   │   │   │   ├── ServerErrorException.java
│   │   │   │   └── UnauthorizedAccessException.java
│   │   │   ├── filters/
│   │   │   │   ├── JwtFilter.java
│   │   │   │   └── RequestLoggingFilter.java
│   │   │   ├── models/
│   │   │   │   ├── Task.java
│   │   │   │   └── User.java
│   │   │   ├── repositories/
│   │   │   │   ├── TaskRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── security/
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── UserSecurity.java
│   │   │   ├── services/
│   │   │   │   ├── TaskService.java
│   │   │   │   ├── UserDetailsService.java
│   │   │   │   └── UserService.java
│   │   │   └── TaskManagementSystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/cognizant/TaskManagementSystemApplicationTests.java
├── build.gradle
├── .gitignore
└── README.md
```


## API Endpoints
| Method | Endpoint                   | Description                   | Authorized    |
|--------|----------------------------|-------------------------------|---------------|
| POST   | `/signup`                  | Sign up                       | Any           |
| POST   | `/login`                   | Log in                        | Any           |
| GET    | `/tasks`                   | List all tasks                | Admin         |
| POST   | `/tasks/{userId}`          | Create a new task for _USER_  | _USER_        |
| GET    | `/tasks/{userId}/{taskid}` | Get _USER_'s task by ID       | Admin, _USER_ |
| PUT    | `/tasks/{userId}/{taskid}` | Update _USER_'s task          | _USER_        |
| DELETE | `/tasks/{userId}/{taskid}` | Delete _USER_'s task          | Admin, _USER_ |
| GET    | `/users`                   | List all users an their tasks | Admin         |
| GET    | `/users/{userId}`          | Get _USER_ by ID              | Admin, _USER_ |
| PUT    | `/users/{userId}`          | Update _USER_ details         | _USER_        |
| DELETE | `/users/{userId}`          | Delete _USER_                 | Admin, _USER_ |

You can test these endpoints through the project's `openAPI.yaml` file.


## Getting Started
1. Clone the repository.
2. Configure `application.properties` as needed.
3. Build and run through `TaskManagementSystemApplication.java`.
4. Ensure MySQL is running and the database `task_management_db` is created.
5. Test API endpoints using tools like Postman or cURL (`PORT 8080`) or through the project's `openAPI.yaml` file.

**Remember to set up the MySQL database and adjust the connection settings in `application.properties` as necessary.*