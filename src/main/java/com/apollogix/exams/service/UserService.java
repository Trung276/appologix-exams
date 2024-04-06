package com.apollogix.exams.service;

import com.apollogix.exams.dto.request.user.UserCreationRequest;
import com.apollogix.exams.dto.request.user.UserUpdateEmailRequest;
import com.apollogix.exams.dto.request.user.UserUpdateRequest;
import com.apollogix.exams.dto.response.user.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserCreationRequest request);

    UserResponse getById(String userId);

    UserResponse updateById(String userId, UserUpdateRequest request);

    UserResponse updateEmail(UserUpdateEmailRequest request);

    void deleteById(String userId);

    List<UserResponse> getAll();

    UserResponse getUsersByEmail(String email);

    List<UserResponse> getUsersByKeyword(String keyword);

    List<UserResponse> getUsersByRole(String role);

    UserResponse getCurrentUser();

}
