# eBanking Spring Backend

This repository contains the backend code for the eBanking application developed with Spring. It provides the necessary APIs and services to support the frontend functionality, such as user authentication, transaction processing, and account management.

## Features

- User authentication and authorization
- Account creation and management
- Transaction processing and history
- Secure API endpoints
- Error handling and validation

## Technologies Used

- Java programming language
- Spring Framework for building robust and scalable backend applications
- MySQL database for data persistence

## Getting Started

To get started with the eBanking Spring Backend, follow these steps:

1. Clone the repository to your local machine:

git clone https://github.com/YDev-0/ebanking-spring-backend.git


2. Navigate to the project directory:

cd ebanking-spring-backend


3. Configure the application properties:
- Open the `src/main/resources/application.properties` file.
- Update the database connection details (URL, username, password) according to your setup.
- Update port if necessary

4. Build and run the application using Maven:

mvn spring-boot:run


The backend will be accessible at `http://localhost:8080`.

## API Documentation

The backend API is documented using Swagger. Once the application is running, you can access the Swagger UI to explore and test the available endpoints. Open the following URL in your browser:

http://localhost:8081/swagger-ui.html
