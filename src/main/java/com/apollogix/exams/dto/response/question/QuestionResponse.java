package com.apollogix.exams.dto.response.question;

import com.apollogix.exams.dto.response.answer.AnswerContentResponse;
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
public class QuestionResponse {
    private String id;
    private String content;
    private String examId;
    private Boolean isActivated;
    List<AnswerContentResponse> answers;
}
