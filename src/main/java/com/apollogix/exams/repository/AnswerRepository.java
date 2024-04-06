package com.apollogix.exams.repository;


import com.apollogix.exams.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
    List<Answer> findAllByQuestionIdAndIsActivatedTrueAndIsCorrectTrue(String questionId);

    List<Answer> findAllByQuestionId(String questionId);

    Optional<Answer> findByIdAndIsActivatedTrue(String id);
}
