package top.goldenpigeon.anonymousquestionboxserver.service.impl;

import top.goldenpigeon.anonymousquestionboxserver.common.enums.ResultEnum;
import top.goldenpigeon.anonymousquestionboxserver.model.dto.QuestionBriefDTO;
import top.goldenpigeon.anonymousquestionboxserver.model.dto.QuestionDetailedDTO;
import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;
import top.goldenpigeon.anonymousquestionboxserver.model.entity.Question;
import top.goldenpigeon.anonymousquestionboxserver.model.entity.User;
import top.goldenpigeon.anonymousquestionboxserver.repository.QuestionRepository;
import top.goldenpigeon.anonymousquestionboxserver.repository.UserRepository;
import top.goldenpigeon.anonymousquestionboxserver.service.QuestionService;
import top.goldenpigeon.anonymousquestionboxserver.service.helper.RedisHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
                        myBriefString(question.getQuestion()),
                        myBriefString(question.getContent())
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


    private String myBriefString(String s){
        if(s.length() > 10){
            return s.substring(0, 10);
        }
        else {
            return s;
        }
    }

    @Override
    public ResultDTO getDetailedQuestion(Long questionId) {
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
    public ResultDTO answerQuestion(String sessionKey, String content, Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(!question.isPresent()){
            return new ResultDTO(ResultEnum.QUESTION_NOT_FOUND);
        }
        Question q = question.get();
        //TODO: 处理sessionKey刚好过期的情况
        if(!redisHelper.getValue(sessionKey).equals(q.getAnswererIdentify())){
            return new ResultDTO(ResultEnum.PERMISSION_DENIED);
        }
        if(StringUtils.isNotEmpty(q.getContent())){
            return new ResultDTO(ResultEnum.CANNOT_ANSWER_REPEATEDLY);
        }
        Optional<User> answererOpt = userRepository.findByIdentify(q.getAnswererIdentify());
        assert answererOpt.isPresent();
        User answerer = answererOpt.get();
        answerer.setAnswerCnt(answerer.getAnswerCnt() + 1);
        userRepository.save(answerer);
        q.setContent(content);
        q.setLastModifiedDate(new Date(System.currentTimeMillis()));
        questionRepository.save(q);
        return new ResultDTO(ResultEnum.SUCCESS);
    }

    public ResultDTO askQuestion(String sessionKey, String question, String answererIdentify){

        Question q = new Question();
//        Optional<User> answererOpt = userRepository.findByIdentify(answererIdentify);
        Optional<User> questionerOpt = userRepository.findByIdentify(redisHelper.getValue(sessionKey));
        if(!questionerOpt.isPresent()){
            return new ResultDTO(ResultEnum.USER_NOT_FOUND);
        }
        User questioner = questionerOpt.get();
//        if(redisHelper.getValue(sessionKey).equals(answererIdentify)){
//            return new ResultDTO(ResultEnum.CANNOT_ASK_YOUR_SELF);
//        }
//
//        User questioner = questionerOpt.get();
        questioner.setAskCnt(questioner.getAskCnt() + 1);
//        userRepository.save(answerer);
        userRepository.save(questioner);
//        answerer.setAskedCnt(answerer.getAskedCnt() + 1);
        Optional<User> answererOpt = userRepository.findByIdentify(answererIdentify);
        if(!answererOpt.isPresent()){
            return new ResultDTO(ResultEnum.USER_NOT_FOUND);
        }
        User answerer = answererOpt.get();
//        answerer = userRepository.findByIdentify(answererIdentify).get();
        answerer.setAskedCnt(answerer.getAskedCnt() + 1);
        userRepository.save(answerer);
        q.setAskDate(new Date(System.currentTimeMillis()));
        q.setLastModifiedDate(new Date(System.currentTimeMillis()));
        q.setQuestionerIdentify(redisHelper.getValue(sessionKey));
        q.setAnswererIdentify(answererIdentify);
        q.setQuestion(question);
        questionRepository.save(q);
        return new ResultDTO(ResultEnum.SUCCESS);
    }
}
