package com.example.anonymousquestionboxserver.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(200, "正常返回");

    private final Integer responseCode;

    private final String message;
}
