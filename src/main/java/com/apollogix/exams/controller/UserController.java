package com.apollogix.exams.controller;

import com.apollogix.exams.dto.request.user.UserCreationRequest;
import com.apollogix.exams.dto.request.user.UserUpdateEmailRequest;
import com.apollogix.exams.dto.request.user.UserUpdateRequest;
import com.apollogix.exams.dto.response.ApiResponse;
import com.apollogix.exams.dto.response.user.UserResponse;
import com.apollogix.exams.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @PostMapping("")
    public ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.create(request))
                .build();
    }

    @PostMapping("/email")
    public ApiResponse<UserResponse> updateEmail(@RequestBody @Valid UserUpdateEmailRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateEmail(request))
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getCurrentUser())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getById(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getById(userId))
                .build();
    }


    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateById(@PathVariable String userId, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateById(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteById(@PathVariable String userId) {
        userService.deleteById(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }


    @GetMapping("/search/name")
    public ApiResponse<List<UserResponse>> getUsersByKeyword(@RequestParam String keyword) {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsersByKeyword(keyword))
                .build();
    }

    @GetMapping("/search/email")
    public ApiResponse<UserResponse> getUsersByEmail(@RequestParam String email) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUsersByEmail(email))
                .build();
    }

    @GetMapping("/search/role")
    public ApiResponse<List<UserResponse>> getUsersByRole(@RequestParam String role) {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsersByRole(role))
                .build();
    }
}
