CREATE DATABASE IF NOT EXISTS exams_service;

USE exams_service;

CREATE TABLE IF NOT EXISTS users
(
    id            VARCHAR(36)  PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    date_of_birth DATE         NOT NULL,
    is_active     BOOLEAN      NOT NULL DEFAULT TRUE,
    role          VARCHAR(20)  NOT NULL
);

-- teacher default with email/password: teacher@example.com/12345678
INSERT INTO users (id, email, password, full_name, date_of_birth, is_active, role)
VALUES ('8271557a-f19a-11ee-b404-3497f6130168', 'teacher@example.com', '$2a$10$09CiyAc4qd0IOOmNnRJ2se1oCqTVIxkNrFn2QXrb719c3l0AQtQx.',
        'TEACHER APOLLOGIX', '2000-01-01', true, 'TEACHER');

-- student 1  with email/password: student1@example.com/12345678
INSERT INTO users (id, email, password, full_name, date_of_birth, is_active, role)
VALUES ('8268e7aa-f19a-11ee-b404-3497f6130168', 'student1@example.com', '$2a$10$09CiyAc4qd0IOOmNnRJ2se1oCqTVIxkNrFn2QXrb719c3l0AQtQx.', 'STUDENT 1',
        '2006-06-12', true, 'STUDENT');

-- student 1  with email/password: student2@example.com/12345678
INSERT INTO users (id, email, password, full_name, date_of_birth, is_active, role)
VALUES ('826d50b8-f19a-11ee-b404-3497f6130168', 'student2@example.com', '$2a$10$09CiyAc4qd0IOOmNnRJ2se1oCqTVIxkNrFn2QXrb719c3l0AQtQx.', 'STUDENT 2',
        '2006-01-03', true, 'STUDENT');

CREATE TABLE IF NOT EXISTS exams
(
    id          VARCHAR(36) PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    creator_id  VARCHAR(36)  NOT NULL,
    create_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    duration    INT          NOT NULL,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    FOREIGN KEY (creator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS questions
(
    id         VARCHAR(36) PRIMARY KEY,
    content    TEXT        NOT NULL,
    exam_id    VARCHAR(36) NOT NULL,
    is_active BOOLEAN      NOT NULL DEFAULT TRUE,
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

CREATE TABLE IF NOT EXISTS answers
(
    id          VARCHAR(36) PRIMARY KEY,
    content     TEXT        NOT NULL,
    is_correct  BOOLEAN     NOT NULL,
    question_id VARCHAR(36) NOT NULL,
    is_active  BOOLEAN      NOT NULL DEFAULT TRUE,
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

    CREATE TABLE IF NOT EXISTS token_blacklist
(
    id      VARCHAR(36) PRIMARY KEY,
    token   VARCHAR(255) UNIQUE NOT NULL
);

    CREATE TABLE IF NOT EXISTS exam_submission
(
    id            VARCHAR(36) PRIMARY KEY,
    submitter_id  VARCHAR(36) NOT NULL,
    exam_id       VARCHAR(36) NOT NULL,
    submit_time   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active     BOOLEAN     NOT NULL DEFAULT TRUE,
    FOREIGN KEY (submitter_id) REFERENCES users(id),
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

    CREATE TABLE IF NOT EXISTS exam_submission_answer
(
    exam_submission_id VARCHAR(36),
    answer_id          VARCHAR(36),
    PRIMARY KEY (exam_submission_id, answer_id),
    FOREIGN KEY (exam_submission_id) REFERENCES exam_submission(id),
    FOREIGN KEY (answer_id) REFERENCES answers(id)
);


-- exam 1
INSERT INTO exams (id, title, description, creator_id, create_time, duration, is_active)
VALUES ('828d0b14-f19a-11ee-b404-3497f6130168', 'Exam 1', 'Description of Exam 1', (SELECT id FROM users WHERE email = 'teacher@example.com'), NOW(),
        60, true);

-- question of exam 1
INSERT INTO questions (id, content, exam_id, is_active)
VALUES ('8292ca2f-f19a-11ee-b404-3497f6130168', 'Question 1 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('8292d89e-f19a-11ee-b404-3497f6133168', 'Question 2 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('8292d89e-f19a-11ee-b404-3497f6130168', 'Question 3 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('8292e7b2-f19a-11ee-b404-3497f6130168', 'Question 4 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('8292f57a-f19a-11ee-b404-3497f6130168', 'Question 5 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('8292fb3b-f19a-11ee-b404-3497f6130168', 'Question 6 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('8293347c-f19a-11ee-b404-3497f6130168', 'Question 7 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('82933f23-f19a-11ee-b404-3497f6130168', 'Question 8 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('82934481-f19a-11ee-b404-3497f6130168', 'Question 9 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true),
       ('82934761-f19a-11ee-b404-3497f6130168', 'Question 10 of Exam 1', '828d0b14-f19a-11ee-b404-3497f6130168', true);

-- answer of exam 1
INSERT INTO answers (id, content, is_correct, question_id, is_active)
VALUES ('82a28255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer A of Question 1 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 1'), true),
       ('82a23255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer B of Question 1 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 1'), true),
       ('82a24255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer C of Question 1 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 1'), true),
       ('82a58255-f19a-11ee-b404-3497f6100168', 'Correct Answer D of Question 1 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 1'), true),
    
       ('82a78255-f19a-11ee-b404-3497f6130168', 'Correct Answer A of Question 2 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 1'), true),
       ('82a68255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer B of Question 2 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 1'), true),
       ('82b28255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer C of Question 2 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 1'), true),
       ('82v28255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer D of Question 2 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 1'), true),

       ('82d28255-f19a-11ee-b404-3497f6130168', 'Correct Answer A of Question 3 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 1'), true),
       ('82g28255-f19a-11ee-b404-3497f6130168', 'Correct Answer B of Question 3 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 1'), true),
       ('82a58255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer C of Question 3 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 1'), true),
       ('82a98255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer D of Question 3 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 1'), true),

       ('82a28355-f19a-11ee-b404-3497f6130168', 'Incorrect Answer A of Question 4 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 4 of Exam 1'), true),
       ('82a26255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer B of Question 4 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 4 of Exam 1'), true),
       ('82a28275-f19a-11ee-b404-3497f6130168', 'Correct Answer C of Question 4 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 4 of Exam 1'), true),
       ('82a2825k-f19a-11ee-b404-3497f6130168', 'Incorrect Answer D of Question 4 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 4 of Exam 1'), true),

       ('82a28254-f19a-11ee-b404-3497f6130168', 'Incorrect Answer A of Question 5 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 5 of Exam 1'), true),
       ('82a28255-f19a-11ee-b404-3491f6130168', 'Incorrect Answer B of Question 5 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 5 of Exam 1'), true),
       ('84a28255-f19a-11ee-b404-3497f6130168', 'Correct Answer C of Question 5 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 5 of Exam 1'), true),
       ('81a28255-f19a-11ee-b404-3497f6130168', 'Correct Answer D of Question 5 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 5 of Exam 1'), true),

       ('83a28255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer A of Question 6 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 6 of Exam 1'), true),
       ('88a28255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer B of Question 6 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 6 of Exam 1'), true),
       ('82a29255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer C of Question 6 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 6 of Exam 1'), true),
       ('82a28055-f19a-11ee-b404-3497f6130168', 'Correct Answer D of Question 6 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 6 of Exam 1'), true),

       ('82a28205-f19a-11ee-b404-3497f6130168', 'Correct Answer A of Question 7 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 7 of Exam 1'), true),
       ('82a28250-f19a-11ee-b404-3497f6130168', 'Incorrect Answer B of Question 7 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 7 of Exam 1'), true),
       ('82a28215-f19a-11ee-b404-3497f6130168', 'Incorrect Answer C of Question 7 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 7 of Exam 1'), true),
       ('82a28235-f19a-11ee-b404-3497f6130168', 'Incorrect Answer D of Question 7 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 7 of Exam 1'), true),

       ('82a28555-f19a-11ee-b404-3497f6130168', 'Correct Answer A of Question 8 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 8 of Exam 1'), true),
       ('82a28685-f19a-11ee-b404-3497f6130168', 'Correct Answer B of Question 8 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 8 of Exam 1'), true),
       ('82a28335-f19a-11ee-b404-3497f6130168', 'Incorrect Answer C of Question 8 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 8 of Exam 1'), true),
       ('82a28445-f19a-11ee-b404-3497f6130168', 'Incorrect Answer D of Question 8 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 8 of Exam 1'), true),

       ('82a28565-f19a-11ee-b404-3497f6130168', 'Incorrect Answer A of Question 9 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 9 of Exam 1'), true),
       ('82a22255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer B of Question 9 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 9 of Exam 1'), true),
       ('82a2155-f19a-11ee-b404-3497f6130168', 'Correct Answer C of Question 9 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 9 of Exam 1'), true),
       ('82a00255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer D of Question 9 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 9 of Exam 1'), true),

       ('82a33255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer A of Question 10 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 10 of Exam 1'), true),
       ('82a0p255-f19a-11ee-b404-3497f6130168', 'Incorrect Answer B of Question 10 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 10 of Exam 1'), true),
       ('82al6255-f19a-11ee-b404-3497f6130168', 'Correct Answer C of Question 10 of Exam 1', false,
        (SELECT id FROM questions WHERE content = 'Question 10 of Exam 1'), true),
       ('82a283m5-f19a-11ee-b404-3497f6130168', 'Correct Answer D of Question 10 of Exam 1', true,
        (SELECT id FROM questions WHERE content = 'Question 10 of Exam 1'), true);


-- exam 2
INSERT INTO exams (id, title, description, creator_id, create_time, duration, is_active)
VALUES ('82a778be-f19a-11ee-b404-3497f6130168', 'Exam 2', 'Description of Exam 2', (SELECT id FROM users WHERE email = 'teacher@example.com'), NOW(),
        15, true);

-- question of exam 2
INSERT INTO questions (id, content, exam_id, is_active)
VALUES ('82b100af-f19a-11ee-b404-3497f6130168', 'Question 1 of Exam 2', '82a778be-f19a-11ee-b404-3497f6130168', true),
       ('82b10b33-f19a-11ee-b404-3497f6130168', 'Question 2 of Exam 2', '82a778be-f19a-11ee-b404-3497f6130168', true),
       ('82b10ef5-f19a-11ee-b404-3497f6130168', 'Question 3 of Exam 2', '82a778be-f19a-11ee-b404-3497f6130168', true);

-- answer of exam 2
INSERT INTO answers (id, content, is_correct, question_id, is_active)
VALUES ('82a28255-f19a-11ee-b404-3497f6130218', 'Incorrect Answer A of Question 1 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6130268', 'Incorrect Answer B of Question 1 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6131168', 'Incorrect Answer C of Question 1 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6132268', 'Correct Answer D of Question 1 of Exam 2', true,
        (SELECT id FROM questions WHERE content = 'Question 1 of Exam 2'), true),

       ('82a28255-f19a-11ee-b404-3497f6134568', 'Correct Answer A of Question 2 of Exam 2', true,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6130668', 'Incorrect Answer B of Question 2 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6130568', 'Incorrect Answer C of Question 2 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6130678', 'Incorrect Answer D of Question 2 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 2 of Exam 2'), true),

       ('82a28255-f19a-11ee-b404-3497f6130778', 'Correct Answer A of Question 3 of Exam 2', true,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6188168', 'Correct Answer B of Question 3 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f6188558', 'Incorrect Answer C of Question 3 of Exam 2', false,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 2'), true),
       ('82a28255-f19a-11ee-b404-3497f8830168', 'Incorrect Answer D of Question 3 of Exam 2', true,
        (SELECT id FROM questions WHERE content = 'Question 3 of Exam 2'), true);






