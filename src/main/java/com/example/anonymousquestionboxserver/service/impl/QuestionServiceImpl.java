package com.example.anonymousquestionboxserver.service.impl;

import com.example.anonymousquestionboxserver.common.enums.ResultEnum;
import com.example.anonymousquestionboxserver.model.dto.QuestionBriefDTO;
import com.example.anonymousquestionboxserver.model.dto.QuestionDetailedDTO;
import com.example.anonymousquestionboxserver.model.dto.ResultDTO;
import com.example.anonymousquestionboxserver.model.entity.Question;
import com.example.anonymousquestionboxserver.model.entity.User;
import com.example.anonymousquestionboxserver.repository.QuestionRepository;
import com.example.anonymousquestionboxserver.repository.UserRepository;
import com.example.anonymousquestionboxserver.service.QuestionService;
import com.example.anonymousquestionboxserver.service.helper.RedisHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisHelper redisHelper;
    @Override
    public ResultDTO getAnsweredList(String identify) {
        List<Question> questions = questionRepository.findAllByAnswererIdentify(identify);
        List<QuestionBriefDTO> briefs = new ArrayList<>();
        questions.forEach(question -> {
            Optional<User> answerer = userRepository.findByIdentify(question.getAnswererIdentify());
            if(!answerer.isPresent()){
                return;
            }
            if(StringUtils.isNotBlank(question.getContent())){
                QuestionBriefDTO brief = new QuestionBriefDTO(
                        question.getId(),
                        question.getAskDate(),
                        question.getLastModifiedDate(),
                        "",
                        answerer.get().getName(),
                        question.getQuestion().substring(0, 10),
                        question.getContent().substring(0, 10)
                );
                Optional<User> questioner = userRepository.findByIdentify(question.getQuestionerIdentify());
                if(!questioner.isPresent()){
                    return;
                }
                brief.setQuestionerName(questioner.get().getName());
                briefs.add(brief);
            }
        });
        ResultDTO result = new ResultDTO(ResultEnum.SUCCESS);
        result.setData(briefs);
        return result;
    }

    @Override
    public ResultDTO getDetailedQuestion(String questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(!question.isPresent()){
            return new ResultDTO(ResultEnum.QUESTION_NOT_FOUND);
        }
        Question q = question.get();
        QuestionDetailedDTO detailed = new QuestionDetailedDTO(
                q.getId(),
                q.getAskDate(),
                q.getLastModifiedDate(),
                "",
                "",
                q.getQuestion(),
                q.getContent()
        );
        Optional<User> questioner = userRepository.findByIdentify(q.getQuestionerIdentify());
        Optional<User> answerer = userRepository.findByIdentify(q.getAnswererIdentify());
        if(!questioner.isPresent() || !answerer.isPresent()){
            return new ResultDTO(ResultEnum.UNKNOWN_ERROR);
        }
        detailed.setQuestionerName(questioner.get().getName());
        detailed.setAnswererName(answerer.get().getName());
        ResultDTO result = new ResultDTO(ResultEnum.SUCCESS);
        result.setData(detailed);
        return result;
    }

    @Override
    public ResultDTO getNotAnsweredList(String sessionKey) {
        //TODO: 处理sessionKey刚好过期的情况
        List<Question> questions = questionRepository.findAllByAnswererIdentify(redisHelper.getValue(sessionKey));
        List<QuestionBriefDTO> detials = new ArrayList<>();
        questions.forEach(question -> {
            Optional<User> answerer = userRepository.findByIdentify(question.getAnswererIdentify());
            if(!answerer.isPresent()){
                return;
            }
            if(StringUtils.isBlank(question.getContent())){
                QuestionBriefDTO detail = new QuestionBriefDTO(
                        question.getId(),
                        question.getAskDate(),
                        question.getLastModifiedDate(),
                        "",
                        answerer.get().getName(),
                        question.getQuestion(),
                        ""
                );
                Optional<User> questioner = userRepository.findByIdentify(question.getQuestionerIdentify());
                if(!questioner.isPresent()){
                    return;
                }
                detail.setQuestionerName(questioner.get().getName());
                detials.add(detail);
            }
        });
        ResultDTO result = new ResultDTO(ResultEnum.SUCCESS);
        result.setData(detials);
        return result;
    }

    @Override
    public ResultDTO answerQuestion(String sessionKey, String content, String questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(!question.isPresent()){
            return new ResultDTO(ResultEnum.QUESTION_NOT_FOUND);
        }
        Question q = question.get();
        //TODO: 处理sessionKey刚好过期的情况
        if(!redisHelper.getValue(sessionKey).equals(q.getAnswererIdentify())){
            return new ResultDTO(ResultEnum.PERMISSION_DENIED);
        }
        q.setContent(content);
        questionRepository.save(q);
        return new ResultDTO(ResultEnum.SUCCESS);
    }
}
