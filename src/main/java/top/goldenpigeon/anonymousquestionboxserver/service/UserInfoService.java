package top.goldenpigeon.anonymousquestionboxserver.service;

import top.goldenpigeon.anonymousquestionboxserver.model.dto.ResultDTO;

public interface UserInfoService {
    ResultDTO getUserInfo(String identify);
}
