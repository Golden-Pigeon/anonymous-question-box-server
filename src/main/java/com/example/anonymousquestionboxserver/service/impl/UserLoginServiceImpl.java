package com.example.anonymousquestionboxserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.anonymousquestionboxserver.common.enums.ResultEnum;
import com.example.anonymousquestionboxserver.common.enums.VariableEnum;
import com.example.anonymousquestionboxserver.common.enums.WechatEnum;
import com.example.anonymousquestionboxserver.common.utils.HttpUtils;
import com.example.anonymousquestionboxserver.model.dto.ResultDTO;
import com.example.anonymousquestionboxserver.model.entity.User;
import com.example.anonymousquestionboxserver.repository.UserRepository;
import com.example.anonymousquestionboxserver.service.UserLoginService;
import com.example.anonymousquestionboxserver.service.helper.RedisHelper;
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
            newUser.setIdentify(Integer.toHexString(newUser.hashCode()));
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
