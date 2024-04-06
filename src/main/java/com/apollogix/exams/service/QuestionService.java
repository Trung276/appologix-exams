package com.apollogix.exams.service;

import com.apollogix.exams.dto.request.question.QuestionCreationRequest;
import com.apollogix.exams.dto.request.question.QuestionUpdateRequest;
import com.apollogix.exams.dto.response.question.QuestionResponse;

import java.util.List;

public interface QuestionService {

    QuestionResponse create(QuestionCreationRequest request);

    QuestionResponse getById(String questionId);

    QuestionResponse updateById(String questionId, QuestionUpdateRequest request);

    void deleteById(String questionId);

    List<QuestionResponse> getAll();

    List<QuestionResponse> getByExamId(String examId);
}
