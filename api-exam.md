# Exams Service API Documentation

## Exam Management Endpoints

### Get All Exams

- **URL:** `/apollogix/api/v1/exams`
- **Method:** `GET`
- **Description:** Retrieves a list of all exams.
- **Role:** TEACHER && STUDENT
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "828d0b14-f19a-11ee-b404-3497f6130168",
            "title": "Exam 1",
            "description": "Description of Exam 1",
            "creatorId": "8271557a-f19a-11ee-b404-3497f6130168",
            "creatorName": "TEACHER APOLLOGIX",
            "duration": 60,
            "isActivated": true,
            "questions": [
                {
                    "id": "8292ca2f-f19a-11ee-b404-3497f6130168",
                    "content": "Question 1 of Exam 1",
                    "examId": "828d0b14-f19a-11ee-b404-3497f6130168",
                    "isActivated": true,
                    "answers": [
                        {
                            "id": "82a23255-f19a-11ee-b404-3497f6130168",
                            "content": "Incorrect Answer B of Question 1 of Exam 1"
                        },
                        {
                            "id": "82a24255-f19a-11ee-b404-3497f6130168",
                            "content": "Incorrect Answer C of Question 1 of Exam 1"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6130168",
                            "content": "Incorrect Answer A of Question 1 of Exam 1"
                        },
                        {
                            "id": "82a58255-f19a-11ee-b404-3497f6100168",
                            "content": "Correct Answer D of Question 1 of Exam 1"
                        }
                    ]
                },
                ...
            ]
        },
        {
            "id": "82a778be-f19a-11ee-b404-3497f6130168",
            "title": "Exam 2",
            "description": "Description of Exam 2",
            "creatorId": "8271557a-f19a-11ee-b404-3497f6130168",
            "creatorName": "TEACHER APOLLOGIX",
            "duration": 15,
            "isActivated": true,
            "questions": [
                {
                    "id": "82b100af-f19a-11ee-b404-3497f6130168",
                    "content": "Question 1 of Exam 2",
                    "examId": "82a778be-f19a-11ee-b404-3497f6130168",
                    "isActivated": true,
                    "answers": [
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6130218",
                            "content": "Incorrect Answer A of Question 1 of Exam 2"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6130268",
                            "content": "Incorrect Answer B of Question 1 of Exam 2"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6131168",
                            "content": "Incorrect Answer C of Question 1 of Exam 2"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6132268",
                            "content": "Correct Answer D of Question 1 of Exam 2"
                        }
                    ]
                },
                ...
            ]
        }
    ]
  }

### Create Exam

- **URL:** `/apollogix/api/v1/exams`
- **Method:** `POST`
- **Description:** Creates a new exam.
- **Role:** TEACHER
- **Request Body:**
  ```json
  {
    "title": "Sample Exam",
    "description": "This is a sample exam",
    "duration": 10
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
        "title": "Sample Exam",
        "description": "This is a sample exam",
        "creatorId": "8271557a-f19a-11ee-b404-3497f6130168",
        "creatorName": "TEACHER APOLLOGIX",
        "duration": 10,
        "isActivated": true,
        "questions": []
    }
  }

### Get Exam By ID

- **URL:** `/apollogix/api/v1/exams/{examId}`
- **Method:** `GET`
- **Description:** Retrieves an exam by exam ID.
- **Role:** TEACHER && STUDENT
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
        "title": "Sample Exam",
        "description": "This is a sample exam",
        "creatorId": "8271557a-f19a-11ee-b404-3497f6130168",
        "creatorName": "TEACHER APOLLOGIX",
        "duration": 10,
        "isActivated": true,
        "questions": []
    }
  }

### Update Exam By ID

- **URL:** `/apollogix/api/v1/exams/{examId}`
- **Method:** `PUT`
- **Description:** Updates information about an exam by exam ID.
- **Role:** TEACHER
- **Request Body:**
  ```json
  {
    "title": "Updated Exam",
    "description": "This is an updated exam",
    "duration": 15,
    "isActivated": "true"
  }
  
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "f8e3dfc6-55e5-4208-b7df-34c0a61485dd",
        "title": "Updated Exam",
        "description": "This is an updated exam",
        "creatorId": "8271557a-f19a-11ee-b404-3497f6130168",
        "creatorName": "TEACHER APOLLOGIX",
        "duration": 15,
        "isActivated": true,
        "questions": []
    }
  }

### Delete Exam By ID

- **URL:** `/apollogix/api/v1/exams/{examId}`
- **Method:** `DELETE`
- **Description:** Deletes an exam by exam ID.
- **Role:** TEACHER
- **Response:**
  ```json
  {
    "code": 1000,
    "result": "exam has been deleted"
  }

### Search Exams By Title

- **URL:** `/apollogix/api/v1/exams/search/title`
- **Method:** `GET`
- **Description:** Retrieves exams by searching title with a keyword.
- **Role:** TEACHER && STUDENT
- **Parameters:**
  - `keyword` (String): The keyword to search for in the exam's title
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

### Search Exams By Creator

- **URL:** `/apollogix/api/v1/exams/search/creator`
- **Method:** `GET`
- **Description:** Retrieves an exam by searching id of the exam creator.
- **Role:** TEACHER && STUDENT
- **Parameters:**
  - `id` (String): The id of user.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "828d0b14-f19a-11ee-b404-3497f6130168",
            "title": "Exam 1",
            "description": "Description of Exam 1",
            "creatorId": "8271557a-f19a-11ee-b404-3497f6130168",
            "creatorName": "TEACHER APOLLOGIX",
            "duration": 60,
            "isActivated": true,
            "questions": [
                {
                    "id": "8292ca2f-f19a-11ee-b404-3497f6130168",
                    "content": "Question 1 of Exam 1",
                    "examId": "828d0b14-f19a-11ee-b404-3497f6130168",
                    "isActivated": true,
                    "answers": [
                        {
                            "id": "82a23255-f19a-11ee-b404-3497f6130168",
                            "content": "Incorrect Answer B of Question 1 of Exam 1"
                        },
                        {
                            "id": "82a24255-f19a-11ee-b404-3497f6130168",
                            "content": "Incorrect Answer C of Question 1 of Exam 1"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6130168",
                            "content": "Incorrect Answer A of Question 1 of Exam 1"
                        },
                        {
                            "id": "82a58255-f19a-11ee-b404-3497f6100168",
                            "content": "Correct Answer D of Question 1 of Exam 1"
                        }
                    ]
                },
                ...
            ]
        },
        {
            "id": "82a778be-f19a-11ee-b404-3497f6130168",
            "title": "Exam 2",
            "description": "Description of Exam 2",
            "creatorId": "8271557a-f19a-11ee-b404-3497f6130168",
            "creatorName": "TEACHER APOLLOGIX",
            "duration": 15,
            "isActivated": true,
            "questions": [
                {
                    "id": "82b100af-f19a-11ee-b404-3497f6130168",
                    "content": "Question 1 of Exam 2",
                    "examId": "82a778be-f19a-11ee-b404-3497f6130168",
                    "isActivated": true,
                    "answers": [
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6130218",
                            "content": "Incorrect Answer A of Question 1 of Exam 2"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6130268",
                            "content": "Incorrect Answer B of Question 1 of Exam 2"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6131168",
                            "content": "Incorrect Answer C of Question 1 of Exam 2"
                        },
                        {
                            "id": "82a28255-f19a-11ee-b404-3497f6132268",
                            "content": "Correct Answer D of Question 1 of Exam 2"
                        }
                    ]
                },
                ...
            ]
        }
    ]
  }
