package com.apollogix.exams.dto.response.exam;

import com.apollogix.exams.dto.response.question.QuestionResponse;
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
public class ExamResponse {
    private String id;
    private String title;
    private String description;
    private String creatorId;
    private String creatorName;
    private Integer duration;
    private Boolean isActivated;
    private List<QuestionResponse> questions;
}
