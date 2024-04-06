package com.apollogix.exams.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserCreationRequest {

    @Email(message = "INVALID_EMAIL")
    private String email;

    @NotBlank(message = "INVALID_PASSWORD")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "INVALID_FULL_NAME")
    private String fullName;

    @NotNull(message = "INVALID_DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Pattern(regexp = "^(TEACHER|STUDENT)$", message = "INVALID_ROLE")
    private String role;

}
