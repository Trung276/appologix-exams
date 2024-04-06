package com.apollogix.exams.controller;

import com.apollogix.exams.dto.request.exam.ExamSubmissionRequest;
import com.apollogix.exams.dto.response.ApiResponse;
import com.apollogix.exams.dto.response.exam.ExamSubmissionResponse;
import com.apollogix.exams.service.ExamSubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exam-submissions")
public class ExamSubmissionController {

    @Autowired
    private ExamSubmissionService examSubmissionService;

    @GetMapping
    public ApiResponse<List<ExamSubmissionResponse>> getAll() {
        return ApiResponse.<List<ExamSubmissionResponse>>builder()
                .result(examSubmissionService.getAll())
                .build();
    }

    @PostMapping("")
    public ApiResponse<ExamSubmissionResponse> create(@RequestBody @Valid ExamSubmissionRequest request) {
        return ApiResponse.<ExamSubmissionResponse>builder()
                .result(examSubmissionService.create(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ExamSubmissionResponse> getById(@PathVariable String id) {
        return ApiResponse.<ExamSubmissionResponse>builder()
                .result(examSubmissionService.getById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        examSubmissionService.deleteById(id);
        return ApiResponse.<String>builder()
                .result("Exam submission has been deleted")
                .build();
    }

    @GetMapping("/search/submitter")
    public ApiResponse<List<ExamSubmissionResponse>> getBySubmitter(@RequestParam("id") String submitterId) {
        return ApiResponse.<List<ExamSubmissionResponse>>builder()
                .result(examSubmissionService.getBySubmitter(submitterId))
                .build();
    }

    @GetMapping("/search/exam")
    public ApiResponse<List<ExamSubmissionResponse>> getByExam(@RequestParam("id") String examId) {
        return ApiResponse.<List<ExamSubmissionResponse>>builder()
                .result(examSubmissionService.getByExam(examId))
                .build();
    }

}

