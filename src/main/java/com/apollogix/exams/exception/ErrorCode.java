package com.apollogix.exams.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998, "Invalid key provided", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(9997, "The requested resource was not found", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(9996, "The requested method is not allowed", HttpStatus.METHOD_NOT_ALLOWED),

    UNAUTHENTICATED(1001, "Authentication failed", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1002, "Unauthorized access", HttpStatus.FORBIDDEN),
    INVALID_DEFAULT_ACCOUNT(1002, "Can not change email of default account or detele default account", HttpStatus.FORBIDDEN),
    TOKEN_GENERATION_FAILED(1003, "Failed to generate token", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1004, "User with provided email already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "User not found", HttpStatus.NOT_FOUND),

    EXAM_EXISTED(2001, "Exam with provided details already exists", HttpStatus.BAD_REQUEST),
    EXAM_NOT_FOUND(2002, "Exam not found", HttpStatus.NOT_FOUND),

    QUESTION_EXISTED(3001, "Question with provided details already exists", HttpStatus.BAD_REQUEST),
    QUESTION_NOT_FOUND(3002, "Question not found", HttpStatus.NOT_FOUND),

    ANSWER_EXISTED(4001, "Answer with provided details already exists", HttpStatus.BAD_REQUEST),
    ANSWER_NOT_FOUND(4002, "Answer not found", HttpStatus.NOT_FOUND),

    EXAM_SUBMISSION_EXISTED(5001, "Exam submission with provided details already exists", HttpStatus.BAD_REQUEST),
    EXAM_SUBMISSION_NOT_FOUND(5002, "Exam submission not found", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;

    private final String message;

    private final HttpStatusCode statusCode;
}
