package com.apollogix.exams.repository;


import com.apollogix.exams.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {

    List<Exam> findAllByTitleContainingIgnoreCase(String keyword);

    List<Exam> findAllByCreatorId(String creatorId);

    Optional<Exam> findByIdAndIsActivatedTrueAndQuestionsIsNotEmpty(String id);

    Optional<Exam> findByIdAndIsActivatedTrue(String id);

    List<Exam> findAllByTitleContainingIgnoreCaseAndIsActivatedTrueAndQuestionsIsNotEmpty(String keyword);

    List<Exam> findAllByCreatorIdAndIsActivatedTrueAndQuestionsIsNotEmpty(String creatorId);

    List<Exam> findAllByIsActivatedTrueAndQuestionsIsNotEmpty();
}
