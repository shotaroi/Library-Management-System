# Library Management System (Spring Boot)

A backend-focused Library Management System built with Spring Boot, showcasing REST API design, JWT-based authentication, Spring Security, and JPA/Hibernate with PostgreSQL.

This project is designed as a portfolio backend application, demonstrating real-world patterns such as layered architecture, DTO mapping, validation, and stateless authentication.

## Features

### Core Domain
- Manage Books, Authors, Categories
- Track Loans (borrow / return books)
- Prevent borrowing a book that is already loaned out
- Availability tracking per book

### Authentication & Security
- JWT-based authentication
- Stateless API (no sessions)
- Spring Security filter chain with custom JWT filter
- In-memory users for demo purposes
- Protected endpoints with access control

### API & Developer Experience
- RESTful endpoints
- DTO-based request/response models
- Bean Validation (`@NotNull`, `@Size`, etc.)
- Global exception handling
- Swagger / OpenAPI documentation
- Actuator health endpoint

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA (Hibernate)
- Spring Security
- JWT (jjwt)
- PostgreSQL
- Swagger / OpenAPI (springdoc)
- Maven

## Architecture Overview

```text
controller  →  service  →  repository  →  database
     ↓            ↓
    DTOs       business rules
