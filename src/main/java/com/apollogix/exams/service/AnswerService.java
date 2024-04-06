package com.apollogix.exams.service;

import com.apollogix.exams.dto.request.answer.AnswerCreationRequest;
import com.apollogix.exams.dto.request.answer.AnswerUpdateRequest;
import com.apollogix.exams.dto.response.answer.AnswerResponse;

import java.util.List;

public interface AnswerService {

    AnswerResponse create(AnswerCreationRequest request);

    AnswerResponse getById(String answerId);

    AnswerResponse updateById(String answerId, AnswerUpdateRequest request);

    void deleteById(String answerId);

    List<AnswerResponse> getAll();

    List<AnswerResponse> getByQuestion(String questionId);

    List<AnswerResponse> getCorrectAnswerByQuestion(String questionId);
}
