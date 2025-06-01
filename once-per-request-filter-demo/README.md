# Once Per Request Filter Demo

This subproject, part of the larger **Spring Security Exploration** repository, is focused on demonstrating the usage and advantages of `OncePerRequestFilter` in Spring Security.

## Overview

The `OncePerRequestFilter` is a specialized filter in the Spring framework that ensures a filter's logic is executed exactly once per request. This is crucial in scenarios where multiple filter invocations can lead to redundant processing or inconsistent behavior.

### Key Features

- **Single Execution Per Request**: Ensures the filter logic runs only once, even in environments with forwards, includes, or async dispatches.
- **Customizable Exclusions**: Use the `shouldNotFilter()` method to exclude specific paths from being processed by the filter.
- **Flexible and HTTP-Specific**: Designed to handle HTTP-specific requests and responses.

## Features in This Demo

This demo provides:
1. A custom implementation of `OncePerRequestFilter` that logs each request's URI and execution count.
2. A demonstration of how the `shouldNotFilter()` method can be overridden to exclude certain requests.
3. Insights into ensuring idempotent, predictable filtering behavior across requests.

### Real-World Use Cases

- **Authentication and Token Validation**: Ensures validation of JWT or OAuth tokens happens exactly once per request, even if other parts of the application forward or reuse the request.
- **Audit Logging**: Captures request details for security and compliance without redundant entries.
- **CORS**: Consistently applies CORS headers in applications.
- **Request Transformation**: Modifies request attributes or headers only once before processing.

## Project Structure

Here's a breakdown of the subproject structure:

## How It Works

The custom filter (`MyCustomOncePerRequestFilter`) extends `OncePerRequestFilter` and overrides the following methods:
- **`doFilterInternal()`**: Contains the core logic for the filter, such as logging request details.
- **`shouldNotFilter()`** *(optional)*: Can be overridden to exclude certain paths from being processed by the filter.
- **`getAlreadyFilteredAttributeName()`** *(optional)*: Defines a unique attribute name to determine if the filter has already been applied.

### Logs

When the application is running, every request passing through this filter will log its URI along with an execution count, demonstrating the single execution per request.

## How to Run the Application

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Gradle (wrapped version in the repository)

### Steps to Run

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/spring-security-exploration.git
   cd spring-security-exploration
   ```

2. **Navigate to the Subproject's Directory:**
   ```bash
   cd once-per-request-filter-demo
   ```

3. **Build and Run:**
   ```bash
   ../gradlew bootRun
   ```

   *(For Windows, use `..\\gradlew.bat bootRun`)*

4. **Access the Application:**
    - The application runs on `http://localhost:8081` by default.
    - Test different endpoints (e.g., `/public`, `/private`) and observe the behavior of the custom filter in the console logs.

## Technologies Used

- **Spring Boot:** 3.x
- **Spring Security:** 6.x
- **Java:** 17
- **Gradle:** For dependency and build management
- **Lombok:** *(optional)* Used for reducing boilerplate code

## License

This subproject is part of the larger repository which is open-sourced under the MIT License. See the repository's `LICENSE` file for more details.