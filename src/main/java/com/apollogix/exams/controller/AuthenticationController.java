package com.apollogix.exams.controller;


import com.apollogix.exams.dto.request.auth.AuthenticationRequest;
import com.apollogix.exams.dto.request.auth.TokenValidationRequest;
import com.apollogix.exams.dto.response.ApiResponse;
import com.apollogix.exams.dto.response.auth.AuthenticationResponse;
import com.apollogix.exams.dto.response.auth.TokenValidationResponse;
import com.apollogix.exams.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.login(request))
                .build();
    }

    @PostMapping("token-validation")
    ApiResponse<TokenValidationResponse> validToken(@RequestBody TokenValidationRequest request) {
        return ApiResponse.<TokenValidationResponse>builder()
                .result(authenticationService.validToken(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<String> logout(HttpServletRequest request) {
        return ApiResponse.<String>builder()
                .result(authenticationService.logout(request))
                .build();
    }

}
