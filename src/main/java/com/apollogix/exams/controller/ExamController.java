package com.apollogix.exams.controller;

import com.apollogix.exams.dto.request.exam.ExamCreationRequest;
import com.apollogix.exams.dto.request.exam.ExamUpdateRequest;
import com.apollogix.exams.dto.response.ApiResponse;
import com.apollogix.exams.dto.response.exam.ExamResponse;
import com.apollogix.exams.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("")
    public ApiResponse<List<ExamResponse>> getAll() {
        return ApiResponse.<List<ExamResponse>>builder()
                .result(examService.getAll())
                .build();
    }

    @PostMapping("")
    public ApiResponse<ExamResponse> create(@RequestBody @Valid ExamCreationRequest request) {
        return ApiResponse.<ExamResponse>builder()
                .result(examService.create(request))
                .build();
    }

    @GetMapping("/{examId}")
    public ApiResponse<ExamResponse> getById(@PathVariable String examId) {
        return ApiResponse.<ExamResponse>builder()
                .result(examService.getById(examId))
                .build();
    }

    @DeleteMapping("/{examId}")
    public ApiResponse<String> deleteById(@PathVariable String examId) {
        examService.deleteById(examId);
        return ApiResponse.<String>builder()
                .result("Exam has been deleted")
                .build();
    }

    @PutMapping("/{examId}")
    public ApiResponse<ExamResponse> updateById(@PathVariable String examId, @RequestBody @Valid ExamUpdateRequest request) {
        return ApiResponse.<ExamResponse>builder()
                .result(examService.updateById(examId, request))
                .build();
    }

    @GetMapping("/search/title")
    public ApiResponse<List<ExamResponse>> getExamsByTitle(@RequestParam String keyword) {
        return ApiResponse.<List<ExamResponse>>builder()
                .result(examService.getExamsByKeyword(keyword))
                .build();
    }

    @GetMapping("/search/creator")
    public ApiResponse<List<ExamResponse>> getExamsByCreator(@RequestParam("id") String creatorId) {
        return ApiResponse.<List<ExamResponse>>builder()
                .result(examService.getExamsByCreator(creatorId))
                .build();
    }

}
