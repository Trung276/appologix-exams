package com.apollogix.exams.dto.request.exam;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExamCreationRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Min(value = 5, message = "Duration must be greater than 5 minutes")
    private Integer duration;
}
