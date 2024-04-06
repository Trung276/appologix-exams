# Database `exams_service`
 
## Table: `users`

| Column Name  | Data Type    | Constraints        |
|--------------|--------------|--------------------|
| id           | VARCHAR(36)  | PRIMARY KEY        |
| email        | VARCHAR(255) | NOT NULL, UNIQUE   |
| password     | VARCHAR(255) | NOT NULL           |
| full_name    | VARCHAR(255) | NOT NULL           |
| date_of_birth| DATE         | NOT NULL           |
| is_active    | BOOLEAN      | NOT NULL, DEFAULT  |
| role         | VARCHAR(20)  | NOT NULL           |

### Sample Data:

| id                                | email                | password                                             | full_name       | date_of_birth | is_active | role    |
|-----------------------------------|----------------------|------------------------------------------------------|-----------------|---------------|-----------|---------|
| 8271557a-f19a-11ee-b404-3497f6130168 | teacher@example.com | $2a$10$09CiyAc4qd0IOOmNnRJ2se1oCqTVIxkNrFn2QXrb719c3l0AQtQx. | TEACHER APOLLOGIX | 2000-01-01    | true      | TEACHER |
| 8268e7aa-f19a-11ee-b404-3497f6130168 | student1@example.com | $2a$10$09CiyAc4qd0IOOmNnRJ2se1oCqTVIxkNrFn2QXrb719c3l0AQtQx. | STUDENT 1       | 2006-06-12    | true      | STUDENT |
| 826d50b8-f19a-11ee-b404-3497f6130168 | student2@example.com | $2a$10$09CiyAc4qd0IOOmNnRJ2se1oCqTVIxkNrFn2QXrb719c3l0AQtQx. | STUDENT 2       | 2006-01-03    | true      | STUDENT |

## Table: `exams`

| Column Name  | Data Type    | Constraints        |
|--------------|--------------|--------------------|
| id           | VARCHAR(36)  | PRIMARY KEY        |
| title        | VARCHAR(255) | NOT NULL           |
| description  | TEXT         | NOT NULL           |
| creator_id   | VARCHAR(36)  | NOT NULL, FOREIGN KEY |
| create_time  | TIMESTAMP    | NOT NULL, DEFAULT  |
| duration     | INT          | NOT NULL           |
| is_active    | BOOLEAN      | NOT NULL, DEFAULT  |

### Sample Data:

| id                                | title   | description           | creator_id                           | create_time         | duration | is_active |
|-----------------------------------|---------|-----------------------|--------------------------------------|---------------------|----------|-----------|
| 828d0b14-f19a-11ee-b404-3497f6130168 | Exam 1  | Description of Exam 1 | 8271557a-f19a-11ee-b404-3497f6130168 | [Current Timestamp] | 60       | true      |
| 82a778be-f19a-11ee-b404-3497f6130168 | Exam 2  | Description of Exam 2 | 8271557a-f19a-11ee-b404-3497f6130168 | [Current Timestamp] | 15       | true      |

## Table: `questions`

| Column Name  | Data Type    | Constraints        |
|--------------|--------------|--------------------|
| id           | VARCHAR(36)  | PRIMARY KEY        |
| content      | TEXT         | NOT NULL           |
| exam_id      | VARCHAR(36)  | NOT NULL, FOREIGN KEY |
| is_active    | BOOLEAN      | NOT NULL, DEFAULT  |

## Table: `answers`

| Column Name  | Data Type    | Constraints        |
|--------------|--------------|--------------------|
| id           | VARCHAR(36)  | PRIMARY KEY        |
| content      | TEXT         | NOT NULL           |
| is_correct   | BOOLEAN      | NOT NULL           |
| question_id  | VARCHAR(36)  | NOT NULL, FOREIGN KEY |
| is_active    | BOOLEAN      | NOT NULL, DEFAULT  |

## Table: `token_blacklist`

| Column Name  | Data Type    | Constraints        |
|--------------|--------------|--------------------|
| id           | VARCHAR(36)  | PRIMARY KEY        |
| token        | VARCHAR(255) | NOT NULL, UNIQUE   |

## Table: `exam_submission`

| Column Name  | Data Type    | Constraints        |
|--------------|--------------|--------------------|
| id           | VARCHAR(36)  | PRIMARY KEY        |
| submitter_id | VARCHAR(36)  | NOT NULL, FOREIGN KEY |
| exam_id      | VARCHAR(36)  | NOT NULL, FOREIGN KEY |
| submit_time  | TIMESTAMP    | NOT NULL, DEFAULT  |
| is_active    | BOOLEAN      | NOT NULL, DEFAULT  |

## Table: `exam_submission_answer`

| Column Name       | Data Type   | Constraints        |
|-------------------|-------------|--------------------|
| exam_submission_id| VARCHAR(36) | FOREIGN KEY        |
| answer_id         | VARCHAR(36) | FOREIGN KEY        |

