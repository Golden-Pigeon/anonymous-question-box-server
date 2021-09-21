package com.example.anonymousquestionboxserver.service;

import com.example.anonymousquestionboxserver.model.dto.ResultDTO;

public interface QuestionService {
    ResultDTO getAnsweredList(String identify);

    ResultDTO getDetailedQuestion(String questionId);

    ResultDTO getNotAnsweredList(String sessionKey);

    ResultDTO answerQuestion(String sessionKey, String content);
}
