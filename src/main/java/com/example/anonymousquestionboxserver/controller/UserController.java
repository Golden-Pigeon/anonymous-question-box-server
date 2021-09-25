package com.example.anonymousquestionboxserver.controller;

import com.example.anonymousquestionboxserver.model.dto.ResultDTO;
import com.example.anonymousquestionboxserver.service.UserInfoService;
import com.example.anonymousquestionboxserver.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    UserLoginService userLoginService;
    @Resource
    UserInfoService userInfoService;

    @PostMapping("/login")
    @ResponseBody
    public ResultDTO login(@RequestParam String code,
                           @RequestParam String name){
        return userLoginService.login(code, name);
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResultDTO getUserInfo(@RequestParam(value = "user_id") String userId){
        return userInfoService.getUserInfo(userId);
    }
}
