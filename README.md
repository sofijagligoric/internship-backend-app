# Internship Management System — Backend

## Overview

The backend of the Internship Management System is a Spring Boot application that provides RESTful services for managing student internships and coordinating interactions between students, faculty, and companies.

It serves as the central component of the system, handling business logic, data management, internship recommendations, and communication with the frontend applications.

## Features

- **RESTful API:** Provides endpoints for the student, faculty, and company applications.
- **Internship Management:** Handles internship listings and related information, including descriptions, required technologies, application periods, and eligibility requirements.
- **AI-Powered Recommendations:** Integrates with an AI API to evaluate internship compatibility based on students' CVs, skills, and interests. Generates ranked recommendations with compatibility scores and explanations.
- **Application Management:** Supports internship applications and their status updates.
- **Student Data Management:** Stores and processes student profiles, CV information, skills, education, and work experience.
- **Work Diary Management:** Supports weekly student activity records and feedback.
- **RSS Feed:** Provides an RSS feed containing information about available internships.
- **Centralized Data Management:** Uses a shared database for the system's applications.

## Technology Stack

- Java
- Spring Boot
- REST API
- AI API integration
- Database: MySQL
- Build tool: Maven

## Architecture

The backend acts as a central service layer, exposing REST endpoints consumed by the student, faculty, and company applications. It manages access to shared data and coordinates the system's core business processes.

## Configuration

Application settings are configured through the application's properties file. Required database credentials and API keys should be supplied through local configuration or environment variables rather than committed to the repository.

## Running the Application

1. Clone the repository.
2. Configure the database connection and required application properties.
3. Configure the AI API credentials if AI-powered recommendations are enabled.
4. Build and run the Spring Boot application using the project's configured build tool.
5. Connect the frontend applications to the backend API.

## Project Context

This application was developed as part of an Internet Programming university project at the Faculty of Electrical Engineering, University of Banja Luka.

