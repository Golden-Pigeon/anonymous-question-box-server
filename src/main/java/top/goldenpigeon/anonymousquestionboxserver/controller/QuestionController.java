package top.goldenpigeon.anonymousquestionboxserver.controller;

import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;
import top.goldenpigeon.anonymousquestionboxserver.service.QuestionService;
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
    ResultDTO getAnswer(@RequestParam(value = "question_id") Long questionId) {
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
                     @RequestParam(value = "question_id") Long questionId) {
        return questionService.answerQuestion(sessionKey, content, questionId);
    }

    @PostMapping("/ask")
    @ResponseBody
    ResultDTO ask(@RequestParam(value = "question") String question,
                  @RequestParam(value = "session_key") String sessionKey,
                  @RequestParam(value = "user_id") String answererId){
        return questionService.askQuestion(sessionKey, question, answererId);
    }
}
