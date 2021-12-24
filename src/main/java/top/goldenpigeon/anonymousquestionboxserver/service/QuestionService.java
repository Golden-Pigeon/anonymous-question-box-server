package top.goldenpigeon.anonymousquestionboxserver.service;

import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;

public interface QuestionService {
    ResultDTO getAnsweredList(String identify);

    ResultDTO getDetailedQuestion(Long questionId);

    ResultDTO getNotAnsweredList(String sessionKey);

    ResultDTO answerQuestion(String sessionKey, String content, Long questionId);

    ResultDTO askQuestion(String sessionKey, String question, String answererIdentify);
}
