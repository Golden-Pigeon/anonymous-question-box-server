package top.goldenpigeon.anonymousquestionboxserver.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VariableEnum {
    OK(0),

    DELETE(1),

    LOGIN_TIMEOUT(7 * 24 * 60 * 60),


    LOGIN_TIMEOUT_TEST(10),

    INVALID(-1);

    private Integer value;



}
