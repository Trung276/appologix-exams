# Exams Service API Documentation

## Question Management Endpoints

### Get All Questions

- **URL:** `/apollogix/api/v1/questions`
- **Method:** `GET`
- **Description:** Retrieves a list of all questions.
- **Role:** TEACHER 
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "8292ca2f-f19a-11ee-b404-3497f6130168",
            "content": "Question 1 of Exam 1",
            "examId": "828d0b14-f19a-11ee-b404-3497f6130168",
            "isActivated": true,
            "answers": [
                {
                    "id": "82a23255-f19a-11ee-b404-3497f6130168",
                    "content": "Incorrect Answer B of question 1 of Exam 1"
                },
                {
                    "id": "82a24255-f19a-11ee-b404-3497f6130168",
                    "content": "Incorrect Answer C of question 1 of Exam 1"
                },
                {
                    "id": "82a28255-f19a-11ee-b404-3497f6130168",
                    "content": "Incorrect Answer A of question 1 of Exam 1"
                },
                {
                    "id": "82a58255-f19a-11ee-b404-3497f6100168",
                    "content": "Correct Answer D of question 1 of Exam 1"
                }
            ]
        },
        ...
    ]
  }

### Create Question

- **URL:** `/apollogix/api/v1/questions`
- **Method:** `POST`
- **Description:** Creates a new question.
- **Role:** TEACHER
- **Request Body:**
  ```json
  {
    "content": "question 1 for exam 3",
    "examId": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd"
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "9201d5c6-ee72-4a59-81a9-999e003d7c9f",
        "content": "question 1 for exam 3",
        "examId": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
        "isActivated": true,
        "answers": []
    }
  }

### Get Question By ID

- **URL:** `/apollogix/api/v1/questions/{questionId}`
- **Method:** `GET`
- **Description:** Retrieves a question by question ID.
- **Role:** TEACHER 
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "9201d5c6-ee72-4a59-81a9-999e003d7c9f",
        "content": "question 2 for exam 3",
        "examId": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
        "isActivated": true,
        "answers": []
    }
  }

### Update Question By ID

- **URL:** `/apollogix/api/v1/questions/{questionId}`
- **Method:** `PUT`
- **Description:** Updates information about a question by question ID.
- **Role:** TEACHER
- **Request Body:**
  ```json
  {
    "content": "new content question 1 for exam 3",
    "isActivated": "true"
  }
  
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "9201d5c6-ee72-4a59-81a9-999e003d7c9f",
        "content": "new content question 1 for exam 3",
        "examId": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
        "isActivated": true,
        "answers": []
    }
  }

### Delete Question By ID

- **URL:** `/apollogix/api/v1/questions/{questionId}`
- **Method:** `DELETE`
- **Description:** Deletes an question by question ID.
- **Role:** TEACHER
- **Response:**
  ```json
  {
    "code": 1000,
    "result": "Question has been deleted"
  }

### Search Questions By Exam

- **URL:** `/apollogix/api/v1/questions/search/exam`
- **Method:** `GET`
- **Description:** Retrieves questions by exam.
- **Role:** TEACHER 
- **Parameters:**
  - `id` (String): The id of exam.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "9201d5c6-ee72-4a59-81a9-999e003d7c9f",
            "content": "new content question 1 for exam 3",
            "examId": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
            "isActivated": true,
            "answers": []
        },
        {
            "id": "bad64def-d1f1-42be-8205-257d094aff2d",
            "content": "question 1 for exam 3",
            "examId": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
            "isActivated": true,
            "answers": []
        },
        ...
    ]
  }
