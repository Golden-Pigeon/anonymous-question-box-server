package top.goldenpigeon.anonymousquestionboxserver.controller;

import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;
import top.goldenpigeon.anonymousquestionboxserver.service.UserInfoService;
import top.goldenpigeon.anonymousquestionboxserver.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
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
        ResultDTO dto = userLoginService.login(code, name);
        log.info("login_completed.");
        return dto;
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResultDTO getUserInfo(@RequestParam(value = "user_id") String userId){
        return userInfoService.getUserInfo(userId);
    }
}
