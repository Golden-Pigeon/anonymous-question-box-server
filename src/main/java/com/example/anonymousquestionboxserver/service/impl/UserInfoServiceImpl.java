package com.example.anonymousquestionboxserver.service.impl;

import com.example.anonymousquestionboxserver.common.enums.ResultEnum;
import com.example.anonymousquestionboxserver.model.dto.ResultDTO;
import com.example.anonymousquestionboxserver.model.entity.User;
import com.example.anonymousquestionboxserver.repository.UserRepository;
import com.example.anonymousquestionboxserver.service.UserInfoService;
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

        Map<String, String> res = new HashMap<>();

        res.put("name", u.getName());

        ResultDTO resultDTO = new ResultDTO(ResultEnum.SUCCESS);
        resultDTO.setData(res);

        return resultDTO;
    }
}
