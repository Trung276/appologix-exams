package com.apollogix.exams.mapper;

import com.apollogix.exams.dto.request.exam.ExamSubmissionRequest;
import com.apollogix.exams.dto.response.exam.ExamSubmissionResponse;
import com.apollogix.exams.entity.ExamSubmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ExamSubmissionMapper {
    ExamSubmission toExamSubmission(ExamSubmissionRequest request);

    @Mapping(source = "submitter.id", target = "submitterId")
    @Mapping(source = "submitter.fullName", target = "submitterName")
    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "exam.title", target = "examTitle")
    @Mapping(source = "exam.description", target = "examDescription")
    @Mapping(source = "exam.duration", target = "examDuration")
    @Mapping(source = "exam.creator.id", target = "examCreatorId")
    @Mapping(source = "exam.creator.fullName", target = "examCreatorName")
    ExamSubmissionResponse toExamSubmissionResponse(ExamSubmission examSubmission);
}
