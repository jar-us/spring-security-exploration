# Spring Security Filter Ordering Demo

This repository contains a practical Spring Boot application designed to illustrate and explore the critical concept of filter ordering within Spring Security. It demonstrates why predictable component execution is vital in complex frameworks and how Spring provides mechanisms to achieve it.

## Table of Contents

- [Spring Security Filter Ordering Demo](#spring-security-filter-ordering-demo)
    - [Table of Contents](#table-of-contents)
    - [Project Overview](#project-overview)
    - [Core Concepts Demonstrated](#core-concepts-demonstrated)
    - [For a Deeper Dive](#for-a-deeper-dive)
    - [Real-World Use Cases Explored](#real-world-use-cases-explored)
    - [How to Run the Application](#how-to-run-the-application)
        - [Prerequisites](#prerequisites)
        - [Steps](#steps)
    - [How to Explore the Examples](#how-to-explore-the-examples)
        - [Default Spring Security Behavior](#default-spring-security-behavior)
        - [Observing the Filter Chain](#observing-the-filter-chain)
        - [Custom Filter Placement (`addFilter*` methods)](#custom-filter-placement-addfilter-methods)
        - [Custom Filter Ordering (`@Order` and `Ordered`)](#custom-filter-ordering-order-and-ordered)
    - [Technologies Used](#technologies-used)
    - [License](#license)

## Project Overview

This project serves as a hands-on guide to understanding filter ordering in Spring Security. It addresses the problem of non-deterministic component execution, where the order of framework-internal and custom components can be unpredictable without explicit control. The application showcases how Spring's ordering mechanisms provide the necessary control for building robust and secure systems.

## Core Concepts Demonstrated

* **The Problem of Non-Deterministic Ordering:** How components with the same default precedence can lead to unpredictable execution sequences.
* **The Solution: Explicit Ordering:** How assigning specific precedence values and using Spring's ordering mechanisms resolve this unpredictability.
* **Ordering Mechanisms in Spring Security:**
    * `@Order` Annotation: For assigning an integer order value to a component.
    * `Ordered` Interface: An alternative for defining component order programmatically.
    * `HttpSecurity.addFilterBefore/After/At` Methods: For precise placement of custom filters relative to known Spring Security filters within the filter chain.

---

## For a Deeper Dive

For a comprehensive explanation of the concepts, problem, solution, and detailed analysis of Spring Security filter ordering, refer to the **[Comprehensive Guide to Spring Security Filter Ordering](https://docs.google.com/document/d/1mAi2odczzOpJoWO3NAo4jUo6HOoSlgE3Q1H6ZExWhOo/edit?usp=sharing)**.

---

## Real-World Use Cases Explored

The examples in this project are designed to provide practical insights into scenarios where filter ordering is crucial:

* **API Gateway / Microservice Edge Authentication & Request Enrichment:** Ensuring user details are injected into requests *after* successful authentication.
* **Custom Rate Limiting / DDoS Protection Integration:** Applying granular rate limits based on authenticated user attributes.
* **Multi-Tenancy Resolution for Database Routing:** Dynamically routing database operations to the correct tenant's data source *after* tenant identification.
* **Custom CSRF Token Validation and Management:** Integrating custom CSRF logic precisely within the security chain.

## How to Run the Application

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Gradle (version specified in `gradle/wrapper/gradle-wrapper.properties` or higher)

### Steps

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/security-ordering-demo.git](https://github.com/YOUR_USERNAME/security-ordering-demo.git)
    cd security-ordering-demo
    ```
    *(Replace `YOUR_USERNAME` with your actual GitHub username or the repository's path)*

2.  **Build the Project:**
    ```bash
    ./gradlew clean build
    ```
    *(For Windows, use `gradlew.bat clean build`)*

3.  **Run the Application:**
    You can run the Spring Boot application using the Gradle wrapper:
    ```bash
    ./gradlew bootRun
    ```
    *(For Windows, use `gradlew.bat bootRun`)*

    Alternatively, you can run it directly from your IDE by locating the `SecurityOrderingDemoApplication.java` file (in `src/main/java/com/example/securityorderingdemo`) and executing its `main` method.

## How to Explore the Examples

Once the application is running (typically on `http://localhost:8080`), observe your console output and interact with the application as described below.

### Default Spring Security Behavior

* Upon startup, Spring Security will generate a default password for the `user` account. Look for a line similar to: `Using generated security password: [a long alphanumeric string]`.
* Access `http://localhost:8080/`. You will be redirected to a login page. Use username `user` and the generated password.
* Access `http://localhost:8080/public`. This endpoint is configured to be publicly accessible without authentication.
* Access `http://localhost:8080/private`. This endpoint requires authentication.

### Observing the Filter Chain

* When the application starts, the console output will display a detailed log of the Spring Security filter chain, including your custom filters, under the heading `--- Spring Security Filter Chain Order ---`. This provides a static view of the configured order.

### Custom Filter Placement (`addFilter*` methods)

* The `SecurityConfig.java` file demonstrates the use of `http.addFilterBefore()`, `http.addFilterAt()`, and `http.addFilterAfter()` to place `MyCustomFilter` instances relative to core Spring Security filters like `UsernamePasswordAuthenticationFilter` and `AuthorizationFilter`.
* Access `http://localhost:8080/private` (log in if necessary). Observe the console logs generated by `MyCustomFilter` instances. Their "Before processing" and "After processing" messages will appear in the order defined by these methods, relative to other Spring Security logs.

### Custom Filter Ordering (`@Order` and `Ordered`)

* The `MyOrderedFilterA.java` (using `@Order(2)`) and `MyOrderedFilterB.java` (implementing `Ordered` with `getOrder() = 1`) are registered as general Servlet filters.
* **Important:** You will *not* see these filters listed in the `--- Spring Security Filter Chain Order ---` output. This is because they operate at a higher level in the Servlet filter chain, *before* Spring Security's main `FilterChainProxy` takes over.
* Access any endpoint (e.g., `http://localhost:8080/private`). Observe the very first logs in your console output. You should see `MyOrderedFilterB`'s logs appearing *before* `MyOrderedFilterA`'s logs, demonstrating that a lower order value (1 vs. 2) results in earlier execution.

## Technologies Used

* **Spring Boot:** 3.x
* **Spring Security:** 6.x
* **Java:** 17
* **Gradle:** For project management and build automation
* **Lombok:** (Optional) For reducing boilerplate code

## License

This project is open-sourced under the MIT License. See the `LICENSE` file for more details.