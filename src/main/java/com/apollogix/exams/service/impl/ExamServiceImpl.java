package com.apollogix.exams.service.impl;

import com.apollogix.exams.dto.request.exam.ExamCreationRequest;
import com.apollogix.exams.dto.request.exam.ExamUpdateRequest;
import com.apollogix.exams.dto.response.answer.AnswerContentResponse;
import com.apollogix.exams.dto.response.exam.ExamResponse;
import com.apollogix.exams.dto.response.question.QuestionResponse;
import com.apollogix.exams.entity.Answer;
import com.apollogix.exams.entity.Exam;
import com.apollogix.exams.entity.Question;
import com.apollogix.exams.entity.User;
import com.apollogix.exams.enums.Role;
import com.apollogix.exams.exception.AppException;
import com.apollogix.exams.exception.ErrorCode;
import com.apollogix.exams.mapper.AnswerMapper;
import com.apollogix.exams.mapper.ExamMapper;
import com.apollogix.exams.mapper.QuestionMapper;
import com.apollogix.exams.repository.ExamRepository;
import com.apollogix.exams.repository.UserRepository;
import com.apollogix.exams.service.ExamService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public ExamResponse create(ExamCreationRequest request) {
        Exam exam = examMapper.toExam(request);

        exam.setCreator(getCurrentUser());
        exam = examRepository.save(exam);

        ExamResponse examResponse = examMapper.toExamResponse(exam);
        examResponse.setQuestions(new ArrayList<>());

        return examResponse;
    }


    @Override
    public ExamResponse getById(String examId) {
        Exam exam = isTeacher(getCurrentUser()) ?
                examRepository.findById(examId)
                        .orElseThrow(() -> new AppException(ErrorCode.EXAM_NOT_FOUND)) :
                examRepository.findByIdAndIsActivatedTrueAndQuestionsIsNotEmpty(examId)
                        .orElseThrow(() -> new AppException(ErrorCode.EXAM_NOT_FOUND));

        ExamResponse examResponse = examMapper.toExamResponse(exam);
        setQuestionsToResponse(examResponse, exam);

        return examResponse;
    }


    @Override
    public ExamResponse updateById(String examId, ExamUpdateRequest request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new AppException(ErrorCode.EXAM_NOT_FOUND));

        exam = examMapper.update(exam, request);
        exam = examRepository.save(exam);

        ExamResponse examResponse = examMapper.toExamResponse(exam);
        setQuestionsToResponse(examResponse, exam);

        return examResponse;
    }

    @Override
    public void deleteById(String examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new AppException(ErrorCode.EXAM_NOT_FOUND));

        exam.setIsActivated(false);

        examRepository.save(exam);
    }

    @Override
    public List<ExamResponse> getAll() {
        List<Exam> exams = isTeacher(getCurrentUser()) ?
                examRepository.findAll() :
                examRepository.findAllByIsActivatedTrueAndQuestionsIsNotEmpty();

        return exams.stream()
                .map(exam -> {
                    ExamResponse examResponse = examMapper.toExamResponse(exam);
                    setQuestionsToResponse(examResponse, exam);
                    return examResponse;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<ExamResponse> getExamsByCreator(String creatorId) {
        List<Exam> exams = isTeacher(getCurrentUser()) ?
                examRepository.findAllByCreatorId(creatorId) :
                examRepository.findAllByCreatorIdAndIsActivatedTrueAndQuestionsIsNotEmpty(creatorId);

        return exams.stream()
                .map(exam -> {
                    ExamResponse examResponse = examMapper.toExamResponse(exam);
                    setQuestionsToResponse(examResponse, exam);
                    return examResponse;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<ExamResponse> getExamsByKeyword(String keyword) {
        List<Exam> exams = isTeacher(getCurrentUser()) ?
                examRepository.findAllByTitleContainingIgnoreCase(keyword) :
                examRepository.findAllByTitleContainingIgnoreCaseAndIsActivatedTrueAndQuestionsIsNotEmpty(keyword);

        return exams.stream()
                .map(exam -> {
                    ExamResponse examResponse = examMapper.toExamResponse(exam);
                    setQuestionsToResponse(examResponse, exam);
                    return examResponse;
                })
                .collect(Collectors.toList());
    }

    private void setQuestionsToResponse(ExamResponse examResponse, Exam exam) {
        List<Question> filteredQuestions = filterActivatedQuestions(exam.getQuestions());

        List<QuestionResponse> questionResponses = filteredQuestions.stream()
                .map(question -> {
                    QuestionResponse questionResponse = questionMapper.toQuestionResponse(question);
                    questionResponse.setAnswers(getAnswers(question.getAnswers()));
                    return questionResponse;
                })
                .collect(Collectors.toList());

        examResponse.setQuestions(questionResponses);
    }

    private List<Question> filterActivatedQuestions(List<Question> questions) {
        if (!isTeacher(getCurrentUser())) {
            return questions.stream()
                    .filter(Question::getIsActivated)
                    .filter(question -> hasCorrectAnswer(question.getAnswers()))
                    .collect(Collectors.toList());
        } else {
            return questions;
        }
    }

    private boolean hasCorrectAnswer(List<Answer> answers) {
        return answers.stream()
                .anyMatch(answer -> Boolean.TRUE.equals(answer.getIsActivated()) && Boolean.TRUE.equals(answer.getIsCorrect()));
    }

    private List<AnswerContentResponse> getAnswers(List<Answer> answers) {
        return answers != null ?
                answers.stream()
                        .map(answerMapper::toAnswerContentResponse)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName();

            return userRepository.findByEmailAndIsActivatedTrue(email)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        } else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public boolean isTeacher(User user) {
        return user.getRole().equals(Role.TEACHER);
    }
}
