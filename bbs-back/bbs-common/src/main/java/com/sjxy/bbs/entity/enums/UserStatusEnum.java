package com.sjxy.bbs.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Data;


public enum UserStatusEnum {

    //正常
    OK(1),
    //封禁
    FORBIDDEN(0),
    //禁言
    MUTE(2);

    private Integer code;

    UserStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
