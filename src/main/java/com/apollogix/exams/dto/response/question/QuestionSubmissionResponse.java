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
public class QuestionSubmissionResponse {
    private String id;
    private String content;
    List<AnswerContentResponse> answers;
    List<String> selectedAnswersIds;
    List<String> correctAnswerIds;
    Boolean isCorrect;
}
