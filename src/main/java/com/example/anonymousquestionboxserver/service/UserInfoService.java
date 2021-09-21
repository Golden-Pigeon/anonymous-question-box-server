package com.example.anonymousquestionboxserver.service;

import com.example.anonymousquestionboxserver.model.dto.ResultDTO;

public interface UserInfoService {
    ResultDTO getUserInfo(String identify);
}
