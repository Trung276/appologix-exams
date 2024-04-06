package com.apollogix.exams.dto.request.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AnswerUpdateRequest {
    @NotBlank
    private String content;
    @NotNull
    private Boolean isCorrect;
    @NotNull
    private Boolean isActivated;
}
