package com.apollogix.exams.repository;


import com.apollogix.exams.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findAllByExamId(String examId);

    Optional<Question> findByIdAndIsActivatedTrue(String id);
}
