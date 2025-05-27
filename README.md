# User Management System

## Introduction
The current project contains the configuration and functionality
to run a user management system through the use of
RESTful APIs with additional logging functionality
and profile-specific configurations.

## Setup
To run this project, follow the steps below:
1. Clone the repository
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   * Alternatively, you can run the application
   through the `UserManagementSystemApplication` class.

## API Endpoints
The project features the following API endpoints:
### Create User
*Sample request*:
```
    POST /api/users
```
```json
{
  "username": "adam_hi_87",
  "password": "$ecret123",
  "role": "admin"
} //Request body
  ```
*Response:*
  ```
    HTTP RESPONSE = 200 (OK)
  ```
<br>

### Get All Users
*Sample request*:
  ```
    GET /api/users
  ```
*Response:*
  ```json
  [
    {
      "id": 1,
      "username": "adam_hi_87",
      "password": "$ecret123",
      "role": "admin"
    }
  ]
  ```
<br>

### Get User
*Sample request*:
  ```
    GET /api/users/1
  ```
*Response:*
  ```json
  {
    "id": 1,
    "username": "adam_hi_87",
    "password": "$ecret123", 
    "role": "admin"
  }
  ```
<br>

### Update User
*Sample request*:
  ```
    PUT /api/users/1?user=new_me&password=newPa$$123
  ```
*Response:*
  ```
    200 (OK)
  ```
*Follow-up request:*
  ```
    GET /api/users/1
  ```
*Follow-up response:*
  ```json
  {
    "id": 1,
    "username": "new_me",
    "password": "newPa$$123", 
    "role": "admin"
  }
  ```
<br>

### Delete User
*Sample request*:
  ```
    DELETE /api/users/1
  ```
*Response:*
  ```
    204 (No Content)
  ```
*Follow-up request:*
  ```
    GET /api/users
  ```
*Follow-up response:*
  ```json
  []
  ```
<br>

## Profile-Specific Configurations
The project also contains tailored configuration
settings for three separate Spring profiles: `dev`,
`test`, and `prod`. Here are the specifications for
each profile:
### Development Profile (`dev`)
- **Logging Level:** DEBUG
- **Output:** Console logging enabled
- **Usage:** `spring.profiles.active=dev`

### Testing Profile (`test`)
- **Logging Level:** INFO (no additivity)
- **Output:** Console logging enabled
- **Root** logging: Disabled
- **Usage:** `spring.profiles.active=test`

### Production Profile (`prod`)
- **Logging Level:** WARN
- **Output:** File-based logging
- **Log file:** logs/app.log
- **Log rotation:** Daily with 30 days retention
- **Usage:** `spring.profiles.active=prod`