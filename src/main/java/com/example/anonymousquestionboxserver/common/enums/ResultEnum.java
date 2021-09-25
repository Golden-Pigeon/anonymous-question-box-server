package com.example.anonymousquestionboxserver.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    INTERFACE_FAIL(30000, "微信登录API请求失败"),

    USER_NOT_FOUND(30001, "用户未找到"),

    QUESTION_NOT_FOUND(30002, "问题未找到"),

    UNKNOWN_ERROR(30003, "未知服务器错误，请联系我"),

    PERMISSION_DENIED(30004, "没有修改权限"),

    SUCCESS(200, "正常返回");


    private final Integer responseCode;

    private final String message;
}
