package com.example.anonymousquestionboxserver.service;

import com.example.anonymousquestionboxserver.model.dto.ResultDTO;

public interface UserLoginService {
    ResultDTO login(String code, String name);
}
