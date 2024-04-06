package com.apollogix.exams.mapper;

import com.apollogix.exams.dto.request.answer.AnswerCreationRequest;
import com.apollogix.exams.dto.request.answer.AnswerUpdateRequest;
import com.apollogix.exams.dto.response.answer.AnswerContentResponse;
import com.apollogix.exams.dto.response.answer.AnswerResponse;
import com.apollogix.exams.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer toAnswer(AnswerCreationRequest request);

    @Mapping(source = "question.id", target = "questionId")
    AnswerResponse toAnswerResponse(Answer answer);

    AnswerContentResponse toAnswerContentResponse(Answer answer);

    Answer update(@MappingTarget Answer answer, AnswerUpdateRequest request);
}
