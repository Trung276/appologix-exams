package com.apollogix.exams.controller;

import com.apollogix.exams.dto.request.answer.AnswerCreationRequest;
import com.apollogix.exams.dto.request.answer.AnswerUpdateRequest;
import com.apollogix.exams.dto.response.ApiResponse;
import com.apollogix.exams.dto.response.answer.AnswerResponse;
import com.apollogix.exams.service.AnswerService;
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
@RequestMapping("/api/v1/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("")
    public ApiResponse<List<AnswerResponse>> getAll() {
        return ApiResponse.<List<AnswerResponse>>builder()
                .result(answerService.getAll())
                .build();
    }

    @PostMapping("")
    public ApiResponse<AnswerResponse> create(@RequestBody @Valid AnswerCreationRequest request) {
        return ApiResponse.<AnswerResponse>builder()
                .result(answerService.create(request))
                .build();
    }

    @GetMapping("/{answerId}")
    public ApiResponse<AnswerResponse> getById(@PathVariable String answerId) {
        return ApiResponse.<AnswerResponse>builder()
                .result(answerService.getById(answerId))
                .build();
    }

    @PutMapping("/{answerId}")
    public ApiResponse<AnswerResponse> updateById(@PathVariable String answerId, @RequestBody @Valid AnswerUpdateRequest request) {
        return ApiResponse.<AnswerResponse>builder()
                .result(answerService.updateById(answerId, request))
                .build();
    }

    @DeleteMapping("/{answerId}")
    public ApiResponse<String> deleteById(@PathVariable String answerId) {
        answerService.deleteById(answerId);
        return ApiResponse.<String>builder()
                .result("Answer has been deleted")
                .build();
    }

    @GetMapping("/search/question")
    public ApiResponse<List<AnswerResponse>> getAnswersByQuestion(@RequestParam("id") String questionId) {
        return ApiResponse.<List<AnswerResponse>>builder()
                .result(answerService.getByQuestion(questionId))
                .build();
    }

    @GetMapping("/search/correct-answer/question")
    public ApiResponse<List<AnswerResponse>> getCorrectAnswerByQuestion(@RequestParam("id") String questionId) {
        return ApiResponse.<List<AnswerResponse>>builder()
                .result(answerService.getCorrectAnswerByQuestion(questionId))
                .build();
    }


}
