package com.apollogix.exams.dto.request.user;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String password;

    @NotBlank(message = "INVALID_FULL_NAME")
    private String fullName;

    @NotNull(message = "INVALID_DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @NotBlank(message = "INVALID_ROLE")
    private String role;

    @NotNull
    private Boolean isActivated;
}
