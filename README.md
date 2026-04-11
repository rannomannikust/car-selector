# 🚗 Car Selector Application

A Spring Boot–based web application for managing and selecting car brands with a hierarchical (recursive) structure.
The project emphasizes clean architecture, separation of concerns, testability, and fully automated CI/CD.


![CI Status](https://github.com/rannomannikust/car-selector/actions/workflows/ci.yml/badge.svg)
![Deploy Status](https://github.com/rannomannikust/car-selector/actions/workflows/deploy.yml/badge.svg)

## 🚀 Key Features

- Hierarchical Car Brands — unlimited parent–child nesting (e.g., Audi → Q‑seeria → Q5)
- Clean Recursive Rendering — backend provides pure data (stepLevel), UI handles indentation
- Internationalization (i18n) — brand names translated via Spring MessageSource
- User Selection Form — multi-select with validation and CSRF protection
- Robust Validation — server-side validation with clear UI feedback


## 🛠️ Technical Stack

* **Backend:** Java 21, Spring Boot 3.x, Spring Data JPA.
* **Database:** PostgreSQL 16 (containerized).
* **Database Migrations:** Managed by **Flyway** with PostgreSQL-optimized sequence synchronization.
* **Security:** Spring Security (session-based, CSRF enabled).
* **Frontend:** Thymeleaf templates with Bootstrap 5 and custom CSS for hierarchical rendering.
* **Infrastructure:** Docker, Docker Compose, Traefik Reverse Proxy with automatic SSL.

## 🧪 Quality Assurance & CI/CD

- Static Analysis: SonarCloud
- Formatting: Spotless (Google Java Style)
- Testing:
- Service layer: JUnit 5 + Mockito
- Web layer: @WebMvcTest
- Database schema: Flyway migrations validated on startup


### 📊 Code Coverage: **96%**
The application achieves 96% test coverage, ensuring stability across all layers.

![Code Coverage](docs/images/coverage.png)

* **Service Layer:** JUnit 5 & Mockito tests validating recursive hierarchy and business logic.
* **Web Layer:** Focused `@WebMvcTest` controllers verifying routing, model attributes, and validation logic.
* **Persistence:** Flyway ensures the database schema is consistent across environments.

## 🔄 CI/CD Pipeline
Every push triggers:
- Build & Test — mvn clean verify
- Docker Image Build — production-ready image (JDK 21)
- Deployment
- Image pushed to GHCR
- Remote VM updated via SSH
- Traefik handles routing + SSL
- Zero-downtime rolling restart


## 🌍 Environments

| Feature           | Local Development          | Production (Live)               |
|-------------------|----------------------------|---------------------------------|
| **Access**        | http://localhost:8080      | https://carselector.mannikust.ee |
| **Protocol**      | HTTP                       | HTTPS (SSL by Let's Encrypt)    |
| **Database**      | PostgreSQL                 | Managed PostgreSQL container    |
| **Security**      | CSRF Enabled               | CSRF Enabled + Traefik Shield   |

## 📂 Project Structure


src/
 ├─ main/
 │   ├─ java/.../controller     → Web controllers
 │   ├─ java/.../service        → Hierarchy logic, translations
 │   ├─ java/.../dto            → Pure data transfer objects
 │   ├─ resources/templates     → Thymeleaf views
 │   └─ resources/db/migration  → Flyway SQL scripts
 └─ test/
     └─ java/...                → 96% coverage test suite


# 🧱 Architecture & Design
## 1. Data Model
The CarBrand entity is self-referencing:
- parent_id → points to another CarBrand
- allows infinite nesting
- top-level brands have parent_id = NULL



## 2. Separation of Concerns
- Service layer
Returns brandName + stepLevel (hierarchy depth).
No HTML, no indentation, no UI logic.
- View layer
Thymeleaf renders indentation:

``` Html
th:text="${'   '.repeat(brand.stepLevel) + brand.brandName}"
```
- CSS
Preserves whitespace:

``` Css 
select option {
    white-space: pre;
}
```


## 3. Database Resilience

To handle manual ID inserts in PostgreSQL, the system includes sequence synchronization:

 ```sql 
  SELECT setval('car_brands_id_seq', (SELECT MAX(id) FROM car_brands));
  ```

## 4. Security

- CSRF protection enabled
- Thymeleaf auto-injects CSRF tokens
- Traefik handles TLS terminatio

# User Guide

- Browse: hierarchical dropdown with indentation
- Select: multi-select supports any depth
- Submit: CSRF-protected forms
- Validation: inline error messages

## 🛠️ Developer Quick Start

``` Bash
git clone git@github.com:rannomannikust/car-selector.git
docker-compose up -d
```

The app becomes available at:

```
http://localhost:8080

```

Flyway automatically initializes the database.

``` Bash
./mvnw spotless:apply
```

👤 Author
Developed by Ranno Männikust, March 2026.





