package com.apollogix.exams.controller;

import com.apollogix.exams.dto.request.question.QuestionCreationRequest;
import com.apollogix.exams.dto.request.question.QuestionUpdateRequest;
import com.apollogix.exams.dto.response.ApiResponse;
import com.apollogix.exams.dto.response.question.QuestionResponse;
import com.apollogix.exams.service.QuestionService;
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
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public ApiResponse<List<QuestionResponse>> getAll() {
        return ApiResponse.<List<QuestionResponse>>builder()
                .result(questionService.getAll())
                .build();
    }

    @PostMapping("")
    public ApiResponse<QuestionResponse> create(@RequestBody @Valid QuestionCreationRequest request) {
        return ApiResponse.<QuestionResponse>builder()
                .result(questionService.create(request))
                .build();
    }

    @GetMapping("/{questionId}")
    public ApiResponse<QuestionResponse> getById(@PathVariable String questionId) {
        return ApiResponse.<QuestionResponse>builder()
                .result(questionService.getById(questionId))
                .build();
    }


    @PutMapping("/{questionId}")
    public ApiResponse<QuestionResponse> updateById(@PathVariable String questionId, @RequestBody @Valid QuestionUpdateRequest request) {
        return ApiResponse.<QuestionResponse>builder()
                .result(questionService.updateById(questionId, request))
                .build();
    }

    @DeleteMapping("/{questionId}")
    public ApiResponse<String> deleteById(@PathVariable String questionId) {
        questionService.deleteById(questionId);
        return ApiResponse.<String>builder()
                .result("Question has been deleted")
                .build();
    }

    @GetMapping("/search/exam")
    public ApiResponse<List<QuestionResponse>> getQuestionsByExam(@RequestParam("id") String examId) {
        return ApiResponse.<List<QuestionResponse>>builder()
                .result(questionService.getByExamId(examId))
                .build();
    }
}
