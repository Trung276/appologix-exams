package com.apollogix.exams.dto.response.answer;

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
public class AnswerResponse {
    private String id;
    private String content;
    private Boolean isCorrect;
    private String questionId;
    private Boolean isActivated;
}
