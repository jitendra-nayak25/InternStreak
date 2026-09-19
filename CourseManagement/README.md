# Course Management System

A full-stack Course Management System built using React, Spring Boot, Spring Security, JWT, and MySQL.

The application allows users to view courses, search/filter courses, and access course payment information. Administrators can add, edit, and delete courses.

## 🚀 Live Application

### Frontend
https://intern-streak2-nilschb8z-jitendra-2003dots-projects.vercel.app/

### Backend
https://internstreak-coursemanagement.up.railway.app/

## 🛠️ Technologies Used

### Frontend
- React
- Vite
- JavaScript
- React Router
- HTML
- CSS

### Backend
- Java
- Spring Boot 3.5.6
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- Maven

### Database
- MySQL

### Deployment
- Vercel - React Frontend
- Railway - Spring Boot Backend
- Railway MySQL - Production Database

## ✨ Features

### Authentication
- User login
- JWT-based authentication
- Role-based authorization
- ADMIN and USER roles

### Course Management
- View all courses
- Search courses
- Filter courses
- Add courses
- Edit courses
- Delete courses

### Role-Based Access

#### ADMIN
Administrators can:
- View courses
- Add courses
- Edit courses
- Delete courses
- Access payment information

#### USER
Regular users can:
- View courses
- Search and filter courses
- View payment information

Regular users cannot:
- Add courses
- Edit courses
- Delete courses

## 💳 Payment

The application contains a course payment page.

The current payment interface displays the payment status and provides a payment action button.

Actual payment gateway integration can be added in a future version.

## 🔐 Security

The application uses Spring Security and JWT authentication.

After successful login:

1. The backend authenticates the user.
2. A JWT token is generated.
3. The React frontend stores the token.
4. The token is sent with protected API requests.
5. Spring Security validates the token.
6. Role-based authorization controls access to ADMIN and USER operations.

## 🔄 Application Architecture

```text
React Frontend
      |
      | HTTP / REST API
      ↓
Spring Boot Backend
      |
      | Spring Data JPA
      ↓
MySQL Database