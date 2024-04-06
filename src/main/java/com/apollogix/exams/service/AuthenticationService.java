package com.apollogix.exams.service;

import com.apollogix.exams.dto.request.auth.AuthenticationRequest;
import com.apollogix.exams.dto.request.auth.TokenValidationRequest;
import com.apollogix.exams.dto.response.auth.AuthenticationResponse;
import com.apollogix.exams.dto.response.auth.TokenValidationResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    TokenValidationResponse validToken(TokenValidationRequest request);

    String logout(HttpServletRequest request);

    boolean isTokenValid(String token);

    AuthenticationResponse login(AuthenticationRequest request);

    void addToBlacklist(String token);

    boolean isTokenInBlacklist(String token);
}
