package com.apollogix.exams.service.impl;

import com.apollogix.exams.dto.request.answer.AnswerCreationRequest;
import com.apollogix.exams.dto.request.answer.AnswerUpdateRequest;
import com.apollogix.exams.dto.response.answer.AnswerResponse;
import com.apollogix.exams.entity.Answer;
import com.apollogix.exams.entity.Question;
import com.apollogix.exams.exception.AppException;
import com.apollogix.exams.exception.ErrorCode;
import com.apollogix.exams.mapper.AnswerMapper;
import com.apollogix.exams.repository.AnswerRepository;
import com.apollogix.exams.repository.QuestionRepository;
import com.apollogix.exams.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public AnswerResponse create(AnswerCreationRequest request) {
        Answer answer = answerMapper.toAnswer(request);
        Question question = questionRepository.findByIdAndIsActivatedTrue(request.getQuestionId())
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));

        answer.setQuestion(question);
        answer = answerRepository.save(answer);

        return answerMapper.toAnswerResponse(answer);
    }

    @Override
    public AnswerResponse getById(String answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AppException(ErrorCode.ANSWER_NOT_FOUND));

        return answerMapper.toAnswerResponse(answer);
    }

    @Override
    public AnswerResponse updateById(String answerId, AnswerUpdateRequest request) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AppException(ErrorCode.ANSWER_NOT_FOUND));

        answer = answerMapper.update(answer, request);
        answer = answerRepository.save(answer);

        return answerMapper.toAnswerResponse(answer);
    }


    @Override
    public void deleteById(String answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AppException(ErrorCode.ANSWER_NOT_FOUND));

        answer.setIsActivated(true);

        answerRepository.save(answer);
    }

    @Override
    public List<AnswerResponse> getAll() {
        List<Answer> answers = answerRepository.findAll();

        return answers.stream()
                .map(answer -> answerMapper.toAnswerResponse(answer))
                .collect(Collectors.toList());
    }
    @Override
    public List<AnswerResponse> getByQuestion(String questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        return answers.stream()
                .map(answer -> answerMapper.toAnswerResponse(answer))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerResponse> getCorrectAnswerByQuestion(String questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionIdAndIsActivatedTrueAndIsCorrectTrue(questionId);

        return answers.stream()
                .map(answer -> answerMapper.toAnswerResponse(answer))
                .collect(Collectors.toList());
    }

}

