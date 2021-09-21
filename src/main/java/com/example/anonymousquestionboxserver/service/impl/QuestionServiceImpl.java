package com.example.anonymousquestionboxserver.service.impl;

import com.example.anonymousquestionboxserver.model.dto.ResultDTO;
import com.example.anonymousquestionboxserver.service.QuestionService;

public class QuestionServiceImpl implements QuestionService {
    @Override
    public ResultDTO getAnsweredList(String identify) {
        return null;
    }

    @Override
    public ResultDTO getDetailedQuestion(String questionId) {
        return null;
    }

    @Override
    public ResultDTO getNotAnsweredList(String sessionKey) {
        return null;
    }

    @Override
    public ResultDTO answerQuestion(String sessionKey, String content) {
        return null;
    }
}
