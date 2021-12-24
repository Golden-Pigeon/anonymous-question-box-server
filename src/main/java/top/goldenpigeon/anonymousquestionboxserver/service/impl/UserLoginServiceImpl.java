package top.goldenpigeon.anonymousquestionboxserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import top.goldenpigeon.anonymousquestionboxserver.common.enums.ResultEnum;
import top.goldenpigeon.anonymousquestionboxserver.common.enums.VariableEnum;
import top.goldenpigeon.anonymousquestionboxserver.common.enums.WechatEnum;
import top.goldenpigeon.anonymousquestionboxserver.common.utils.HttpUtils;
import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;
import top.goldenpigeon.anonymousquestionboxserver.model.entity.User;
import top.goldenpigeon.anonymousquestionboxserver.repository.UserRepository;
import top.goldenpigeon.anonymousquestionboxserver.service.UserLoginService;
import top.goldenpigeon.anonymousquestionboxserver.service.helper.RedisHelper;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisHelper redisHelper;

    @Override
    public ResultDTO login(String code, String name) {
        Map<String, String> param = new HashMap<>();
        param.put("appid", WechatEnum.APP_ID.getValue());
        param.put("secret", WechatEnum.SECRET.getValue());
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String wxResult = HttpUtils.doGet(WechatEnum.WX_LOGIN.getValue(), param);
        log.info("wxResult:{}", wxResult);

        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        String openId = jsonObject.getString("openid");
        String session_key = jsonObject.getString("session_key");
//        System.out.println("code: " + code + " name: " + name);
        log.info("code: " + code + " name: " + name);
//        String openId = "123";
//        String session_key = "123";
        String session = UUID.randomUUID().toString().replace("-", " ").toUpperCase();
        if (StringUtil.isNullOrEmpty(openId)) {
            log.error("errcode:{}，errmsg: {}", jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
            return new ResultDTO(ResultEnum.INTERFACE_FAIL);
        }
        Map<String, Object> res = new HashMap<>();
        Optional<User> user = userRepository.findByOpenId(openId);

        if (user.isPresent()) {
            //login-ed
            if (!user.get().getName().equals(name)){
                user.get().setName(name);
                userRepository.save(user.get());
            }
            redisHelper.set(session_key, user.get().getIdentify(), VariableEnum.LOGIN_TIMEOUT.getValue());
            res.put("identify", user.get().getIdentify());
        } else {
            //never login-ed
            User newUser = new User(openId);
            newUser.setName(name);
            newUser.setIdentify(Integer.toHexString(openId.hashCode()));
            newUser.setAnswerCnt(0);
            newUser.setAskedCnt(0);
            newUser.setAskCnt(0);
            userRepository.save(newUser);
            redisHelper.set(session_key, newUser.getIdentify(), VariableEnum.LOGIN_TIMEOUT.getValue());
            res.put("identify", newUser.getIdentify());
        }
        res.put("session-key", session_key);
        ResultDTO resultDTO = new ResultDTO(ResultEnum.SUCCESS);
        resultDTO.setData(res);

        return resultDTO;
    }
}
