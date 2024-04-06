package com.apollogix.exams.mapper;

import com.apollogix.exams.dto.request.exam.ExamCreationRequest;
import com.apollogix.exams.dto.request.exam.ExamUpdateRequest;
import com.apollogix.exams.dto.response.exam.ExamResponse;
import com.apollogix.exams.entity.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ExamMapper {

    Exam toExam(ExamCreationRequest request);

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.fullName", target = "creatorName")
    ExamResponse toExamResponse(Exam exam);

    Exam update(@MappingTarget Exam exam, ExamUpdateRequest request);
}
