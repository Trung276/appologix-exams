package com.apollogix.exams.service.impl;

import com.apollogix.exams.dto.request.question.QuestionCreationRequest;
import com.apollogix.exams.dto.request.question.QuestionUpdateRequest;
import com.apollogix.exams.dto.response.answer.AnswerContentResponse;
import com.apollogix.exams.dto.response.question.QuestionResponse;
import com.apollogix.exams.entity.Answer;
import com.apollogix.exams.entity.Exam;
import com.apollogix.exams.entity.Question;
import com.apollogix.exams.exception.AppException;
import com.apollogix.exams.exception.ErrorCode;
import com.apollogix.exams.mapper.AnswerMapper;
import com.apollogix.exams.mapper.QuestionMapper;
import com.apollogix.exams.repository.ExamRepository;
import com.apollogix.exams.repository.QuestionRepository;
import com.apollogix.exams.service.QuestionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public QuestionResponse create(QuestionCreationRequest request) {
        Question question = questionMapper.toQuestion(request);
        Exam exam = examRepository.findByIdAndIsActivatedTrue(request.getExamId())
                .orElseThrow(() -> new AppException(ErrorCode.EXAM_NOT_FOUND));

        question.setExam(exam);
        question.setAnswers(new ArrayList<>());
        question = questionRepository.save(question);

        return questionMapper.toQuestionResponse(question);
    }


    @Override
    public QuestionResponse getById(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));

        QuestionResponse questionResponse = questionMapper.toQuestionResponse(question);
        setAnswersToResponse(question.getAnswers(), questionResponse);

        return questionResponse;
    }


    @Override
    public QuestionResponse updateById(String questionId, QuestionUpdateRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));

        question = questionMapper.update(question, request);
        question = questionRepository.save(question);

        QuestionResponse questionResponse = questionMapper.toQuestionResponse(question);
        setAnswersToResponse(question.getAnswers(), questionResponse);

        return questionResponse;
    }

    @Override
    public void deleteById(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));

        question.setIsActivated(true);

        questionRepository.save(question);
    }

    @Override
    public List<QuestionResponse> getAll() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(question -> {
                    QuestionResponse questionResponse = questionMapper.toQuestionResponse(question);
                    setAnswersToResponse(question.getAnswers(), questionResponse);
                    return questionResponse;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<QuestionResponse> getByExamId(String examId) {
        List<Question> questions = questionRepository.findAllByExamId(examId);

        return questions.stream()
                .map(question -> {
                    QuestionResponse questionResponse = questionMapper.toQuestionResponse(question);
                    setAnswersToResponse(question.getAnswers(), questionResponse);
                    return questionResponse;
                })
                .collect(Collectors.toList());
    }

    private void setAnswersToResponse(List<Answer> answers, QuestionResponse questionResponse) {
        List<AnswerContentResponse> answerContentResponses = answers.stream()
                .map(answerMapper::toAnswerContentResponse)
                .collect(Collectors.toList());

        questionResponse.setAnswers(answerContentResponses);
    }

}
