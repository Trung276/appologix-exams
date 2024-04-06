package com.apollogix.exams.service;


import com.apollogix.exams.dto.request.exam.ExamCreationRequest;
import com.apollogix.exams.dto.request.exam.ExamUpdateRequest;
import com.apollogix.exams.dto.response.exam.ExamResponse;

import java.util.List;

public interface ExamService {

    ExamResponse create(ExamCreationRequest request);

    ExamResponse getById(String examId);

    ExamResponse updateById(String examId, ExamUpdateRequest request);

    void deleteById(String examId);

    List<ExamResponse> getAll();

    List<ExamResponse> getExamsByCreator(String creatorId);

    List<ExamResponse> getExamsByKeyword(String keyword);
}
