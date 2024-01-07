# README for the Application MovieLikerNK
Recruitment task for Nowe Kolory

## Required Environment Variables
Before running this application, make sure the following environment variables are defined:

* OMDB_API_KEY - API Key for OMDb Service
* JWT_SECRET - JWT Secret Key for Security Configuration (64 Characters)
* DB_PORT - PostgreSQL database port
* DB_NAME - PostgreSQL database name
* DB_USER - PostgreSQL database username
* DB_PASSWORD - PostgreSQL database user password

### After the application is running, the Swagger interface will be accessible at:
http://localhost:8080/swagger-ui.html

### 1. Registering a New User:
To register a new user, follow these steps:

* Make a POST request to the /user endpoint.
* Provide registration data in the request.
* Receive a response confirming the successful registration.

### 2. Logging in and Obtaining a JWT Token:
To obtain a JWT token after logging in, follow these steps:

* Make a POST request to the /login endpoint.
* Provide login credentials in the request header.
* Receive a response with the JWT token.

### 3. Authenticating with JWT Token on Secured Endpoints:
To use features that require authentication, follow these steps:

* Include the JWT token in the request header when calling secured endpoints.
* Secured endpoints will only be accessible to users with a valid JWT token.