package top.goldenpigeon.anonymousquestionboxserver.service.impl;

import top.goldenpigeon.anonymousquestionboxserver.common.enums.ResultEnum;
import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;
import top.goldenpigeon.anonymousquestionboxserver.model.entity.User;
import top.goldenpigeon.anonymousquestionboxserver.repository.UserRepository;
import top.goldenpigeon.anonymousquestionboxserver.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserRepository userRepository;

    @Override
    public ResultDTO getUserInfo(String identify) {
        Optional<User> user = userRepository.findByIdentify(identify);
        if (!user.isPresent()) {
            return new ResultDTO(ResultEnum.USER_NOT_FOUND);
        }
        User u = user.get();
        log.debug("User_id: " + identify);
        log.debug("User: " + u);
        Map<String, String> res = new HashMap<>();

        res.put("name", u.getName());
        res.put("ask_cnt", u.getAskCnt().toString());
        res.put("asked_cnt", u.getAskedCnt().toString());
        res.put("ans_cnt", u.getAnswerCnt().toString());

        ResultDTO resultDTO = new ResultDTO(ResultEnum.SUCCESS);
        resultDTO.setData(res);

        return resultDTO;
    }
}
