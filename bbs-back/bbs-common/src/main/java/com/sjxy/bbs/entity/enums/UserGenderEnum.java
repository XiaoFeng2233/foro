package com.sjxy.bbs.entity.enums;

public enum UserGenderEnum {
    MALE(1),
    FEMALE(0),
    SECRET(-1);
    private Integer code;

    UserGenderEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
