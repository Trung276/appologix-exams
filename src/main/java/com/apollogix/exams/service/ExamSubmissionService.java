package com.apollogix.exams.service;

import com.apollogix.exams.dto.request.exam.ExamSubmissionRequest;
import com.apollogix.exams.dto.response.exam.ExamSubmissionResponse;

import java.util.List;

public interface ExamSubmissionService {
    ExamSubmissionResponse create(ExamSubmissionRequest request);

    List<ExamSubmissionResponse> getAll();

    ExamSubmissionResponse getById(String examId);

    void deleteById(String id);

    List<ExamSubmissionResponse> getBySubmitter(String submitterId);

    List<ExamSubmissionResponse> getByExam(String examId);
}
