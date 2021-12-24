package top.goldenpigeon.anonymousquestionboxserver.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WechatEnum {
    WX_LOGIN("https://api.weixin.qq.com/sns/jscode2session"),

    WX_ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token"),

    WX_CODE("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="),

    APP_ID("wx5b825c53f40ef575"),

    SECRET("a9505f41bf5d7244530647ac8e2bee6a");

    private String value;
}
