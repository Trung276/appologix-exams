# Exams Service API Documentation

## User Management Endpoints

### Get All Users

- **URL:** `/apollogix/api/v1/users`
- **Method:** `GET`
- **Description:** Retrieves a list of all users.
- **Role:** TEACHER
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "8268e7aa-f19a-11ee-b404-3497f6130168",
            "email": "student1@example.com",
            "fullName": "STUDENT 1",
            "dateOfBirth": "2006-06-11",
            "isActivated": true,
            "role": "STUDENT"
        },
        {
            "id": "826d50b8-f19a-11ee-b404-3497f6130168",
            "email": "student2@example.com",
            "fullName": "STUDENT 2",
            "dateOfBirth": "2006-01-02",
            "isActivated": true,
            "role": "STUDENT"
        },
        {
            "id": "8271557a-f19a-11ee-b404-3497f6130168",
            "email": "teacher@example.com",
            "fullName": "TEACHER APOLLOGIX",
            "dateOfBirth": "1999-12-31",
            "isActivated": true,
            "role": "TEACHER"
        }
    ]
  }

### Create User

- **URL:** `/apollogix/api/v1/users`
- **Method:** `POST`
- **Description:** Creates a new user.
- **Role:** TEACHER
- **Request Body:**
  ```json
  {
    "email": "newuser@example.com",
    "password": "12345678",
    "fullName": "New User",
    "dateOfBirth": "1990-05-20",
    "isActivated": true,
    "role": "STUDENT"
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "79cbbb70-a63c-4ed9-b95e-a150fbfea242",
        "email": "newuser@example.com",
        "fullName": "New User",
        "dateOfBirth": "1990-05-20",
        "isActivated": true,
        "role": "STUDENT"
    }
  }

### Get User By ID

- **URL:** `/apollogix/api/v1/users/{userId}`
- **Method:** `GET`
- **Description:** Retrieves a user by their ID.
- **Role:** TEACHER
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "79cbbb70-a63c-4ed9-b95e-a150fbfea242",
        "email": "newuser@example.com",
        "fullName": "New User",
        "dateOfBirth": "1990-05-19",
        "isActivated": true,
        "role": "STUDENT"
    }
  }

### Get Current User Info

- **URL:** `/apollogix/api/v1/users/my-info`
- **Method:** `GET`
- **Description:** Retrieves information about the currently authenticated user.
- **Role:** TEACHER & STUDENT
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "8271557a-f19a-11ee-b404-3497f6130168",
        "email": "teacher@example.com",
        "fullName": "TEACHER APOLLOGIX",
        "dateOfBirth": "1999-12-31",
        "isActivated": true,
        "role": "TEACHER"
    }
  }

### Update User By ID

- **URL:** `/apollogix/api/v1/users/{userId}`
- **Method:** `PUT`
- **Description:** Updates information about a user by their ID but can not change email. TEACHER can change student's role.
- **Role:** TEACHER
- **Request Body:**
  ```json
  {
    "password": "a12345678",
    "fullName": "STUDENT 2 UPDATE",
    "dateOfBirth": "1990-05-20",
    "isActivated": false,
    "role": "STUDENT"
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "79cbbb70-a63c-4ed9-b95e-a150fbfea242",
        "email": "newuser@example.com",
        "fullName": "NEW NAME UPDATE",
        "dateOfBirth": "1990-05-20",
        "isActivated": false,
        "role": "STUDENT"
    }
  }

### Update Email Address

- **URL:** `/apollogix/api/v1/users/email`
- **Method:** `POST`
- **Description:** Updates the email address of a user. Only default account with email "teacher@example.com"
- **Role:** TEACHER
- **Request Body:**
  ```json
  {
    "oldEmail": "newuser@example.com",
    "newEmail": "student3@example.com"
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "4c75150c-d188-45d6-a42e-e3d48b2f456a",
        "email": "student3@example.com",
        "fullName": "New User",
        "dateOfBirth": "1990-05-19",
        "isActivated": true,
        "role": "STUDENT"
    }
  }

### Delete User By ID

- **URL:** `/apollogix/api/v1/users/{userId}`
- **Method:** `DELETE`
- **Description:** Deletes a user by their ID.
- **Role:** TEACHER
- **Response:**
  ```json
  {
    "code": 1000,
    "result": "User has been deleted"
  }

### Search Users By Name

- **URL:** `/apollogix/api/v1/users/search/name`
- **Method:** `GET`
- **Description:** Retrieves users by searching their full names with a keyword.
- **Role:** TEACHER
- **Parameters:**
  - `keyword` (String): The keyword to search for in the user's full name.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "826d50b8-f19a-11ee-b404-3497f6130168",
            "email": "student2@example.com",
            "fullName": "STUDENT 2 UPDATE",
            "dateOfBirth": "1990-05-19",
            "isActivated": false,
            "role": "STUDENT"
        }
    ]
  }

### Search Users By Email

- **URL:** `/apollogix/api/v1/users/search/email`
- **Method:** `GET`
- **Description:** Retrieves a user by searching their email address.
- **Role:** TEACHER
- **Parameters:**
  - `email` (String): The email address of the user to search for.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "826d50b8-f19a-11ee-b404-3497f6130168",
        "email": "student2@example.com",
        "fullName": "STUDENT 2 UPDATE",
        "dateOfBirth": "1990-05-19",
        "isActivated": false,
        "role": "STUDENT"
    }
  }

### Search Users By Role

- **URL:** `/apollogix/api/v1/users/search/role`
- **Method:** `GET`
- **Description:** Retrieves users by searching their role.
- **Role:** TEACHER
- **Parameters:**
  - `role` (String): The role of the users to search for.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "826d50b8-f19a-11ee-b404-3497f6130168",
            "email": "student2@example.com",
            "fullName": "STUDENT 2 UPDATE",
            "dateOfBirth": "1990-05-19",
            "isActivated": false,
            "role": "STUDENT"
        }
    ]
  }
