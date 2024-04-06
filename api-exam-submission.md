# exam-submissions Service API Documentation

## Exam Submission Management Endpoints

### Get All exam-submissions Submission

- **URL:** `/apollogix/api/v1/exam-submissions`
- **Method:** `GET`
- **Description:** Retrieves a list of all exam-submissions.
- **Role:** TEACHER && STUDENT
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "07a98dd2-7baa-4d91-8d6b-7d25bae6bedc",
            "examId": "82a778be-f19a-11ee-b404-3497f6130168",
            "examTitle": "Exam 2",
            "examDescription": "Description of Exam 2",
            "examDuration": 15,
            "examCreatorId": "8271557a-f19a-11ee-b404-3497f6130168",
            "examCreatorName": "TEACHER APOLLOGIX",
            "submitterId": "8271557a-f19a-11ee-b404-3497f6130168",
            "submitterName": "TEACHER APOLLOGIX",
            "submitTime": "2024-04-06T08:42:53.000+00:00",
            "questions": [
                {
                    "id": "82b100af-f19a-11ee-b404-3497f6130168",
                    "content": "Question 1 of Exam 2",
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
                    ],
                    "selectedAnswersIds": [
                        "82a28255-f19a-11ee-b404-3497f6132268"
                    ],
                    "correctAnswerIds": [
                        "82a28255-f19a-11ee-b404-3497f6132268"
                    ],
                    "isCorrect": true
                },
                ...
            ],
            "result": "1/3",
            "isActivated": true
        },
        ...
    ]
  }

### Create Exam Submission

- **URL:** `/apollogix/api/v1/exam-submissions`
- **Method:** `POST`
- **Description:** Creates a new exam submission.
- **Role:** TEACHER && STUDENT
- **Request Body:**
  ```json
  {
    "examId": "82a778be-f19a-11ee-b404-3497f6130168",
    "selectedAnswerIds": [
        "82a28255-f19a-11ee-b404-3497f6132268",
        "82a28255-f19a-11ee-b404-3497f6134568",
        "82a28255-f19a-11ee-b404-3497f6130678",
        "82a28255-f19a-11ee-b404-3497f6188558"
    ]
  }
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "07a98dd2-7baa-4d91-8d6b-7d25bae6bedc",
        "examId": "82a778be-f19a-11ee-b404-3497f6130168",
        "examTitle": "Exam 2",
        "examDescription": "Description of Exam 2",
        "examDuration": 15,
        "examCreatorId": "8271557a-f19a-11ee-b404-3497f6130168",
        "examCreatorName": "TEACHER APOLLOGIX",
        "submitterId": "8271557a-f19a-11ee-b404-3497f6130168",
        "submitterName": "TEACHER APOLLOGIX",
        "submitTime": "2024-04-06T08:42:52.682+00:00",
        "questions": [
            {
                "id": "82b100af-f19a-11ee-b404-3497f6130168",
                "content": "Question 1 of Exam 2",
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
                ],
                "selectedAnswersIds": [
                    "82a28255-f19a-11ee-b404-3497f6132268"
                ],
                "correctAnswerIds": [
                    "82a28255-f19a-11ee-b404-3497f6132268"
                ],
                "isCorrect": true
            },
            ...
        ],
        "result": "1/3",
        "isActivated": true
    }
  }

### Get Exam Submission By ID

- **URL:** `/apollogix/api/v1/exam-submissions/{examId}`
- **Method:** `GET`
- **Description:** Retrieves an exam by exam ID.
- **Role:** TEACHER && STUDENT
- **Response:**
  ```json
  {
    "code": 1000,
    "result": {
        "id": "07a98dd2-7baa-4d91-8d6b-7d25bae6bedc",
        "examId": "82a778be-f19a-11ee-b404-3497f6130168",
        "examTitle": "Exam 2",
        "examDescription": "Description of Exam 2",
        "examDuration": 15,
        "examCreatorId": "8271557a-f19a-11ee-b404-3497f6130168",
        "examCreatorName": "TEACHER APOLLOGIX",
        "submitterId": "8271557a-f19a-11ee-b404-3497f6130168",
        "submitterName": "TEACHER APOLLOGIX",
        "submitTime": "2024-04-06T08:42:53.000+00:00",
        "questions": [
            {
                "id": "82b100af-f19a-11ee-b404-3497f6130168",
                "content": "Question 1 of Exam 2",
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
                ],
                "selectedAnswersIds": [
                    "82a28255-f19a-11ee-b404-3497f6132268"
                ],
                "correctAnswerIds": [
                    "82a28255-f19a-11ee-b404-3497f6132268"
                ],
                "isCorrect": true
            },
            ...
        ],
        "result": "1/3",
        "isActivated": true
    }
  }

### Delete Exam By ID

- **URL:** `/apollogix/api/v1/exam-submissions/{examId}`
- **Method:** `DELETE`
- **Description:** Deletes an exam submission by exam ID.
- **Role:** TEACHER
- **Response:**
  ```json
  {
    "code": 1000,
    "result": "Exam submission has been deleted"
  }

### Search Exam Submissions By Submitter

- **URL:** `/apollogix/api/v1/exam-submissions/search/submitter`
- **Method:** `GET`
- **Description:** Retrieves an exam-submissions by searching id of the submitter.
- **Role:** TEACHER && STUDENT
- **Parameters:**
  - `id` (String): The keyword to search for in the user's id.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "07a98dd2-7baa-4d91-8d6b-7d25bae6bedc",
            "examId": "82a778be-f19a-11ee-b404-3497f6130168",
            "examTitle": "Exam 2",
            "examDescription": "Description of Exam 2",
            "examDuration": 15,
            "examCreatorId": "8271557a-f19a-11ee-b404-3497f6130168",
            "examCreatorName": "TEACHER APOLLOGIX",
            "submitterId": "8271557a-f19a-11ee-b404-3497f6130168",
            "submitterName": "TEACHER APOLLOGIX",
            "submitTime": "2024-04-06T08:42:53.000+00:00",
            "questions": [
                {
                    "id": "82b100af-f19a-11ee-b404-3497f6130168",
                    "content": "Question 1 of Exam 2",
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
                    ],
                    "selectedAnswersIds": [
                        "82a28255-f19a-11ee-b404-3497f6132268"
                    ],
                    "correctAnswerIds": [
                        "82a28255-f19a-11ee-b404-3497f6132268"
                    ],
                    "isCorrect": true
                },
                ...
            ],
            "result": "1/3",
            "isActivated": true
        },
        ...
    ]
  }

### Search Exam Submissions By Exam

- **URL:** `/apollogix/api/v1/exam-submissions/search/exam`
- **Method:** `GET`
- **Description:** Retrieves an exam submission by searching id of the exam.
- **Role:** TEACHER && STUDENT
- **Parameters:**
  - `id` (String): The keyword to search for in the exam's id.
- **Response:**
  ```json
  {
    "code": 1000,
    "result": [
        {
            "id": "07a98dd2-7baa-4d91-8d6b-7d25bae6bedc",
            "examId": "82a778be-f19a-11ee-b404-3497f6130168",
            "examTitle": "Exam 2",
            "examDescription": "Description of Exam 2",
            "examDuration": 15,
            "examCreatorId": "8271557a-f19a-11ee-b404-3497f6130168",
            "examCreatorName": "TEACHER APOLLOGIX",
            "submitterId": "8271557a-f19a-11ee-b404-3497f6130168",
            "submitterName": "TEACHER APOLLOGIX",
            "submitTime": "2024-04-06T08:42:53.000+00:00",
            "questions": [
                {
                    "id": "82b100af-f19a-11ee-b404-3497f6130168",
                    "content": "Question 1 of Exam 2",
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
                    ],
                    "selectedAnswersIds": [
                        "82a28255-f19a-11ee-b404-3497f6132268"
                    ],
                    "correctAnswerIds": [
                        "82a28255-f19a-11ee-b404-3497f6132268"
                    ],
                    "isCorrect": true
                },
                ...
            ],
            "result": "1/3",
            "isActivated": true
        },
        ...
    ]
  }
