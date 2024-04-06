package com.apollogix.exams.mapper;

import com.apollogix.exams.dto.request.question.QuestionCreationRequest;
import com.apollogix.exams.dto.request.question.QuestionUpdateRequest;
import com.apollogix.exams.dto.response.question.QuestionResponse;
import com.apollogix.exams.dto.response.question.QuestionSubmissionResponse;
import com.apollogix.exams.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toQuestion(QuestionCreationRequest request);

    @Mapping(source = "exam.id", target = "examId")
    QuestionResponse toQuestionResponse(Question question);

    QuestionSubmissionResponse toQuestionSubmissionResponse(Question question);

    Question update(@MappingTarget Question question, QuestionUpdateRequest request);
}
