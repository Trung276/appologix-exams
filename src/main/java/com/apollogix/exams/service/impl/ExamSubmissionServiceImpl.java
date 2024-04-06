package com.apollogix.exams.service.impl;

import com.apollogix.exams.dto.request.exam.ExamSubmissionRequest;
import com.apollogix.exams.dto.response.answer.AnswerContentResponse;
import com.apollogix.exams.dto.response.exam.ExamSubmissionResponse;
import com.apollogix.exams.dto.response.question.QuestionSubmissionResponse;
import com.apollogix.exams.entity.Answer;
import com.apollogix.exams.entity.Exam;
import com.apollogix.exams.entity.ExamSubmission;
import com.apollogix.exams.entity.Question;
import com.apollogix.exams.entity.User;
import com.apollogix.exams.enums.Role;
import com.apollogix.exams.exception.AppException;
import com.apollogix.exams.exception.ErrorCode;
import com.apollogix.exams.mapper.AnswerMapper;
import com.apollogix.exams.mapper.ExamSubmissionMapper;
import com.apollogix.exams.mapper.QuestionMapper;
import com.apollogix.exams.repository.AnswerRepository;
import com.apollogix.exams.repository.ExamRepository;
import com.apollogix.exams.repository.ExamSubmissionRepository;
import com.apollogix.exams.repository.UserRepository;
import com.apollogix.exams.service.ExamSubmissionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ExamSubmissionServiceImpl implements ExamSubmissionService {
    @Autowired
    private ExamSubmissionRepository examSubmissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamSubmissionMapper examSubmissionMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public ExamSubmissionResponse create(ExamSubmissionRequest request) {
        ExamSubmission examSubmission = examSubmissionMapper.toExamSubmission(request);

        examSubmission.setSubmitter(getCurrentUser());

        List<Answer> answers = getSelectedAnswers(request.getSelectedAnswerIds());
        examSubmission.setSelectedAnswers(answers);

        Exam exam = examRepository.findByIdAndIsActivatedTrueAndQuestionsIsNotEmpty(request.getExamId())
                .orElseThrow(() -> new AppException(ErrorCode.EXAM_NOT_FOUND));

        examSubmission.setExam(exam);

        ExamSubmission createdExamSubmission = examSubmissionRepository.save(examSubmission);

        return toExamSubmissionResponse(createdExamSubmission);
    }

    @Override
    public List<ExamSubmissionResponse> getAll() {
        List<ExamSubmission> examSubmissions = examSubmissionRepository.findAll();

        return examSubmissions.stream()
                .map(this::toExamSubmissionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExamSubmissionResponse getById(String id) {
        ExamSubmission examSubmission = isTeacher(getCurrentUser()) ?
                examSubmissionRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.EXAM_SUBMISSION_NOT_FOUND)) :
                examSubmissionRepository.findByIdAndIsActivatedTrue(id)
                        .orElseThrow(() -> new AppException(ErrorCode.EXAM_SUBMISSION_NOT_FOUND));

        return toExamSubmissionResponse(examSubmission);
    }


    @Override
    public void deleteById(String id) {
        ExamSubmission examSubmission = examSubmissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EXAM_SUBMISSION_NOT_FOUND));

        examSubmission.setIsActivated(false);

        examSubmissionRepository.save(examSubmission);
    }

    @Override
    public List<ExamSubmissionResponse> getBySubmitter(String submitterId) {
        if(!userRepository.existsById(submitterId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        List<ExamSubmission> examSubmissions = isTeacher(getCurrentUser()) ?
                examSubmissionRepository.findAllBySubmitterId(submitterId) :
                examSubmissionRepository.findAllBySubmitterIdAndIsActivatedTrue(submitterId);

        return examSubmissions.stream()
                .map(this::toExamSubmissionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamSubmissionResponse> getByExam(String examId) {
        if(!examRepository.existsById(examId)) {
            throw new AppException(ErrorCode.EXAM_NOT_FOUND);
        }

        List<ExamSubmission> examSubmissions = isTeacher(getCurrentUser()) ?
                examSubmissionRepository.findAllByExamId(examId) :
                examSubmissionRepository.findAllByExamIdAndIsActivatedTrue(examId);

        return examSubmissions.stream()
                .map(this::toExamSubmissionResponse)
                .collect(Collectors.toList());
    }

    private ExamSubmissionResponse toExamSubmissionResponse(ExamSubmission examSubmission) {
        ExamSubmissionResponse response = examSubmissionMapper.toExamSubmissionResponse(examSubmission);
        List<QuestionSubmissionResponse> questions = getQuestionsForResponse(examSubmission);
        response.setQuestions(questions);
        response.setResult(getResult(response));

        return response;
    }

    private List<QuestionSubmissionResponse> getQuestionsForResponse(ExamSubmission examSubmission) {
        List<Question> questions = examSubmission.getExam().getQuestions();

        return questions.stream()
                .map(question -> {
                    QuestionSubmissionResponse response = questionMapper.toQuestionSubmissionResponse(question);
                    response.setAnswers(getAnswersForQuestion(question.getAnswers()));
                    response.setSelectedAnswersIds(getSelectedAnswerIdsForQuestion(question, examSubmission.getSelectedAnswers()));
                    response.setCorrectAnswerIds(getCorrectAnswerIdsForQuestion(question));
                    response.setIsCorrect(isCorrectQuestion(response.getSelectedAnswersIds(), response.getCorrectAnswerIds()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    private List<AnswerContentResponse> getAnswersForQuestion(List<Answer> answers) {
        return answers != null ?
                answers.stream()
                        .map(answerMapper::toAnswerContentResponse)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }

    private List<String> getSelectedAnswerIdsForQuestion(Question question, List<Answer> selectedAnswers) {
        return selectedAnswers.stream()
                .filter(answer -> answer.getQuestion().getId().equals(question.getId()))
                .map(Answer::getId)
                .toList();
    }

    private List<String> getCorrectAnswerIdsForQuestion(Question question) {
        List<Answer> answers = question.getAnswers();

        return answers.stream()
                .filter(Answer::getIsActivated)
                .filter(Answer::getIsCorrect)
                .map(Answer::getId)
                .toList();
    }

    private boolean isCorrectQuestion(List<String> selectedAnswerIds, List<String> correctAnswerIds) {
        return new HashSet<>(correctAnswerIds).containsAll(selectedAnswerIds);
    }

    private List<Answer> getSelectedAnswers(List<String> selectedAnswerIds) {
        return selectedAnswerIds.stream()
                .map(answerId -> answerRepository.findByIdAndIsActivatedTrue(answerId)
                        .orElseThrow(() -> new AppException(ErrorCode.ANSWER_NOT_FOUND)))
                .toList();
    }

    private String getResult(ExamSubmissionResponse examResponse) {
        List<QuestionSubmissionResponse> questions = examResponse.getQuestions();
        int correctQuestions = 0;

        for (QuestionSubmissionResponse question : questions) {
            if (question.getIsCorrect()) {
                correctQuestions++;
            }
        }

        return correctQuestions + "/" + questions.size();
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
