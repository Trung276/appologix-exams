package com.apollogix.exams.repository;


import com.apollogix.exams.entity.ExamSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamSubmissionRepository extends JpaRepository<ExamSubmission, String> {
    List<ExamSubmission> findAllBySubmitterId(String submitterId);

    List<ExamSubmission> findAllByExamId(String examId);

    List<ExamSubmission> findAllBySubmitterIdAndIsActivatedTrue(String submitterId);

    List<ExamSubmission> findAllByExamIdAndIsActivatedTrue(String examId);

    List<ExamSubmission> findAllByIsActivatedTrue();

    Optional<ExamSubmission> findByIdAndIsActivatedTrue(String id);
}
