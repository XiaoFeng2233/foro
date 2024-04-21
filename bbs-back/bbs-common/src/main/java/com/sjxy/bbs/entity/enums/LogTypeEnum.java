package com.sjxy.bbs.entity.enums;


public enum LogTypeEnum {
    LOGIN(1),
    LOGOUT(2),
    SIGN_IN(3);

    private int code;

    LogTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
