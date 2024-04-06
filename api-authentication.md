# Exams Service API Documentation

## Authentication Endpoints

### Login

- **URL:** `/apollogix/api/v1/auth/login`
- **Method:** `POST`
- **Description:** Authenticates a user and generates an authentication token.
- **Request Body:**
  ```json
  {
  "email": "student1@example.com",
  "password": "12345678"
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhcG9sbG9naXguY29tIiwic3ViIjoic3R1ZGVudDFAZXhhbXBsZS5jb20iLCJleHAiOjE3MTIzMDczMjksImlhdCI6MTcxMjMwMzcyOSwic2NvcGUiOiJTVFVERU5UIn0.xCsNK4QDcIIwY91zZdnCDecMpJOxec9WUwXdzrCYFbMxYuoxg_RpOknOl3qgFjqMBIwdP7acwJeBXRMsSh8pCg",
        "tokenType": "Bearer",
        "expiresTime": "3600000"
    }
  }

### Token Validation

- **URL:** `/apollogix/api/v1/auth/token-validation`
- **Method:** `POST`
- **Description:** Validates the authenticity of an authentication token.
- **Request Body:**
  ```json
  {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhcG9sbG9naXguY29tIiwic3ViIjoic3R1ZGVudDFAZXhhbXBsZS5jb20iLCJleHAiOjE3MTIzMDczMjksImlhdCI6MTcxMjMwMzcyOSwic2NvcGUiOiJTVFVERU5UIn0.xCsNK4QDcIIwY91zZdnCDecMpJOxec9WUwXdzrCYFbMxYuoxg_RpOknOl3qgFjqMBIwdP7acwJeBXRMsSh8pCg"
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "message": "Token is valid",
        "validToken": true
    }
  }

### Logout

- **URL:** `/apollogix/api/v1/auth/logout`
- **Method:** `POST`
- **Description:** Invalidates the current authentication token and add token to token blacklist.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": "Logout success!"
  }