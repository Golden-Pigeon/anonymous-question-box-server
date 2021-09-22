package com.example.anonymousquestionboxserver.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WechatEnum {
    WX_LOGIN("https://api.weixin.qq.com/sns/jscode2session"),

    WX_ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token"),

    WX_CODE("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="),

    APP_ID("123"),

    SECRET("123");

    private String value;
}
