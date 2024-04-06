package com.apollogix.exams.dto.request.exam;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExamSubmissionRequest {

    @NotBlank
    private String examId;

    private List<String> selectedAnswerIds;
}
