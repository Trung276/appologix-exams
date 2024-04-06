package com.apollogix.exams.dto.response.exam;

import com.apollogix.exams.dto.response.question.QuestionSubmissionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExamSubmissionResponse {
    private String id;

    private String examId;
    private String examTitle;
    private String examDescription;
    private Integer examDuration;
    private String examCreatorId;
    private String examCreatorName;

    private String submitterId;
    private String submitterName;

    private Date submitTime;

    private List<QuestionSubmissionResponse> questions;
    private String result;
    private Boolean isActivated;
}
