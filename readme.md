# Let's Play 🎮

A secure RESTful CRUD API built with **Spring Boot**, **MongoDB**, **Spring Security**, and **JWT authentication**.

Let's Play is a small e-commerce-like backend platform where users can create and manage products. Administrators have broader permissions and can manage users and products across the platform.

---

## 📋 Project Overview

**Let's Play** is a backend REST API designed to demonstrate modern backend development practices with Spring Boot.

The application provides:

* User registration and authentication
* JWT-based authentication
* Role-based authorization
* User and product CRUD operations
* Product ownership management
* Secure password hashing with BCrypt
* MongoDB persistence
* Global exception handling
* Structured HTTP error responses
* HTTPS support
* Input validation
* Secure API access control

The application follows a layered architecture:

```text
Client
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
  │
  ▼
MongoDB
```

Security is handled through Spring Security and a custom JWT authentication filter.

---

# 🏗️ Architecture

The project follows a layered architecture to separate responsibilities.

```text
┌──────────────────────────────┐
│            Client            │
│      Postman / Frontend      │
└──────────────┬───────────────┘
               │ HTTP / HTTPS
               ▼
┌──────────────────────────────┐
│         Controllers          │
│  AuthController              │
│  UserController              │
│  ProductController           │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│           Services           │
│  AuthService                 │
│  UserService                 │
│  ProductService              │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│         Repositories         │
│  UserRepository              │
│  ProductRepository           │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│           MongoDB            │
└──────────────────────────────┘
```

Security is applied before requests reach protected controllers:

```text
Request
   │
   ▼
JWT Authentication Filter
   │
   ├── No token ───────────────► 401 Unauthorized
   │
   ├── Invalid token ──────────► 401 Unauthorized
   │
   ▼
SecurityContext
   │
   ├── Insufficient permission ► 403 Forbidden
   │
   ▼
Controller
```

---

# 🛠️ Technologies

| Technology          | Purpose                        |
| ------------------- | ------------------------------ |
| Java                | Programming language           |
| Spring Boot         | Backend framework              |
| Spring Web          | REST API                       |
| Spring Security     | Authentication & authorization |
| JWT                 | Stateless authentication       |
| MongoDB             | NoSQL database                 |
| Spring Data MongoDB | Database access                |
| BCrypt              | Password hashing               |
| Gradle              | Build tool                     |
| Docker              | MongoDB container              |
| HTTPS               | Secure communication           |

---

# 📁 Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/example/lets_play/
    │       │
    │       ├── config/
    │       │   ├── SecurityConfig.java
    │       │   └── JwtAuthenticationFilter.java
    │       │
    │       ├── controller/
    │       │   ├── AuthController.java
    │       │   ├── UserController.java
    │       │   └── ProductController.java
    │       │
    │       ├── dto/
    │       │   ├── AuthResponse.java
    │       │   ├── Loginrequest.java
    │       │   ├── Registe.java
    │       │   ├── UserInfo.java
    │       │   ├── UserResponse.java
    │       │   └── ProductRequest.java
    │       │
    │       ├── exception/
    │       │   ├── ErrorResponse.java
    │       │   ├── GlobalExceptionHandler.java
    │       │   ├── BadRequestException.java
    │       │   ├── ConflictException.java
    │       │   ├── ForbiddenException.java
    │       │   └── ResourceNotFoundException.java
    │       │
    │       ├── model/
    │       │   ├── User.java
    │       │   ├── Product.java
    │       │   └── Role.java
    │       │
    │       ├── repository/
    │       │   ├── UserRepository.java
    │       │   └── ProductRepository.java
    │       │
    │       ├── security/
    │       │   └── JwtService.java
    │       │
    │       └── service/
    │           ├── AuthService.java
    │           ├── UserService.java
    │           └── ProductService.java
    │
    └── resources/
        └── application.properties
```

---

# 🗄️ Database Design

The application contains two main entities:

```text
User
 │
 │ 1
 │
 │ owns
 │
 │ *
 ▼
Product
```

## User

```text
User
├── id
├── name
├── email
├── password
└── role
```

Example:

```json
{
  "id": "6ab520e5e734d8f2522f2270",
  "name": "hamza",
  "email": "hamza@example.com",
  "role": "USER"
}
```

Passwords are **never returned** by the API.

---

## Product

```text
Product
├── id
├── name
├── description
├── price
└── userId
```

Example:

```json
{
  "id": "67abc123",
  "name": "Laptop",
  "description": "Gaming laptop",
  "price": 1200.0,
  "userId": "6ab520e5e734d8f2522f2270"
}
```

The `userId` identifies the owner of the product.

---

# 🔐 Authentication

The application uses **JWT (JSON Web Token)** authentication.

Authentication flow:

```text
1. Register
      │
      ▼
2. Login
      │
      ▼
3. Server verifies password
      │
      ▼
4. Server generates JWT
      │
      ▼
5. Client stores token
      │
      ▼
6. Client sends token with requests
      │
      ▼
Authorization: Bearer <JWT>
```

Example:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

The JWT contains information such as:

```text
Header
Payload
Signature
```

The payload contains:

```text
sub   → user ID
name  → username
email → email
role  → USER / ADMIN
iat   → issued time
exp   → expiration time
```

The server verifies the JWT signature before authenticating the request.

---

# 👥 Roles & Permissions

The application has two roles:

```text
USER
ADMIN
```

## User

A normal user can:

* Register
* Login
* Create products
* View products
* Update their own products
* Delete their own products

A user cannot modify or delete another user's product.

---

## Admin

An administrator can:

* Manage users
* View users
* Create users
* Update users
* Delete users
* Manage products
* Update products
* Delete products

---

# 🔑 Authorization Rules

| Operation                     | Anonymous | USER | ADMIN |
| ----------------------------- | --------: | ---: | ----: |
| Register                      |         ✅ |    ✅ |     ✅ |
| Login                         |         ✅ |    ✅ |     ✅ |
| GET products                  |         ✅ |    ✅ |     ✅ |
| POST product                  |         ❌ |    ✅ |     ✅ |
| PUT own product               |         ❌ |    ✅ |     ✅ |
| DELETE own product            |         ❌ |    ✅ |     ✅ |
| PUT another user's product    |         ❌ |    ❌ |     ✅ |
| DELETE another user's product |         ❌ |    ❌ |     ✅ |
| GET users                     |         ❌ |    ❌ |     ✅ |
| Create user                   |         ❌ |    ❌ |     ✅ |
| Update user                   |         ❌ |    ❌ |     ✅ |
| Delete user                   |         ❌ |    ❌ |     ✅ |

---

# 🌐 API Endpoints

Base URL:

```text
https://localhost:8443
```

> The exact port can be changed in `application.properties`.

---

## Authentication

### Register

```http
POST /api/Auths/register
```

Request:

```json
{
  "name": "hamza",
  "email": "hamza@example.com",
  "password": "password123"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "hamza",
  "role": "USER"
}
```

---

### Login

```http
POST /api/Auths/login
```

Request:

```json
{
  "email": "hamza@example.com",
  "password": "password123"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "hamza",
  "role": "USER"
}
```

Copy the returned token and use it for authenticated requests.

---

# 📦 Products API

## Get all products

```http
GET /api/products
```

Authentication:

```text
Not required
```

Example response:

```json
[
  {
    "id": "123",
    "name": "Laptop",
    "description": "Gaming laptop",
    "price": 1200.0,
    "userId": "456"
  }
]
```

---

## Get product by ID

```http
GET /api/products/{id}
```

Example:

```http
GET /api/products/123
```

Authentication:

```text
Not required
```

---

## Create product

```http
POST /api/products
```

Authentication:

```text
Required
```

Header:

```http
Authorization: Bearer <JWT>
```

Request:

```json
{
  "name": "Laptop",
  "description": "Gaming laptop",
  "price": 1200
}
```

The owner is automatically determined from the authenticated user's JWT.

The client does **not** provide the owner ID.

---

## Update product

```http
PUT /api/products/{id}
```

Authentication:

```text
Required
```

Authorization:

```text
Product owner OR ADMIN
```

Example:

```http
PUT /api/products/123
```

Header:

```http
Authorization: Bearer <JWT>
```

Request:

```json
{
  "name": "Updated Laptop",
  "description": "Updated description",
  "price": 1500
}
```

If the authenticated user is neither the owner nor an administrator:

```http
403 Forbidden
```

---

## Delete product

```http
DELETE /api/products/{id}
```

Authentication:

```text
Required
```

Authorization:

```text
Product owner OR ADMIN
```

Example:

```http
DELETE /api/products/123
```

Header:

```http
Authorization: Bearer <JWT>
```

Successful response:

```http
204 No Content
```

---

# 👤 Users API

User management is restricted to administrators.

## Get all users

```http
GET /api/User
```

Authentication:

```text
Required
```

Authorization:

```text
ADMIN
```

---

## Get user by ID

```http
GET /api/User/{id}
```

Example:

```http
GET /api/User/6ab520e5e734d8f2522f2270
```

Authentication:

```text
Required
```

The response does not contain the user's password.

---

## Create user

```http
POST /api/User
```

Authorization:

```text
ADMIN
```

Request:

```json
{
  "name": "john",
  "email": "john@example.com",
  "password": "password123"
}
```

The password is hashed using BCrypt before being stored.

---

## Update user

```http
PUT /api/User/{id}
```

Authorization:

```text
ADMIN
```

Example:

```http
PUT /api/User/6ab520e5e734d8f2522f2270
```

Request:

```json
{
  "name": "john-updated",
  "email": "john@example.com",
  "password": "newPassword123"
}
```

---

## Delete user

```http
DELETE /api/User/{id}
```

Authorization:

```text
ADMIN
```

Example:

```http
DELETE /api/User/6ab520e5e734d8f2522f2270
```

Successful response:

```http
204 No Content
```

---

# 🚨 HTTP Status Codes

The API uses standard HTTP status codes.

| Status                      | Meaning                                |
| --------------------------- | -------------------------------------- |
| `200 OK`                    | Request successful                     |
| `201 Created`               | Resource successfully created          |
| `204 No Content`            | Resource successfully deleted          |
| `400 Bad Request`           | Invalid request                        |
| `401 Unauthorized`          | Authentication required or JWT invalid |
| `403 Forbidden`             | Authenticated but not authorized       |
| `404 Not Found`             | Resource or endpoint does not exist    |
| `409 Conflict`              | Resource already exists                |
| `500 Internal Server Error` | Unexpected server error                |

---

# ❌ Error Response Format

Errors are handled globally using `@RestControllerAdvice`.

Example:

```json
{
  "timestamp": "2026-09-23T15:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with id: 123",
  "path": "/api/products/123"
}
```

---

## 400 Bad Request

Example:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Email is required.",
  "path": "/api/Auths/register"
}
```

---

## 401 Unauthorized

Returned when authentication is missing or invalid.

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Authentication required",
  "path": "/api/products"
}
```

---

## 403 Forbidden

Returned when the user is authenticated but does not have permission.

Example:

```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "You are not authorized to update this product.",
  "path": "/api/products/123"
}
```

---

## 404 Not Found

Example:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with id: 123",
  "path": "/api/products/123"
}
```

---

## 409 Conflict

Used for duplicate resources.

Example:

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Email already exists.",
  "path": "/api/Auths/register"
}
```

---

# 🔒 Security

## Password Security

Passwords are never stored as plain text.

Before saving:

```text
password
    │
    ▼
BCrypt
    │
    ▼
hashed password
    │
    ▼
MongoDB
```

Example database value:

```text
$2a$10$...
```

The original password cannot be retrieved from the stored hash.

---

## JWT Security

JWTs are signed using a secret key.

The secret should be stored as an environment variable rather than directly in source code.

Example:

```properties
jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
```

The expiration value is in milliseconds.

```text
3600000 ms = 1 hour
```

Example environment variable:

```bash
export JWT_SECRET="your-long-random-secret"
```

Do not commit the real secret to Git.

---

# 🛡️ Request Authentication

Every protected request must include:

```http
Authorization: Bearer <JWT>
```

The custom JWT filter:

1. Reads the `Authorization` header.
2. Checks for the `Bearer` prefix.
3. Extracts the JWT.
4. Validates the signature.
5. Checks token expiration.
6. Extracts the user ID and role.
7. Creates a Spring Security authentication object.
8. Stores the authentication in `SecurityContext`.

Simplified flow:

```text
HTTP Request
     │
     ▼
Authorization Header
     │
     ▼
JWT Filter
     │
     ▼
Validate Signature
     │
     ▼
Check Expiration
     │
     ▼
Extract User ID + Role
     │
     ▼
SecurityContext
     │
     ▼
Controller
```

---

# 🔐 Product Ownership

Products belong to the user who created them.

When a product is created:

```text
Authenticated User
       │
       ▼
JWT → userId
       │
       ▼
Product.userId
```

When updating or deleting:

```text
Request
   │
   ▼
Find product
   │
   ▼
Get product.userId
   │
   ▼
Compare with authenticated userId
   │
   ├── Owner ────────► Allow
   │
   ├── ADMIN ────────► Allow
   │
   └── Other USER ──► 403 Forbidden
```

This prevents users from modifying other users' products.

---

# 🌍 HTTPS

The application supports HTTPS for encrypted communication.

Example:

```text
https://localhost:8443
```

HTTPS protects sensitive information such as:

* Passwords
* JWT tokens
* User information
* Product information

---

# 🐳 MongoDB with Docker

MongoDB can be run using Docker.

Example:

```bash
docker compose up -d
```

Check running containers:

```bash
docker ps
```

Stop the container:

```bash
docker stop <container-name>
```

Remove the container:

```bash
docker rm <container-name>
```

MongoDB is exposed on:

```text
localhost:27017
```

---

# ⚙️ Configuration

Example `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/lets_play

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
```

For development, make sure the `JWT_SECRET` environment variable is configured.

Example:

```bash
export JWT_SECRET="your-secret-key"
```

On Windows PowerShell:

```powershell
$env:JWT_SECRET="your-secret-key"
```

---

# 🚀 Running the Project

## 1. Clone the repository

```bash
git clone <repository-url>
cd lets-play
```

---

## 2. Start MongoDB

Using Docker:

```bash
docker compose up -d
```

Verify:

```bash
docker ps
```

---

## 3. Configure JWT secret

Linux/macOS:

```bash
export JWT_SECRET="your-long-random-secret"
```

Windows PowerShell:

```powershell
$env:JWT_SECRET="your-long-random-secret"
```

---

## 4. Run the application

Using Gradle:

```bash
./gradlew bootRun
```

On Windows:

```bash
gradlew.bat bootRun
```

Or build the project:

```bash
./gradlew build
```

---

# 🧪 Testing with Postman

A typical testing workflow is:

### Step 1 — Register

```http
POST /api/Auths/register
```

```json
{
  "name": "hamza",
  "email": "hamza@example.com",
  "password": "password123"
}
```

---

### Step 2 — Login

```http
POST /api/Auths/login
```

Copy the JWT returned by the server.

---

### Step 3 — Get products

```http
GET /api/products
```

No token is required.

---

### Step 4 — Create product

```http
POST /api/products
```

Add:

```http
Authorization: Bearer <JWT>
```

Body:

```json
{
  "name": "Laptop",
  "description": "Gaming laptop",
  "price": 1200
}
```

---

### Step 5 — Update your product

```http
PUT /api/products/{productId}
```

Header:

```http
Authorization: Bearer <JWT>
```

---

### Step 6 — Delete your product

```http
DELETE /api/products/{productId}
```

Header:

```http
Authorization: Bearer <JWT>
```

---

# 🔄 Complete Authentication Flow

```text
                 ┌──────────────┐
                 │    Client    │
                 └──────┬───────┘
                        │
                 Register/Login
                        │
                        ▼
                 ┌──────────────┐
                 │ AuthService  │
                 └──────┬───────┘
                        │
                 Verify password
                        │
                        ▼
                 ┌──────────────┐
                 │  JwtService  │
                 └──────┬───────┘
                        │
                    Generate JWT
                        │
                        ▼
                 ┌──────────────┐
                 │    Client    │
                 └──────┬───────┘
                        │
               Bearer JWT
                        │
                        ▼
              ┌──────────────────┐
              │ JWT Auth Filter  │
              └────────┬─────────┘
                       │
                 Validate token
                       │
                       ▼
              ┌──────────────────┐
              │ SecurityContext  │
              └────────┬─────────┘
                       │
                       ▼
                 ┌──────────────┐
                 │  Controller  │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │   Service    │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │   MongoDB    │
                 └──────────────┘
```

---

# 🧩 Error Handling Architecture

Application exceptions are handled centrally.

```text
Controller
    │
    ▼
Service
    │
    ├── ResourceNotFoundException
    ├── ForbiddenException
    ├── ConflictException
    └── BadRequestException
             │
             ▼
   GlobalExceptionHandler
             │
             ▼
      ErrorResponse
             │
             ▼
        JSON Response
```

Spring Security errors are handled separately:

```text
Security Filter
      │
      ├── Authentication failure
      │          │
      │          ▼
      │         401
      │
      └── Authorization failure
                 │
                 ▼
                403
```

---

# 📐 REST Design Principles

The API follows REST conventions:

### GET

Used to retrieve resources.

```http
GET /api/products
GET /api/products/{id}
```

### POST

Used to create resources.

```http
POST /api/products
```

### PUT

Used to update resources.

```http
PUT /api/products/{id}
```

### DELETE

Used to remove resources.

```http
DELETE /api/products/{id}
```

The API also uses appropriate HTTP status codes to communicate the result of each operation.

---

# 🔍 Security Principles

The project applies several backend security principles:

* Passwords are hashed with BCrypt.
* JWTs are used for stateless authentication.
* JWT expiration is enforced.
* Authorization is checked using roles.
* Product ownership is checked before modifications.
* Passwords are excluded from API responses.
* JWT secrets are stored outside the source code.
* HTTPS is supported.
* Invalid requests receive structured errors.
* Unexpected exceptions do not expose internal implementation details.
* Public and protected endpoints are explicitly configured.

---

# 📌 Example API Flow

A complete user scenario:

```text
User
 │
 ├── POST /api/Auths/register
 │        │
 │        └── Account created
 │
 ├── POST /api/Auths/login
 │        │
 │        └── JWT returned
 │
 ├── GET /api/products
 │        │
 │        └── Public product list
 │
 ├── POST /api/products
 │        │
 │        └── Product created
 │
 ├── PUT /api/products/{id}
 │        │
 │        └── Owner updates product
 │
 └── DELETE /api/products/{id}
          │
          └── Owner deletes product
```

An administrator can additionally manage users and products across the application.

---

# 🎯 Learning Objectives Achieved

This project demonstrates:

* Spring Boot REST API development
* MongoDB integration
* CRUD operations
* Layered backend architecture
* JWT authentication
* Spring Security
* Role-based authorization
* Resource ownership authorization
* BCrypt password hashing
* HTTP status code handling
* Global exception handling
* Input validation
* HTTPS
* Docker-based database setup
* Secure API design

---

# 👨‍💻 Project Goal

The goal of **Let's Play** is to demonstrate how to build a secure backend API from the ground up using Spring Boot and MongoDB.

The project focuses not only on CRUD functionality, but also on the security and architecture required for a production-oriented REST API:

```text
REST API
   +
MongoDB
   +
Spring Security
   +
JWT
   +
RBAC
   +
Ownership checks
   +
Secure passwords
   +
Global error handling
   =
Secure Backend API
```

---

## 📄 License

This project was created for educational purposes as part of a backend development project.
