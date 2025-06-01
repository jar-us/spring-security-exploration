# Database Exploration Demo

This project demonstrates database-backed authentication and authorization with Spring Security.

## Overview

The Database Exploration Demo showcases how to implement security in a Spring Boot application using a database to store
user credentials and role information. It uses Spring Security, Spring Data JPA, and Thymeleaf to create a complete
authentication and authorization workflow.

## Features

- Database-backed authentication using Spring Security and JPA
- User and role management with different access levels
- Custom login page with Thymeleaf templates
- BCrypt password encoding for secure credential storage
- Role-based access control with protected endpoints

## Project Structure

- `src/main/java/jar/us/databaseexplorationdemo`
    - `DatabaseExplorationDemoApplication.java` - Main application class
    - `SecurityConfig.java` - Spring Security configuration
    - `User.java` - Entity representing users with authentication info
    - `UserRepository.java` - JPA repository for user data
    - `WebController.java` - Controller for handling web requests
- `src/main/resources`
    - `templates/` - Thymeleaf templates for UI
    - `schema.sql` - SQL script for creating the user table
    - `data.sql` - SQL script for initializing test users and roles

## Getting Started

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
../gradlew bootRun
```

4. Open your browser and go to `http://localhost:8082`

## Test Users

The application comes with two pre-configured users:

1. Regular User
    - Username: `user`
    - Password: `password123`
    - Roles: `ROLE_USER`

2. Administrator
    - Username: `admin`
    - Password: `password123`
    - Roles: `ROLE_USER`, `ROLE_ADMIN`

## Pages and Access Control

- `/` and `/home` - Public access (no authentication required)
- `/dashboard` - Requires authentication (any authenticated user)
- `/admin` - Requires ADMIN role

## Important Implementation Notes

1. User authentication information is stored in the database
2. A custom `UserDetailsService` loads user data from the database
3. Passwords are encoded with BCrypt for security
4. The Thymeleaf Spring Security integration shows authentication details in the UI
5. The H2 in-memory database is used for development purposes

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf with Spring Security integration
- H2 Database
- Lombok

## Important Configuration

Make sure to include the Thymeleaf Spring Security extras dependency to properly display authentication information in
templates:

```textmate
implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
```

Without this dependency, the `sec:` namespace attributes in Thymeleaf templates won't function correctly.


## Generating BCrypt Passwords

When creating new users, passwords should be stored in BCrypt encoded format. You can generate BCrypt passwords using the Spring Boot CLI:

1. Make sure you have Spring Boot CLI installed:
   ```bash
   # Homebrew (macOS)
   brew install spring-boot

   # SDKMAN
   sdk install springboot
   ```

2. Use the Spring CLI to encode a password:
   ```bash
   spring encodepassword YourPasswordHere
   ```

3. The CLI will output the BCrypt encoded password that you can use in your application:
   ```
   {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
   ```

4. You can use the encoded password in your `data.sql` file or when creating users programmatically:
   ```sql
   INSERT INTO users (username, password, role) VALUES ('newuser', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ROLE_USER');
   ```

## Additional Resources

For a comprehensive guide on Spring Security implementation patterns and best practices, refer to our detailed documentation:

[Spring Security Implementation Patterns](https://docs.google.com/document/d/1-znTlqOMOscj2BuxpnQw2bKGLwH9kTbs64Rdm8V0CNg/edit?usp=sharing)
