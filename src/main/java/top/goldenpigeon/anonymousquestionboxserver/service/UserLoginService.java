package top.goldenpigeon.anonymousquestionboxserver.service;

import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;

public interface UserLoginService {
    ResultDTO login(String code, String name);
}
