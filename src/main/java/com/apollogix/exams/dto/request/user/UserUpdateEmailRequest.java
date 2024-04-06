package com.apollogix.exams.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateEmailRequest {
    @NotBlank
    @Email(message = "INVALID_EMAIL")
    private String oldEmail;

    @NotBlank
    @Email(message = "INVALID_EMAIL")
    private String newEmail;
}
