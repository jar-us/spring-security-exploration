# Spring Security Exploration

This repository contains multiple Spring Boot applications designed to illustrate and explore various concepts within Spring Security. Each subproject focuses on a specific aspect of Spring Security, demonstrating why predictable component execution is vital in complex frameworks and how Spring provides mechanisms to achieve it.

## Subprojects

1. **filter-ordering-demo**: Explores the critical concept of filter ordering within Spring Security.
2. **once-per-request-filter-demo**: Demonstrates the usage and benefits of OncePerRequestFilter in Spring Security.

## Table of Contents

- [Spring Security Exploration](#spring-security-exploration)
    - [Subprojects](#subprojects)
    - [Table of Contents](#table-of-contents)
    - [Project Overview](#project-overview)
    - [Subproject Details](#subproject-details)
        - [Filter Ordering Demo](#filter-ordering-demo)
        - [Once Per Request Filter Demo](#once-per-request-filter-demo)
    - [How to Run the Applications](#how-to-run-the-applications)
        - [Prerequisites](#prerequisites)
        - [Steps](#steps)
    - [Technologies Used](#technologies-used)
    - [License](#license)

## Project Overview

This project serves as a hands-on guide to understanding various aspects of Spring Security. It addresses common challenges in implementing security in Spring applications and demonstrates how Spring Security provides mechanisms to build robust and secure systems.

## Subproject Details

### Filter Ordering Demo

The filter-ordering-demo subproject focuses on the critical concept of filter ordering within Spring Security. It demonstrates:

* **The Problem of Non-Deterministic Ordering:** How components with the same default precedence can lead to unpredictable execution sequences.
* **The Solution: Explicit Ordering:** How assigning specific precedence values and using Spring's ordering mechanisms resolve this unpredictability.
* **Ordering Mechanisms in Spring Security:**
    * `@Order` Annotation: For assigning an integer order value to a component.
    * `Ordered` Interface: An alternative for defining component order programmatically.
    * `HttpSecurity.addFilterBefore/After/At` Methods: For precise placement of custom filters relative to known Spring Security filters within the filter chain.

**Real-World Use Cases:**

* **API Gateway / Microservice Edge Authentication & Request Enrichment:** Ensuring user details are injected into requests *after* successful authentication.
* **Custom Rate Limiting / DDoS Protection Integration:** Applying granular rate limits based on authenticated user attributes.
* **Multi-Tenancy Resolution for Database Routing:** Dynamically routing database operations to the correct tenant's data source *after* tenant identification.
* **Custom CSRF Token Validation and Management:** Integrating custom CSRF logic precisely within the security chain.

For a comprehensive explanation of filter ordering concepts, refer to the **[Comprehensive Guide to Spring Security Filter Ordering](https://docs.google.com/document/d/1mAi2odczzOpJoWO3NAo4jUo6HOoSlgE3Q1H6ZExWhOo/edit?usp=sharing)**.

### Once Per Request Filter Demo

The once-per-request-filter-demo subproject demonstrates the usage and benefits of OncePerRequestFilter in Spring Security. It showcases:

* **The Problem of Multiple Filter Execution:** How standard filters can be executed multiple times in certain scenarios.
* **The Solution: OncePerRequestFilter:** How this specialized filter ensures exactly one execution per request.
* **Key Features of OncePerRequestFilter:**
    * Guaranteed single execution per request
    * Conditional filtering with `shouldNotFilter()`
    * HTTP-specific request/response handling

**Real-World Use Cases:**

* **Authentication Token Validation:** Ensuring JWT or OAuth token validation happens exactly once per request.
* **Request Logging and Auditing:** Capturing request details for security auditing without duplicate entries.
* **Cross-Origin Resource Sharing (CORS):** Applying CORS headers consistently without duplication.
* **Request Transformation:** Modifying request attributes or headers exactly once before processing.

## How to Run the Applications

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Gradle (version specified in `gradle/wrapper/gradle-wrapper.properties` or higher)

### Steps

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/spring-security-exploration.git](https://github.com/YOUR_USERNAME/spring-security-exploration.git)
    cd spring-security-exploration
    ```
    *(Replace `YOUR_USERNAME` with your actual GitHub username or the repository's path)*

2.  **Build All Projects:**
    ```bash
    ./gradlew clean build
    ```
    *(For Windows, use `gradlew.bat clean build`)*

3.  **Run a Specific Subproject:**

    **Filter Ordering Demo:**
    ```bash
    ./gradlew filter-ordering-demo:bootRun
    ```
    *(For Windows, use `gradlew.bat filter-ordering-demo:bootRun`)*

    This application runs on port 8080 by default.

    **Once Per Request Filter Demo:**
    ```bash
    ./gradlew once-per-request-filter-demo:bootRun
    ```
    *(For Windows, use `gradlew.bat once-per-request-filter-demo:bootRun`)*

    This application runs on port 8081 by default.

    Alternatively, you can run either application directly from your IDE by locating the respective main application class and executing its `main` method.

4.  **Exploring the Applications:**

    **Filter Ordering Demo (port 8080):**
    * Access `http://localhost:8080/` - You'll be redirected to a login page
    * Use username `admin` and password `admin123` to log in
    * Access `http://localhost:8080/public` - Publicly accessible without authentication
    * Access `http://localhost:8080/private` - Requires authentication
    * Observe the console logs to see the filter ordering in action

    **Once Per Request Filter Demo (port 8081):**
    * Access `http://localhost:8081/` - You'll be redirected to a login page
    * Use username `admin` and password `admin123` to log in
    * Access `http://localhost:8081/public` - Publicly accessible and bypasses the OncePerRequestFilter (due to shouldNotFilter)
    * Access `http://localhost:8081/private` - Requires authentication and passes through the OncePerRequestFilter
    * Observe the console logs to see the OncePerRequestFilter in action

## Technologies Used

* **Spring Boot:** 3.x
* **Spring Security:** 6.x
* **Java:** 17
* **Gradle:** For project management and build automation
* **Lombok:** (Optional) For reducing boilerplate code

## License

This project is open-sourced under the MIT License. See the `LICENSE` file for more details.
