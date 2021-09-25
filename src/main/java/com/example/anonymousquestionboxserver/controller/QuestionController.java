package com.example.anonymousquestionboxserver.controller;

import com.example.anonymousquestionboxserver.model.dto.ResultDTO;
import com.example.anonymousquestionboxserver.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class QuestionController {

    @Resource
    QuestionService questionService;

    @GetMapping("/getAnswersBrief")
    @ResponseBody
    ResultDTO getAnswersBrief(@RequestParam(value = "user_id") String userId) {
        return questionService.getAnsweredList(userId);
    }

    @GetMapping("/getAnswer")
    @ResponseBody
    ResultDTO getAnswer(@RequestParam(value = "question_id") String questionId) {
        return questionService.getDetailedQuestion(questionId);
    }

    @GetMapping("/GetQuestions")
    @ResponseBody
    ResultDTO GetQuestions(@RequestParam(value = "session_key") String sessionKey) {
        return questionService.getNotAnsweredList(sessionKey);
    }

    @PostMapping("/answer")
    @ResponseBody
    ResultDTO answer(@RequestParam(value = "session_key") String sessionKey,
                     @RequestParam String content,
                     @RequestParam(value = "user_id") String userId) {
        return questionService.answerQuestion(sessionKey, content, userId);
    }
}
