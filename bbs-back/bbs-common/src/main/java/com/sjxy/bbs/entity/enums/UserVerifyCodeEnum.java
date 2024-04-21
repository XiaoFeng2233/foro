package com.sjxy.bbs.entity.enums;

public enum UserVerifyCodeEnum {
    REGISTER(1),
    FORGET_PASSWORD(2);

    UserVerifyCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    private Integer code;
}
