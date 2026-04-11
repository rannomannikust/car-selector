# Car Selector Application

A Spring Boot based web application for managing and selecting car brands with a hierarchical (recursive) structure. This project was developed as a technical assignment, focusing on clean code, architectural integrity, and automated testing.

![CI Status](https://github.com/rannomannikust/car-selector/actions/workflows/ci.yml/badge.svg)
![Deploy Status](https://github.com/rannomannikust/car-selector/actions/workflows/deploy.yml/badge.svg)

## 🚀 Key Features

* **Hierarchical Car Brands:** Supports multi-level parent-child relationships between car brands (e.g., Audi -> A4).
* **Smart Recursive Rendering:** Uses recursive logic to build a display tree. Indentation is handled at the UI layer for optimal separation of concerns.
* **Internationalization (i18n):** Full support for multi-language brand names using Spring `MessageSource`.
* **User Selection:** A responsive form for users to enter details and select multiple car brands.
* **Robust Validation:** Server-side validation with immediate feedback on the UI.

## 🛠️ Technical Stack

* **Backend:** Java 21, Spring Boot 3.x, Spring Data JPA.
* **Database:** PostgreSQL 16 (containerized).
* **Database Migrations:** Managed by **Flyway** with PostgreSQL-optimized sequence synchronization.
* **Security:** Spring Security with **CSRF protection enabled** and session-based authentication.
* **Frontend:** Thymeleaf templates with Bootstrap 5 and custom CSS for hierarchical rendering.
* **Infrastructure:** Docker, Docker Compose, Traefik Reverse Proxy with automatic SSL.

## 🧪 Quality Assurance & CI/CD

This project follows modern DevOps practices to ensure high code quality.

* **Continuous Inspection:** Automated static code analysis with SonarCloud.
* **Code Formatting:** Strict adherence to coding standards enforced by **Spotless** (Google Java Style).
* **Automated Formatting:** Run `./mvnw spotless:apply` locally to fix any formatting violations.

### Code Coverage: **96%**
The application achieves 96% test coverage, ensuring stability across all layers.

![Code Coverage](docs/images/coverage.png)

* **Service Layer:** JUnit 5 & Mockito tests validating recursive hierarchy and business logic.
* **Web Layer:** Focused `@WebMvcTest` controllers verifying routing, model attributes, and validation logic.
* **Persistence:** Flyway ensures the database schema is consistent across environments.

## CI/CD Pipeline
The project uses an automated **CI/CD pipeline** (GitHub Actions / GitLab CI), which performs the following steps on every push:
1. **Build & Test:** Runs `mvn clean verify` to ensure all tests pass and quality gates are met.
2. **Dockerize:** Builds a production-ready Docker image.
3. **Deploy:** Automatically updates the live environment at `carselector.mannikust.ee`.

## 🌍 Environment Comparison

| Feature           | Local Development          | Production (Live)               |
|-------------------|----------------------------|---------------------------------|
| **Access**        | http://localhost:8080      | https://carselector.mannikust.ee |
| **Protocol**      | HTTP                       | HTTPS (SSL by Let's Encrypt)    |
| **Database**      | PostgreSQL                 | Managed PostgreSQL container    |
| **Security**      | CSRF Enabled               | CSRF Enabled + Traefik Shield   |

## 📂 Project Structure

* `src/main/java/.../service`: Core logic for hierarchical data processing.
* `src/main/java/.../controller`: Web controllers and global exception handling.
* `src/main/resources/db/migration`: SQL scripts for schema and data seeding.
* `src/test/java/...`: Comprehensive test suite (96% coverage).
---
Developed by **Ranno Männikust**, March 2026.

# Documentation & Architecture

## 1. Data Model 

The application uses a self-referencing relational model.
* **CarBrand Entity:** Features a `parent_id` foreign key referencing its own primary key, allowing for infinite nesting levels.
* **Recursive Logic:** Top-level brands (`parent_id IS NULL`) act as roots, with models and sub-models branching out as children.

## 2. Separation of Concerns

A key architectural decision was implemented to decouple logic from presentation:
* **Service Layer:** Focuses purely on data and translations, providing a `level` attribute in the DTO.
* **View Layer:** Thymeleaf and CSS (`white-space: pre;`) handle the visual indentation, ensuring the backend remains agnostic of the display implementation.

## 3. Database Resilience

To handle manual ID inserts in PostgreSQL, the system includes specific sequence synchronization:
```sql
SELECT setval('car_brands_id_seq', (SELECT MAX(id) FROM car_brands));

This ensures that the primary key sequences stay in sync after initial data seeding, preventing UniqueConstraint violations during runtime.

## 4. User Guide
- Browsing: Hierarchical list with clear visual nesting.
- Selection: Multi-select functionality that correctly maps complex hierarchies to the user selection DTO.
- Security: Every form submission is protected by a CSRF token automatically managed by Spring Security and Thymeleaf.

# CI/CD & Automation
The project employs a sophisticated automation pipeline to guarantee code quality and seamless delivery:

** 1. Continuous Inspection (CI) **

- Every push to any branch triggers the Code Quality workflow:
- Checkstyle & Formatting: Enforced via Spotless (Google Java Style) to maintain a unified codebase.
- Automated Testing: Runs the full JUnit 5 / Mockito test suite.
- Security & Quality Gate: Integrated with SonarCloud to analyze code smells, security vulnerabilities, and maintain technical debt.

** 2. Continuous Deployment (CD) **

- Upon merging into the main branch, the deployment pipeline automates the release process:
- Containerization: Builds a production-ready Docker image using JDK 21.
- Registry: Pushes the versioned image to the GitHub Container Registry (GHCR).
- Remote Deployment: Automatically synchronizes the environment on a remote VM via SSH:
- Updates docker-compose configurations.
- Pulls the latest image and performs a rolling restart to minimize downtime.
- Cleans up orphaned containers to maintain server health.

## 📦 Deployment
### 🛠️ Developer Quick Start
Getting started is as simple as:
1. Clone the repository git@github.com:rannomannikust/car-selector.git. 
2. Run `docker-compose up -d`.
3. The app is live at `http://localhost:8080` with the database automatically migrated by Flyway.

The application is fully containerized.

** How to format code locally? **
If the CI/CD pipeline fails due to formatting issues (e.g., incorrect indentation or trailing spaces), run the following command in your terminal to automatically fix the violations:

```bash
./mvnw spotless:apply .




